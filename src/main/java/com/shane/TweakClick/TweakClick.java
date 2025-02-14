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

package com.shane.TweakClick;

import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.tweakeroo.InitHandler;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TweakClick implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_NAME = "TweakClick";
    public static final String MOD_ID = "tweakclick";
    public static final String MOD_VERSION = StringUtils.getModVersionString(MOD_ID);

    @Override
    public void onInitialize() {
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
    }
}
