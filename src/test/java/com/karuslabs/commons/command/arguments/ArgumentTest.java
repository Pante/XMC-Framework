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
package com.karuslabs.commons.command.arguments;

import java.util.function.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;


@TestInstance(PER_CLASS)
public class ArgumentTest {

    private static Argument argument = new Argument("text");
    
    
    @Test
    public void match() {
        Predicate<String> predicate = when(mock(Predicate.class).test("text")).thenReturn(true).getMock();
        
        assertTrue(argument.match(predicate));
        verify(predicate).test("text");
    }
    
    
    @Test
    public void as() {
        Function<String, Boolean> function = when(mock(Function.class).apply("text")).thenReturn(true).getMock();
        
        assertTrue(argument.as(function));
        verify(function).apply("text");
    }
    
    
    @ParameterizedTest
    @MethodSource("asOrDefault_parameters")
    public void asOrDefault(String returned, String expected) {
        Function<String, Object> function = when(mock(Function.class).apply("text")).thenReturn(returned).getMock();
        
        assertEquals(expected, argument.asOrDefault(function, "value"));
        verify(function).apply("text");
    }
    
    static Stream<Arguments> asOrDefault_parameters() {
        return Stream.of(of("returned", "returned"), of(null, "value"));
    }
    
    
    @Test
    public void asOrThrow() {
        Function<String, Boolean> function = when(mock(Function.class).apply("text")).thenReturn(true).getMock();
        
        assertTrue(argument.asOrThrow(function, null));
        verify(function).apply("text");
    }
    
    
    @Test
    public void asOrThrow_ThrowsException() {
        Function<String, Boolean> function = when(mock(Function.class).apply("text")).thenReturn(null).getMock();
        Supplier<IllegalArgumentException> supplier = IllegalArgumentException::new;
        
        assertThrows(IllegalArgumentException.class, () -> argument.asOrThrow(function, supplier));
    }
    
}
