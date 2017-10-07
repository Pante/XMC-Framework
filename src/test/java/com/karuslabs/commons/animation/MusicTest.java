/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
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
package com.karuslabs.commons.animation;

import com.karuslabs.commons.annotation.JDK9;
import org.bukkit.*;
import org.bukkit.entity.Player;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.bukkit.Sound.WEATHER_RAIN;
import static org.bukkit.SoundCategory.MASTER;
import static org.mockito.Mockito.*;


public class MusicTest {
    
    private Music music = spy(new Music(WEATHER_RAIN, MASTER, 0.5F, 0.75F));
    private World world = mock(World.class);
    private Location location = when(mock(Location.class).getWorld()).thenReturn(world).getMock();
    private Player player = when(mock(Player.class).getLocation()).thenReturn(location).getMock();
    
    
    @Test
    public void play() {
        music.play(player);
        
        verify(music).play(player, location);
    }
    
    
    @Test
    @JDK9("List.of(...)")
    public void play_Collection_Location() {
        music.play(singletonList(player), location);
        
        verify(music).play(player, location);
    }
    
    
    @Test
    public void play_Player_Location() {
        music.play(player, location);
        
        verify(player).playSound(location, WEATHER_RAIN, MASTER, 0.5F, 0.75F);
    }
    
    
    @Test
    public void play_Location() {
        music.play(location);
        
        verify(world).playSound(location, WEATHER_RAIN, MASTER, 0.5F, 0.75F);
    }
    
}
