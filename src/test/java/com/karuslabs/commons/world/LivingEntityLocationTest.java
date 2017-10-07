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
package com.karuslabs.commons.world;

import com.karuslabs.commons.util.Weak;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.Mockito.*;


public class LivingEntityLocationTest {
    
    private Location raw = new Location(null, 2, 3, 4);
    private Location entityLocation = new Location(null, 1, 2, 3);
    private LivingEntity entity = when(mock(LivingEntity.class).getEyeLocation()).thenReturn(entityLocation).getMock();
    private LivingEntityLocation<LivingEntity> location = spy(LivingEntityLocation.builder(entity, raw).nullable(true).update(true).build());
    
    
    @Test
    public void builder() {
        assertEquals(new PathVector(1, 1, 1, 0, 0), location.getOffset());
    }
    
    @ParameterizedTest
    @CsvSource({"true, 1", "false, 0"})
    public void update(boolean present, int times) {
        location.entity = present ? location.entity : new Weak<>(null);
        doNothing().when(location).update(any(Location.class));
        
        location.update();
        
        verify(location, times(times)).update(any(Location.class));
    }
    
}
