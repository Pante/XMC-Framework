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
package com.karuslabs.typist.annotations;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Signifies that the annotated parameter is automatically resolved using an argument. 
 * Aforementioned argument must occur before the command(s) to which the enclosing
 * method of the annotated parameter is bound. If the annotation does not contain
 * an argument name, it is inferred to be the annotated parameter's name.
 */
@Documented
@Retention(SOURCE)
@Target(PARAMETER)
public @interface Let {

    /**
     * A value which signifies that the argument's name should be inferred from
     * the annotated parameter's name.
     */
    static String INFERRED_ARGUMENT = "${inferred argument}";
    
    /**
     * The name of the argument used to resolved this annotated parameter.
     * 
     * @return the argument's name
     */
    String value() default INFERRED_ARGUMENT;
    
}
