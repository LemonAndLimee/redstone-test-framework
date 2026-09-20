package com.test.framework;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class PlayerRaycastHelper
{
    /**
     *  Returns the block currently being looked at by the player.
     *
     *  @param  player  The player who executed the relevant trigger command.
     *
     *  @return  Position of targeted block, or null if there is none.
     */
    public static BlockPos getTargetedBlock(ServerPlayer player)
    {
        // Twice the usual player reach distance
        double reachDistance = 10.0;
        HitResult hitResult = player.pick(reachDistance, 0.0F, false);

        if (hitResult.getType() == HitResult.Type.BLOCK)
        {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            return blockHitResult.getBlockPos();
        }
        else
        {
            // Player is looking at air or an entity
            return null;
        }
    }
}
