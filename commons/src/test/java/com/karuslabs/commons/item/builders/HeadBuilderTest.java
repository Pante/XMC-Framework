/*
 * The MIT License
 *
 * Copyright 2018 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.commons.item.builders;

import com.karuslabs.commons.MockBukkit;

import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import org.junit.jupiter.api.Test;

import static com.karuslabs.commons.item.Head.ALEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HeadBuilderTest {
    
    SkullMeta meta = MockBukkit.meta(SkullMeta.class);
    
    @Test
    void constructors() {
        assertEquals(HeadBuilder.head().build().getType(), Material.PLAYER_HEAD);
        assertEquals(HeadBuilder.wall().build().getType(), Material.PLAYER_WALL_HEAD);
    }
    
    @Test
    void owner() {
        var builder = HeadBuilder.head().self();
        var owner = MockBukkit.offline();
        
        builder.head(ALEX);
        
        verify(meta).setOwningPlayer(owner);
    }
    
}