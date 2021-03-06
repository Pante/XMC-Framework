/*
 * The MIT License
 *
 * Copyright 2019 Karus Labs.
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
package com.karuslabs.commons.command;

import com.mojang.brigadier.*;
import com.mojang.brigadier.context.*;
import com.mojang.brigadier.tree.CommandNode;

import java.util.*;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.*;


class OptionalContextTest {
    
    static final Object SOURCE = new Object();
    static final Command<Object> COMMAND = context -> 1;
    static final CommandNode<Object> NODE = mock(CommandNode.class);
    static final StringRange RANGE = new StringRange(0, 0);
    static final RedirectModifier<Object> MODIFIER = context -> List.of();
    static final CommandContext<Object> DELEGATE = new CommandContext<>(SOURCE, "", Map.of("argument", new ParsedArgument<>(0, 1, "value")), COMMAND, NODE, List.of(), RANGE, null, MODIFIER, false);
    static final OptionalContext<Object> CONTEXT = new OptionalContext<>(DELEGATE);
    
    
    @Test
    void getOptionalArgument() {
        assertEquals("value", CONTEXT.getOptionalArgument("argument", String.class));
    }
    
    
    @Test
    void getOptionalArgument_null() {
        assertNull(CONTEXT.getOptionalArgument("invalid", String.class));
    }
    
    
    @Test
    void getOptionalArgument_default() {
        assertEquals("value", CONTEXT.getOptionalArgument("argument", String.class, "value"));
    }
    
    
    @Test
    void getOptionalArgument_default_default() {
        assertEquals("default", CONTEXT.getOptionalArgument("invalid", String.class, "default"));
    }
    
    
    @Test
    void delegate() {
        var delegate = mock(CommandContext.class);
        var context = new OptionalContext<>(delegate);
        
        context.copyFor(null);
        verify(delegate).copyFor(null);
        
        context.getChild();
        verify(delegate).getChild();
        
        context.getLastChild();
        verify(delegate).getLastChild();
        
        context.getCommand();
        verify(delegate).getCommand();
        
        context.getSource();
        verify(delegate).getSource();
        
        context.getArgument("name", String.class);
        verify(delegate).getArgument("name", String.class);
        
        context.getRedirectModifier();
        verify(delegate).getRedirectModifier();
        
        context.getRange();
        verify(delegate).getRange();
        
        context.getInput();
        verify(delegate).getInput();
        
        context.getNodes();
        verify(delegate).getNodes();
        
        context.isForked();
        verify(delegate).isForked();
    }
    
    
    @ParameterizedTest
    @MethodSource("equality_parameters")
    void equality(Object other, boolean expected) {
        assertEquals(expected, CONTEXT.equals(other));
    }
    
    
    @ParameterizedTest
    @MethodSource("equality_parameters")
    void hashCode(Object other, boolean expected) {
        assertEquals(expected, CONTEXT.hashCode() == Objects.hashCode(other));
    }
    
    
    static Stream<Arguments> equality_parameters() {
        var context = new CommandContext<>(SOURCE, "", Map.of("argument", new ParsedArgument<>(0, 1, "value")), COMMAND, NODE, List.of(), RANGE, null, MODIFIER, false);
        var other = new OptionalContext<>(context);
        return Stream.of(
            of(CONTEXT, true),
            of(other, true),
            of(null, false)
        );
    }

} 
