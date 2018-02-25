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
package com.karuslabs.commons.locale;

import com.karuslabs.commons.locale.providers.Provider;
import com.karuslabs.commons.locale.resources.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BuilderTest {    
    
    @Test
    void build_translation() {
        Translation translation = Translation.builder().bundle("bundle name").provider(Provider.NONE).embedded("embedded").external("external").build();
        ExternalControl control = (ExternalControl) translation.getControl();
        EmbeddedResource embedded = (EmbeddedResource) control.getResources()[0];
        ExternalResource external = (ExternalResource) control.getResources()[1];
        
        assertEquals("bundle name", translation.getBundleName());
        assertEquals(Provider.NONE, translation.getProvider());
        assertEquals("embedded/", embedded.getPath());
        assertEquals("external", external.getPath());
    }
    
    
    @Test
    void build_MessageTranslation() {
        assertEquals(MessageTranslation.class, MessageTranslation.builder().build().getClass());
    }
    
}