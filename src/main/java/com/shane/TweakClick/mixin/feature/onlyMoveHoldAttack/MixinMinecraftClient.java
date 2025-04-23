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

package com.shane.TweakClick.mixin.feature.onlyMoveHoldAttack;

import com.shane.TweakClick.config.ConfigExtend;
import com.shane.TweakClick.config.FeatureToggleExtended;
import com.shane.TweakClick.config.ListExtend;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    protected int attackCooldown;
    @Shadow
    public Screen currentScreen;
    @Final
    @Shadow
    public GameOptions options;

    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void onHoldEasyPlace(CallbackInfo ci) {
        MinecraftClient mc = (MinecraftClient) (Object) this;
        if (this.currentScreen == null && mc.player != null && FeatureToggleExtended.ONLY_SPRINT_HOLD_ATTACK.getBooleanValue() && FeatureToggle.TWEAK_TOOL_SWITCH.getBooleanValue()) {
            ClientPlayerEntity player = mc.player;
            if (this.attackCooldown >= 10000) {
                this.attackCooldown = 0;
            }
            boolean pressed = (ConfigExtend.MOVEMENT_WILL_HOLD.getBooleanValue()) ? !player.input.getMovementInput().equals(Vec2f.ZERO) : player.isSprinting();
            KeyBinding.setKeyPressed(InputUtil.fromTranslationKey(this.options.attackKey.getBoundKeyTranslationKey()), pressed);
        }
    }
}