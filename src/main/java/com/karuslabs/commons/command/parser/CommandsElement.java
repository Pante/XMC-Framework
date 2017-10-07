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
package com.karuslabs.commons.command.parser;

import com.karuslabs.commons.annotation.Ignored;
import com.karuslabs.commons.command.Command;

import java.util.*;
import javax.annotation.*;

import org.bukkit.configuration.ConfigurationSection;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;


public class CommandsElement extends Element<Map<String, Command>> {

    private Element<Command> command;
    
    
    public CommandsElement(@Nullable Element<Command> command) {
        this(command, new HashMap<>());
    }
    
    public CommandsElement(@Nullable Element<Command> command, Map<String, Map<String, Command>> declarations) {
        super(declarations);
        this.command = command;
    }
    
    
    @Override
    protected @Nonnull Map<String, Command> handleNull(@Ignored ConfigurationSection config, @Ignored String key) {
        return new HashMap<>(0);
    }
    
    @Override
    protected boolean check(@Nonnull ConfigurationSection config, @Nonnull String key) {
        return config.isConfigurationSection(key);
    }

    @Override
    protected @Nonnull Map<String, Command> handle(@Nonnull ConfigurationSection config, @Nonnull String key) {
        ConfigurationSection subcommands = config.getConfigurationSection(key);
        return subcommands.getKeys(false).stream().collect(toMap(identity(), aKey -> {
            String declared = subcommands.getString(aKey);
            if (declared != null && declared.equals("declared")) {
                return command.getDeclared(subcommands, aKey);
                
            } else {
                return command.parse(subcommands, aKey);
            }
        }));
    }

    
    public @Nullable Element<Command> getCommandElement() {
        return command;
    }

    public void setCommandElement(Element<Command> command) {
        this.command = command;
    }
    
}
