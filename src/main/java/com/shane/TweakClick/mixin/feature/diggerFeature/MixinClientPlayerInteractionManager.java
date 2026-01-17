/*
 * MIT License
 *
 * Copyright (c) 2025 Shane0411
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shane.TweakClick.mixin.feature.diggerFeature;


import com.shane.TweakClick.config.FeatureToggleExtended;
import com.shane.TweakClick.tweak.PlacementTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class MixinClientPlayerInteractionManager {
    @Inject(method = "continueDestroyBlock", at = @At(value = "HEAD"), cancellable = true)
    private void flatDigger1(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (shouldCancelBreaking(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    private void handleBreakingRestriction1(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (shouldCancelBreaking(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean shouldCancelBreaking(BlockPos pos) {
        ClientLevel world = Minecraft.getInstance().level;
        LocalPlayer player = Minecraft.getInstance().player;
        if (world == null && player == null) {
            return false;
        }
        assert player != null;
        boolean isSneaking = player.isShiftKeyDown();

        //#if MC >= 11700
        int playerX = player.getBlockX();
        int playerY = player.getBlockY();
        int playerZ = player.getBlockZ();
        //#elseif MC >= 11600 && MC < 11700
        //$$ int playerX = player.blockPosition().getX();
        //$$ int playerY = player.blockPosition().getY();
        //$$ int playerZ = player.blockPosition().getZ();
        //#else
        //$$ int playerX = player.getCommandSenderBlockPosition().getX();
        //$$ int playerY = player.getCommandSenderBlockPosition().getY();
        //$$ int playerZ = player.getCommandSenderBlockPosition().getZ();
        //#endif

        boolean diggerX = FeatureToggleExtended.DIGGER_X.getBooleanValue() && !isSneaking && pos.getX() != playerX;
        boolean diggerZ = FeatureToggleExtended.DIGGER_Z.getBooleanValue() && !isSneaking && pos.getZ() != playerZ;
        boolean flatDigger = FeatureToggleExtended.FLAT_DIGGER.getBooleanValue() && !isSneaking && pos.getY() < playerY;
        boolean verticalDigger = FeatureToggleExtended.VERTICAL_DIGGER.getBooleanValue() && isSneaking && (pos.getX() != playerX || pos.getZ() != playerZ);

        return diggerX || diggerZ || flatDigger || verticalDigger;
    }
}
