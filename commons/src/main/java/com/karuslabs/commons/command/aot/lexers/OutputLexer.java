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
package com.karuslabs.commons.command.aot.lexers;

import com.karuslabs.commons.command.aot.*;

import java.util.List;
import java.util.regex.*;
import javax.lang.model.element.Element;

import static java.util.Collections.EMPTY_LIST;


public class OutputLexer implements Lexer {
    
    static final Matcher PACKAGE = Pattern.compile("^([a-zA-Z_]{1}[a-zA-Z]*){2,10}\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,30}((\\.([a-zA-Z_]{1}[a-zA-Z0-9_]*){1,61})*)?$").matcher("");
    static final Matcher FILE = Pattern.compile("([a-zA-Z_$]?)([a-zA-Z\\d_$])*(\\.java)").matcher("");

    
    @Override
    public List<Token> lex(Environment environment, Element location, String folder, String file) {
        if (!PACKAGE.reset(folder).matches()) {
            environment.error(location, "Invalid package name, https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.8");
            return EMPTY_LIST;
        }
        
        if (!FILE.reset(file).matches()) {
            environment.error(location, "Invalid file name, https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.8");
            return EMPTY_LIST;
        }
        
        return List.of(Token.generation(location, folder + "." + file));
    }

}