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
package com.karuslabs.commons.item.meta;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import org.junit.jupiter.api.Test;

import static org.bukkit.inventory.meta.BookMeta.Generation.COPY_OF_COPY;
import static org.mockito.Mockito.*;


public class BookBuilderTest {
    
    private BookMeta meta = mock(BookMeta.class);
    private BookBuilder builder = new BookBuilder((ItemStack) when(mock(ItemStack.class).getItemMeta()).thenReturn(meta).getMock());
    
    
    @Test
    public void build() {
        builder.title("title").author("Pante").generation(COPY_OF_COPY).pages("pg 1", "pg 2");
        
        verify(meta).setTitle("title");
        verify(meta).setAuthor("Pante");
        verify(meta).setGeneration(COPY_OF_COPY);
        verify(meta).addPage("pg 1", "pg 2");
    }
    
}
