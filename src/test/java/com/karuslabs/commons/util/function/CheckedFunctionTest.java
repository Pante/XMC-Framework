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
package com.karuslabs.commons.util.function;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;


public class CheckedFunctionTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    
    private CheckedFunction<Object, Object, Exception> function;
    
    
    public CheckedFunctionTest() {
        function = mock(CheckedFunction.class);
    }
    
    
    @Test
    public void uncheck() throws Exception {
        CheckedFunction.uncheck(function).apply(null);
        verify(function).apply(null);
    }
    
    
    @Test
    public void uncheck_ThrowsException() throws Exception {
        exception.expect(UncheckedFunctionException.class);
        
        doThrow(Exception.class).when(function).apply(null);
        
        CheckedFunction.uncheck(function).apply(null);
    }
    
}
