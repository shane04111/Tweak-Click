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

package com.shane.TweakClick.mixin.feature.Digger;


import com.shane.TweakClick.config.FeatureToggleExtended;
import com.shane.TweakClick.tweak.PlacementTweaks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Inject(method = "updateBlockBreakingProgress", at = @At(value = "HEAD"), cancellable = true)
    private void flatDigger1(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (shouldCancelBreaking(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void handleBreakingRestriction1(BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (shouldCancelBreaking(pos) || PlacementTweaks.isPositionDisallowedByPerimeterOutlineList(pos)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean shouldCancelBreaking(BlockPos pos) {
        ClientWorld world = MinecraftClient.getInstance().world;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (world == null && player == null) {
            return false;
        }
        assert player != null;
        boolean isSneaking = player.isSneaking();

        //#if MC >= 11701
        int playerX = player.getBlockX();
        int playerY = player.getBlockY();
        int playerZ = player.getBlockZ();
        //#else
        //$$ int playerX = player.getBlockPos().getX();
        //$$ int playerY = player.getBlockPos().getY();
        //$$ int playerZ = player.getBlockPos().getZ();
        //#endif

        boolean diggerX = FeatureToggleExtended.DIGGER_X.getBooleanValue() && !isSneaking && pos.getX() != playerX;
        boolean diggerZ = FeatureToggleExtended.DIGGER_Z.getBooleanValue() && !isSneaking && pos.getZ() != playerZ;
        boolean flatDigger = FeatureToggleExtended.FLAT_DIGGER.getBooleanValue() && !isSneaking && pos.getY() < playerY;
        boolean verticalDigger = FeatureToggleExtended.VERTICAL_DIGGER.getBooleanValue() && isSneaking && (pos.getX() != playerX || pos.getZ() != playerZ);

        return diggerX || diggerZ || flatDigger || verticalDigger;
    }
}
