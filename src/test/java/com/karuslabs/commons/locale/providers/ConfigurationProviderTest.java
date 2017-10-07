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
package com.karuslabs.commons.locale.providers;

import com.karuslabs.commons.configuration.ProxiedConfiguration;

import java.util.*;

import org.bukkit.entity.Player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class ConfigurationProviderTest {
    
    private ProxiedConfiguration config = mock(ProxiedConfiguration.class);
    private ConfigurationProvider provider = new ConfigurationProvider(config);
    private Player player = when(mock(Player.class).getUniqueId()).thenReturn(UUID.randomUUID()).getMock();
    
    
    @Test
    public void get() {
        when(config.getString(player.getUniqueId().toString())).thenReturn("ja_JP");
        
        assertEquals(Locale.JAPAN, provider.get(player));
    }
    
    
    @Test
    public void set() {
        provider.set(player, Locale.JAPAN);
        
        verify(config).set(player.getUniqueId().toString(), "ja_JP");
    }
    
    
    @Test
    public void save() {
        provider.save();
        
        verify(config).save();
    }
    
}
