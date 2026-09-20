package com.test.framework;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

// Class relating to the origin position for the current session. This is the relative 0,0,0 for any coordinates
// listed in test scenarios.
public class OriginPosition
{
    // Default to 0,0,0
    private static BlockPos origin = new BlockPos(0,0,0);

    public static void setOrigin(BlockPos position)
    {
        origin = position;
    }
    public static BlockPos getOrigin()
    {
        return origin;
    }

    /**
     *  Prints current origin position.
     *
     *  @param  ctx  Context object for the command being executed.
     *
     *  @return  1 if successful, 0 otherwise.
     */
    public static int executeGetOriginCommand(CommandContext<CommandSourceStack> ctx)
    {
        String posString = origin.toShortString();

        CommandSourceStack source = ctx.getSource();
        source.sendSuccess(
            () -> Component.literal("Origin is currently set to (" + posString + ").\n").withStyle(ChatFormatting.WHITE),
            false
        );

        return 1;
    }

    /**
     *  Sets origin position to the block being looked at.
     *
     *  @param  ctx  Context object for the command being executed.
     *
     *  @return  1 if successful, 0 otherwise.
     */
    public static int executeSetOriginCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException
    {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();

        BlockPos targetedBlockPos = PlayerRaycastHelper.getTargetedBlock(player);
        if (targetedBlockPos == null)
        {
            source.sendFailure(
                Component.literal("Cannot set origin position: must be looking at a block.\n").withStyle(ChatFormatting.RED)
            );
            return 0;
        }

        String posString = targetedBlockPos.toShortString();
        source.sendSuccess(
            () -> Component.literal("Setting origin to (" + posString + ").\n").withStyle(ChatFormatting.WHITE), false
        );

        origin = targetedBlockPos;

        return 1;
    }

    /**
     *  Prints the position of the block being looked at, relative to the current origin.
     *
     *  @param  ctx  Context object for the command being executed.
     *
     *  @return  1 if successful, 0 otherwise.
     */
    public static int executeGetRelativePosCommand(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException
    {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayerOrException();

        BlockPos targetedBlockPos = PlayerRaycastHelper.getTargetedBlock(player);
        if (targetedBlockPos == null)
        {
            source.sendFailure(
                Component.literal("Cannot get relative position: must be looking at a block.\n").withStyle(ChatFormatting.RED)
            );
            return 0;
        }

        BlockPos relativePos = targetedBlockPos.subtract(origin);

        String posString = relativePos.toShortString();
        source.sendSuccess(
            () -> Component.literal("Position relative to origin: (" + posString + ").\n").withStyle(ChatFormatting.WHITE), false
        );

        return 1;
    }
}
