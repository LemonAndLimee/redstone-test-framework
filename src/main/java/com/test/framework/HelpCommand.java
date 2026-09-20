package com.test.framework;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class HelpCommand
{
    /**
     *  Prints help command output, listing other available commands.
     *
     *  @param  ctx  Context object for the command being executed.
     *
     *  @return  1 if successful, 0 otherwise.
     */
    public static int execute(CommandContext<CommandSourceStack> ctx)
    {
        Component message = Component.empty()
            .append(Component.literal("--- Available commands ---\n").withStyle(ChatFormatting.BOLD))

            .append(Component.literal("/testf help").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" : Prints this help message.\n").withStyle(ChatFormatting.GRAY))

            .append(Component.literal("/testf origin set").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" : Sets the origin position (to the block you are looking at) for any test" +
                "scenarios. This is the relative 0,0,0 for any coordinates\n").withStyle(ChatFormatting.GRAY))

            .append(Component.literal("/testf origin get").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" : Prints the current origin position.\n").withStyle(ChatFormatting.GRAY))

            .append(Component.literal("/testf origin get_relative_pos").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" : Prints the targeted block position, relative to the current origin.\n")
                .withStyle(ChatFormatting.GRAY))

            // TODO: add more commands as they are created.

            .append(Component.literal("------\n").withStyle(ChatFormatting.BOLD));

        CommandSourceStack source = ctx.getSource();
        source.sendSuccess(() -> message, false);

        return 1;
    }
}
