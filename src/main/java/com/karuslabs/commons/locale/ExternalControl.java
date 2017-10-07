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

import com.karuslabs.commons.annotation.*;
import com.karuslabs.commons.locale.resources.Resource;
    
import java.io.*;
import java.util.*;
import java.util.ResourceBundle.Control;
import javax.annotation.*;

import static com.karuslabs.commons.configuration.Configurations.*;
import static java.util.Arrays.*;
import static java.util.Collections.unmodifiableList;


public class ExternalControl extends Control {
    
    @JDK9("UPDATE TO List.of(...) when updating to Java 9")
    public static final @Immutable List<String> FORMATS = unmodifiableList(asList("properties", "yml", "yaml"));
    
    
    private Resource[] resources;
    
    
    public ExternalControl(Resource... resources) {
        this.resources = resources;
    }
    
    
    @Override
    public @Nullable ResourceBundle newBundle(String baseName, Locale locale, String format, @Ignored ClassLoader loader, @Ignored boolean reload) {
        if (getFormats(baseName).contains(format)) {
            String bundle = toResourceName(toBundleName(baseName, locale), format);
            for (Resource resource : resources) {
                if (resource.exists(bundle)) {
                    return load(format, resource.load(bundle));
                }
            }
        }
            
        return null;
    }
    
    @JDK9
    protected @Nullable ResourceBundle load(@Nonnull String format, @Nullable InputStream stream) {
        switch (format) {
            case "properties":
                try (InputStream aStream = stream) {
                    return new PropertyResourceBundle(aStream);

                } catch (IOException e) {
                    return null;
                }
                
            case "yml":
            case "yaml":
                return new CachedResourceBundle(concurrentFlatten(from(stream)));
                
            default:
                return null;
        }
    }
    
    
    @Override
    public @Immutable List<String> getFormats(@Ignored String bundleName) {
        return FORMATS;
    }
    
    
    public @Immutable Resource[] getResources() {
        return copyOf(resources, resources.length);
    }
    
}
