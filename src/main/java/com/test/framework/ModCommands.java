package com.test.framework;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands
{
    /**
     *  Registers all custom commands for the mod.
     *
     *  @param  dispatcher  Command dispatcher for registering commands to.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(
            Commands.literal("testf")
            .then(
                Commands.literal("help").executes(HelpCommand::execute)
            )
            .then(
                Commands.literal("origin")
                .then(
                    Commands.literal("get").executes(OriginPosition::executeGetOriginCommand)
                )
                .then(
                    Commands.literal("set").executes(OriginPosition::executeSetOriginCommand)
                )
                .then(
                    Commands.literal("get_relative_pos").executes(OriginPosition::executeGetRelativePosCommand)
                )
            )
        );
    }
}
