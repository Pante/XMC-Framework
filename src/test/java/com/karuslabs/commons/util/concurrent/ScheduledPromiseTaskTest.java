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
package com.karuslabs.commons.util.concurrent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ScheduledPromiseTaskTest {
    
    private Runnable runnable = mock(Runnable.class);
    private ScheduledPromiseTask<String> task = spy(ScheduledPromiseTask.of(runnable, "result", 1));
    
    
    @ParameterizedTest
    @CsvSource({"0, -1, 0, 0, false", "0, 1, 1, 0, false", "1, 1, 1, 1, true"})
    public void run(int current, int total, int expected, int callback, boolean done) {
        task.current = current;
        task.total = total;
        
        task.run();
        
        verify(task, times(callback)).callback();
        assertEquals(expected, task.getCurrent());
        assertEquals(done, task.isDone());
    }
    
    
    @Test
    public void run_ThrowsException() {
        doThrow(Exception.class).when(runnable).run();
        
        task.run();
        
        verify(task).callback();
        assertEquals(Exception.class, task.thrown.getClass());
        assertTrue(task.isDone());
    }
    
}
