/*
 * The MIT License
 *
 * Copyright 2020 Karus Labs.
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
package com.karuslabs.typist.parsers;

import com.karuslabs.typist.Environment;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.lang.model.element.Element;

/**
 * A parser that parses annotations on an element.
 */
public interface Parser {
    
    /**
     * Parses the given annotated elements.
     * 
     * @param environment the environment
     * @param elements the annotated elements
     */
    default void parse(Environment environment, Set<? extends Element> elements) {
        for (var element : elements) {
            parse(environment, element);
        }
    }
    
    /**
     * Parses the given annotated element.
     * 
     * @param environment the environment
     * @param element the annotated element
     */
    void parse(Environment environment, Element element);
    
    /**
     * Returns the annotation that this parser supports.
     * 
     * @return the supported annotation
     */
    Class<? extends Annotation> annotation();
    
}
