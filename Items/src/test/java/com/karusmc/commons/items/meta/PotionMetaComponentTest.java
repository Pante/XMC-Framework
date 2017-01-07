/*
 * Copyright (C) 2017 PanteLegacy @ karusmc.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.karusmc.commons.items.meta;

import com.karusmc.commons.core.xml.test.XMLResource;

import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import org.junit.*;

import static org.mockito.Mockito.*;


public class PotionMetaComponentTest {
    
    public static final PotionEffectType EFFECT;
    
    static {
        EFFECT = mock(PotionEffectType.class);
        when(EFFECT.getName()).thenReturn("JUMP");
        PotionEffectType.registerPotionEffectType(EFFECT);
    }
    
    
    @Rule
    public XMLResource xml = new XMLResource().load(getClass().getClassLoader().getResourceAsStream("meta/PotionMeta.xml"), null);
    
    @Rule
    public ItemMetaResource<PotionMeta> resource = new ItemMetaResource(new PotionMetaComponent(), mock(PotionMeta.class));

    
    @Test
    public void parse() {
        resource.parse(xml.getRoot());
        resource.assertMeta();
        
        PotionMeta meta = resource.getMeta();
        
        verify(meta, times(1)).setColor(Color.SILVER);
        verify(meta, times(1)).addCustomEffect(new PotionEffect(EFFECT, 10, 3, true, true), true);
    }
    
    
}
