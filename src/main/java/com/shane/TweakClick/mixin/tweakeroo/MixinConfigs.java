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

package com.shane.TweakClick.mixin.tweakeroo;

import com.google.common.collect.ImmutableList;
import com.shane.TweakClick.config.ListExtend;
import com.shane.TweakClick.tweak.PlacementTweaks;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.tweakeroo.config.Configs;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;


@Pseudo
@Mixin(value = Configs.class, remap = false)
public class MixinConfigs {
    @Unique
    private static final ImmutableList<IHotkeyTogglable> FEATURE_OPTIONS = new ImmutableList.Builder<IHotkeyTogglable>().addAll(Arrays.asList(FeatureToggle.values())).addAll(ListExtend.FEATURE_OPTIONS).build();

    @ModifyArg(method = "loadFromFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;readHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> readFeatureOptions(List<? extends IHotkeyTogglable> options) {
        return FEATURE_OPTIONS;
    }

    @ModifyArg(method = "saveToFile", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/config/ConfigUtils;writeHotkeyToggleOptions(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", ordinal = 1), index = 3)
    private static List<? extends IHotkeyTogglable> writeFeatureOptions(List<? extends IHotkeyTogglable> options) {
        return FEATURE_OPTIONS;
    }

    @Inject(method = "loadFromFile", at = @At("RETURN"))
    private static void setTweakLists(CallbackInfo ci) {
        PlacementTweaks.setPerimeterOutlineBlocks(ListExtend.PERIMETER_OUTLINE_BLOCKS_LIST.getStrings());
    }
}
