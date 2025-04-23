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
import com.shane.TweakClick.config.FeatureToggleExtended;
import fi.dy.masa.malilib.config.*;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//#if MC >=11800
import fi.dy.masa.malilib.config.options.BooleanHotkeyGuiWrapper;
//#else
//$$ import java.util.ArrayList;
//$$ import java.util.List;
//#endif


import java.util.Arrays;
import java.util.Collection;

@Pseudo
@Mixin(value = GuiConfigs.class, remap = false)
public class MixinGuiConfigs {
    //#if MC >= 11800
    @Unique
    private static final ImmutableList<IHotkeyTogglable> FEATURE_TOGGLES = new ImmutableList
            .Builder<IHotkeyTogglable>()
            .addAll(Arrays.asList(FeatureToggleExtended.values()))
            .addAll(Arrays.asList(FeatureToggle.values()))
            .build();

    @Unique
    protected BooleanHotkeyGuiWrapper wrapConfig(IHotkeyTogglable config) {
        return new BooleanHotkeyGuiWrapper(config.getName(), config, config.getKeybind());
    }

    @ModifyArg(method = "getConfigs", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;createFor(Ljava/util/Collection;)Ljava/util/List;", ordinal = 1), index = 0)
    private Collection<? extends IConfigBase> ExtendTweaks(Collection<? extends IConfigBase> configs) {
        return FEATURE_TOGGLES.stream().map(this::wrapConfig).toList();
    }
    //#else
    //$$ private static final ImmutableList<IConfigValue> FEATURE_TOGGLES = new ImmutableList.Builder<IConfigValue>().addAll(Arrays.asList(FeatureToggle.values())).addAll(Arrays.asList(FeatureToggleExtended.values())).build();
    //$$ @ModifyArg(method = "getConfigs", at = @At(value = "INVOKE", target = "Lfi/dy/masa/malilib/gui/GuiConfigsBase$ConfigOptionWrapper;createFor(Ljava/util/Collection;)Ljava/util/List;", ordinal = 1), index = 0)
    //$$ private Collection<? extends IConfigBase> ExtendTweaks(Collection<? extends IConfigBase> configs) {
    //$$     List<IConfigBase> list = new ArrayList<>();
    //$$     list.addAll(ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, FEATURE_TOGGLES));
    //$$     list.addAll(ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, FEATURE_TOGGLES));
    //$$     return list;
    //$$ }
    //#endif

}
