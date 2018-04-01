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
package com.karuslabs.commons.annotation.signature;

import com.karuslabs.commons.annotation.JDK9;

import java.util.*;
import javax.lang.model.element.Modifier;


@FunctionalInterface
public interface Modifiers {
    
    @JDK9("Will be made private one compiling agaisnt JDK 9")
    static final Modifiers ANY = actual -> true;
    
    
    
    public boolean matches(Collection<Modifier> actual);
    
    
    public static Modifiers any() {
        return ANY;
    }
    
    public static Modifiers exactly(Collection<Modifier> expected) {
        return actual -> Collections.disjoint(expected, actual);
    }
    
    public static Modifiers only(Collection<Modifier> expected) {
        return actual -> expected.containsAll(actual);
    }
    
    public static Modifiers except(Collection<Modifier> expected) {
        return actual -> Collections.disjoint(expected, actual);
    }
    
    public static Modifiers none() {
        return Collection::isEmpty;
    }
    
}
