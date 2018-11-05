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
package com.karuslabs.commons.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;

import java.util.*;

import net.minecraft.server.v1_13_R2.*;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;


public class Dispatcher implements Listener {
    
    public static Dispatcher of(Plugin plugin) {
        var dispatcher = new Dispatcher(plugin);
        plugin.getServer().getPluginManager().registerEvents(dispatcher, plugin);
        
        return dispatcher;
    }
    
    
    private CraftServer server;
    private CommandDispatcher dispatcher;
    private CommandDispatcher vanilla;
    private List<CommandNode<CommandListenerWrapper>> commands;
    
    
    protected Dispatcher(Plugin plugin) {
        this.server = (CraftServer) plugin.getServer();
        this.dispatcher = server.getServer().commandDispatcher;
        this.vanilla = server.getServer().vanillaCommandDispatcher;
        this.commands = new ArrayList<>();
    }
    
    
    public <Builder extends ArgumentBuilder<?, Builder>> Dispatcher add(Builder builder) {
        return add(builder.build());
    }
    
    public Dispatcher add(CommandNode<?> command) {
        var child = (CommandNode<CommandListenerWrapper>) command;
        dispatcher.a().getRoot().addChild(child);
        vanilla.a().getRoot().addChild(child);
        commands.add(child);
        return this;
    }
    
         
    public void update() {
        for (var player : server.getHandle().players) {
            dispatcher.a(player);
        }
    }
    
    
    @EventHandler
    protected void load(ServerLoadEvent event) {
        var root = dispatcher.a().getRoot();
        for (var command : commands) {
            root.addChild(command);
        }
        
        Dispatcher.this.update();
    }
    
    @EventHandler
    protected void update(PlayerJoinEvent event) {
        var player = ((CraftPlayer) event.getPlayer()).getHandle();
        dispatcher.a(player);
    }
    
}