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
package com.karuslabs.commons.locale;

import org.junit.jupiter.api.*;

import static com.karuslabs.commons.locale.CachedResourceBundle.NONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;


@TestInstance(PER_CLASS)
public class CachedResourceBundleTest {
    
    private CachedResourceBundle bundle;
    
    
    public CachedResourceBundleTest() {
        bundle = new CachedResourceBundle();
        bundle.getMessages().put("key", "message");
    }
    
    
    @Test
    public void none_handleGetObject() {
        assertEquals("key", NONE.handleGetObject("key"));
    }
    
    
    @Test
    public void none_getKeys() {
        assertSame(NONE.getKeys(), NONE.getKeys());
    }
    
    
    @Test
    public void getKeys() {
        assertEquals("key", bundle.getKeys().nextElement());
    }
    
}
