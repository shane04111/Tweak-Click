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

package com.shane.TweakClick.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakeroo.Tweakeroo;
import org.apache.logging.log4j.Logger;

public enum FeatureToggleExtended implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean> {
    PERIMETER_WALL_DIGGER("perimeterWallDigger"),
    ONLY_SPRINT_HOLD_ATTACK("onlySprintHoldAttack"),
    FLAT_DIGGER("flatDigger"),
    VERTICAL_DIGGER("verticalDigger"),
    DIGGER_X("diggerX"),
    DIGGER_Z("diggerZ"),
    REMOVE_BREAKING_COOLDOWN("removeCooldown");
    private final String name;
    //#if MC <= 12100
    private final String comment;
    private final String prettyName;
    //#else
    //$$ private String comment;
    //$$ private String prettyName;
    //$$ private String translatedName;
    //#endif
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final boolean singlePlayer;
    private boolean valueBoolean;
    private IValueChangeCallback<IConfigBoolean> callback;

    FeatureToggleExtended(String name) {
        this(name, false, false, "", name + ".comment");
    }

    FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT, comment);
    }

    FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment) {
        this(name, defaultValue, singlePlayer, defaultHotkey, settings, comment, StringUtils.splitCamelCase(name.substring(5)));
    }
    //#if MC <= 12100
    FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName) {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.singlePlayer = singlePlayer;
        this.comment = comment;
        this.prettyName = prettyName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }
    //#else
    //$$ FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName){
    //$$    this(name, defaultValue, singlePlayer, defaultHotkey, settings, prettyName, StringUtils.splitCamelCase(name), name);
    //$$ }
    //$$ FeatureToggleExtended(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName, String translatedName) {
    //$$     this.name = name;
    //$$     this.valueBoolean = defaultValue;
    //$$     this.defaultValueBoolean = defaultValue;
    //$$     this.singlePlayer = singlePlayer;
    //$$     this.translatedName = translatedName;
    //$$     this.comment = comment;
    //$$     this.prettyName = prettyName;
    //$$     this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
    //$$     this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    //$$ }
    //$$ @Override
    //$$ public String getTranslatedName() {
    //$$     String name = StringUtils.getTranslatedOrFallback(this.translatedName, this.name);
    //$$     if (this.singlePlayer) {
    //$$         name = GuiBase.TXT_GOLD + name + GuiBase.TXT_RST;
    //$$     }
    //$$     return name;
    //$$ }
    //$$ @Override
    //$$ public void setPrettyName(String prettyName) {
    //$$     this.prettyName = prettyName;
    //$$ }
    //$$ @Override
    //$$ public void setTranslatedName(String translatedName) {
    //$$     this.translatedName = translatedName;
    //$$ }
    //$$ @Override
    //$$ public void setComment(String comment) {
    //$$     this.comment = comment;
    //$$ }
    //#endif

    @Override
    public ConfigType getType() {
        return ConfigType.HOTKEY;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getConfigGuiDisplayName() {
        if (this.singlePlayer) {
            return GuiBase.TXT_GOLD + this.getName() + GuiBase.TXT_RST;
        }

        return this.getName();
    }

    @Override
    public String getPrettyName() {
        return this.prettyName;
    }

    @Override
    public String getStringValue() {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public String getDefaultStringValue() {
        return String.valueOf(this.defaultValueBoolean);
    }

    @Override
    public void setValueFromString(String value) {
    }

    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged(this);
        }
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback) {
        this.callback = callback;
    }

    @Override
    public String getComment() {
        if (this.comment == null) {
            return "";
        }

        if (this.singlePlayer) {
            return this.comment + "\n" + StringUtils.translate("tweakeroo.label.config_comment.single_player_only");
        } else {
            return this.comment;
        }
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
    }

    @Override
    public boolean getBooleanValue() {
        return this.valueBoolean;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultValueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value) {
        boolean oldValue = this.valueBoolean;
        this.valueBoolean = value;

        if (oldValue != this.valueBoolean) {
            this.onValueChanged();
        }
    }

    @Override
    public boolean isModified() {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public boolean isModified(String newValue) {
        return Boolean.parseBoolean(newValue) != this.defaultValueBoolean;
    }

    @Override
    public void resetToDefault() {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public JsonElement getAsJsonElement() {
        return new JsonPrimitive(this.valueBoolean);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            if (element.isJsonPrimitive()) {
                this.valueBoolean = element.getAsBoolean();
            } else {
                Logger().warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            Logger().warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }
    private Logger Logger() {
        //#if MC <= 12104
        return Tweakeroo.logger;
        //#else
        //$$ return Tweakeroo.LOGGER;
        //#endif
    }
}
