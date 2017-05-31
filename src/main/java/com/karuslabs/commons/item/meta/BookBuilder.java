/*
 * Copyright (C) 2017 Karus Labs
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
package com.karuslabs.commons.item.meta;

import com.karuslabs.commons.item.Builder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Represents a specialised <code>Builder</code> for building items with <code>BookMeta</code>.
 */
public class BookBuilder extends Builder<BookBuilder, BookMeta> {
    
    /**
     * Constructs a <code>BookBuilder</code> with the specified <code>ItemStack</code>.
     * 
     * @param item the ItemStack
     */
    public BookBuilder(ItemStack item) {
        super(item);
    }
    
    /**
     * Copy constructor which constructs a <code>BookBuilder</code> with the specified <code>Builder</code>.
     * 
     * @param builder the Builder
     */
    public BookBuilder(Builder builder) {
        super(builder);
    }
    
    /**
     * Sets the title to the specified parameter.
     * 
     * @param title the Title
     * @return this
     */
    public BookBuilder title(String title) {
        meta.setTitle(title);
        return this;
    }
    
    /**
     * Sets the author to the specified parameter.
     * 
     * @param author the Author
     * @return this
     */
    public BookBuilder author(String author) {
        meta.setAuthor(author);
        return this;
    }
    
    /**
     * Adds the specified array of pages.
     * 
     * @param pages the array of pages
     * @return this
     */
    public BookBuilder pages(String... pages) {
        meta.addPage(pages);
        return this;
    }
    
    /**
     * Sets the generation level to the specified parameter.
     * 
     * @param generation the Generation level
     * @return this
     */
    public BookBuilder generation(BookMeta.Generation generation) {
        meta.setGeneration(generation);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BookBuilder getThis() {
        return this;
    }
    
}
