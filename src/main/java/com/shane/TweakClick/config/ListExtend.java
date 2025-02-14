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

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigStringList;

public class ListExtend {
    public static final ConfigBoolean MOVEMENT_WILL_HOLD = Boolean("movementWillHold");
    public static final ConfigBooleanHotkeyed PERIMETER_WALL_DIGGER = BooleanHotkey("perimeterWallDigger");
    public static final ConfigBooleanHotkeyed ONLY_SPRINT_HOLD_ATTACK = BooleanHotkey("onlySprintHoldAttack");
    public static final ConfigBooleanHotkeyed FLAT_DIGGER = BooleanHotkey("flatDigger");
    public static final ConfigBooleanHotkeyed VERTICAL_DIGGER = BooleanHotkey("verticalDigger");
    public static final ConfigBooleanHotkeyed DIGGER_X = BooleanHotkey("diggerX");
    public static final ConfigBooleanHotkeyed DIGGER_Z = BooleanHotkey("diggerZ");
    public static final ConfigBooleanHotkeyed REMOVE_BREAKING_COOLDOWN = BooleanHotkey("removeCooldown");
    public static final ConfigStringList PERIMETER_OUTLINE_BLOCKS_LIST = StringList("perimeterOutlineBlocks");
    public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
            MOVEMENT_WILL_HOLD,
            ONLY_SPRINT_HOLD_ATTACK,
            REMOVE_BREAKING_COOLDOWN,
            FLAT_DIGGER,
            VERTICAL_DIGGER,
            DIGGER_X,
            DIGGER_Z,
            PERIMETER_WALL_DIGGER,
            PERIMETER_OUTLINE_BLOCKS_LIST
    );
    public static final ImmutableList<IHotkeyTogglable> FEATURE_OPTIONS = ImmutableList.of(
            PERIMETER_WALL_DIGGER,
            ONLY_SPRINT_HOLD_ATTACK,
            FLAT_DIGGER,
            VERTICAL_DIGGER,
            REMOVE_BREAKING_COOLDOWN
    );

    private static ConfigBoolean Boolean(String name) {
        return new ConfigBoolean(name, false, name + ".comment");
    }

    private static ConfigBooleanHotkeyed BooleanHotkey(String name) {
        return new ConfigBooleanHotkeyed(name, false, "", name + ".comment");
    }

    private static ConfigStringList StringList(String name) {
        return new ConfigStringList(name, ImmutableList.of(), name + ".comment");
    }

    private static ConfigInteger Integer(String name) {
        return new ConfigInteger(name, 0, 0, 6000, name + ".comment");
    }
}
