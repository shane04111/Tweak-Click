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

package com.shane.TweakClick.tweak;

import com.shane.TweakClick.config.ConfigExtend;
import com.shane.TweakClick.config.FeatureToggleExtended;
import com.shane.TweakClick.config.ListExtend;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
//#if MC <= 11802
import net.minecraft.util.registry.Registry;
//#else
//$$ import net.minecraft.registry.Registries;
//#endif
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlacementTweaks {
    public static final ArrayList<Block> PERIMETER_OUTLINE_BLOCKS = new ArrayList<>();

    public static boolean isPositionDisallowedByPerimeterOutlineList(BlockPos pos) {
        boolean restrictionEnabled = FeatureToggleExtended.PERIMETER_WALL_DIGGER.getBooleanValue();

        if (!restrictionEnabled) return false;

        ClientWorld world = MinecraftClient.getInstance().world;
        return world != null && PERIMETER_OUTLINE_BLOCKS.contains(world.getBlockState(world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).down()).getBlock());
    }

    public static void setPerimeterOutlineBlocks(List<String> blocks) {
        PERIMETER_OUTLINE_BLOCKS.clear();

        for (String str : blocks) {
            Block block = getBlockFromName(str);

            if (block != null) PERIMETER_OUTLINE_BLOCKS.add(block);
        }
    }

    @Nullable
    private static Block getBlockFromName(String name) {
        try {
            Identifier identifier = new Identifier(name);
            //#if MC <= 11802
            Block result = Registry.BLOCK.getOrEmpty(identifier).orElse(null);
            //#else
            //$$ Block result = Registries.BLOCK.getOrEmpty(identifier).orElse(null);
            //#endif
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
