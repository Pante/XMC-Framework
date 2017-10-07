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

import com.karuslabs.commons.command.*;
import com.karuslabs.commons.command.completion.Completion;
import com.karuslabs.commons.locale.MessageTranslation;

import java.util.*;
import javax.annotation.Nonnull;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;


public class CommandElement extends Element<Command> {
    
    private Plugin plugin;
    private Element<Map<String, Command>> subcommands;
    private Element<MessageTranslation> translation;
    private Element<Map<Integer, Completion>> completions;
    
    
    public CommandElement(Plugin plugin, Element<Map<String, Command>> subcommands, Element<MessageTranslation> translation, Element<Map<Integer, Completion>> completions) {
        this(plugin, subcommands, translation, completions, new HashMap<>());
    }
    
    public CommandElement(Plugin plugin, Element<Map<String, Command>> subcommands, Element<MessageTranslation> translation, Element<Map<Integer, Completion>> completions, Map<String, Command> declarations) {
        super(declarations);
        this.plugin = plugin;
        this.subcommands = subcommands;
        this.translation = translation;
        this.completions = completions;
    }

    
    @Override
    protected boolean check(@Nonnull ConfigurationSection config, @Nonnull String key) {
        return config.isConfigurationSection(key);
    }

    @Override
    protected @Nonnull Command handle(@Nonnull ConfigurationSection config, @Nonnull String key) {
        config = config.getConfigurationSection(key);
        Command command = new Command(
            config.getName(), plugin, translation.parse(config, "translation"),
            config.getString("description", ""),
            config.getString("usage", ""),
            config.getStringList("aliases"),
            CommandExecutor.NONE,
            subcommands.parse(config, "subcommands"),
            completions.parse(config, "completions")
        );
        command.setPermission(config.getString("permission", ""));
        command.setPermissionMessage(config.getString("permission-message", ""));
        
        return command;
    }
    
}
