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

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import org.jdom2.Element;

import org.junit.rules.ExternalResource;

import static org.mockito.Mockito.*;


public class ItemMetaResource<GenericMeta extends ItemMeta> extends ExternalResource {
    
    public static final Enchantment ENCHANTMENT;
    
    static {
        ENCHANTMENT = mock(Enchantment.class);
        when(ENCHANTMENT.getName()).thenReturn("PROTECTION_ENVIROMENTAL");
        Enchantment.registerEnchantment(ENCHANTMENT);
    }
    
    
    private ItemMetaComponent<GenericMeta> component;
    private GenericMeta meta;
    
    
    public ItemMetaResource(ItemMetaComponent<GenericMeta> component, GenericMeta meta) {
        this.component = component;
        this.meta = meta;
    }

    
    public ItemMetaComponent<GenericMeta> getComponent() {
        return component;
    }

    public GenericMeta getMeta() {
        return meta;
    }
    
    
    public void parse(Element root) {
        component.parse(root, meta);
    }
    
    
    public void assertMeta() {
        verify(meta, times(1)).setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cItem"));
        verify(meta, times(1)).addEnchant(ENCHANTMENT, 3, true);
        verify(meta, times(1)).setLore(Arrays.asList(new String[] {"Line 1", "Line 2"}));
        verify(meta, times(1)).addItemFlags(ItemFlag.HIDE_DESTROYS);
    }
    
}
