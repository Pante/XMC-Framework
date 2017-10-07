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
package com.karuslabs.commons.animation;

import com.karuslabs.commons.annotation.Immutable;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import org.bukkit.Server;
import org.bukkit.boss.*;

import static org.bukkit.boss.BarColor.BLUE;
import static org.bukkit.boss.BarStyle.SEGMENTED_10;


@Immutable
public class BossBarSupplier implements Supplier<BossBar> {
    
    private Server server;
    private String message;
    private BarColor colour;
    private BarStyle style;
    private BarFlag[] flags;
    private double progress;

    
    public BossBarSupplier(Server server, String message, BarColor colour, BarStyle style, double progress, BarFlag... flags) {
        this.server = server;
        this.message = message;
        this.colour = colour;
        this.style = style;
        this.flags = flags;
        this.progress = progress;
    }
    
    
    @Override
    public @Nonnull BossBar get() {
        BossBar bar = server.createBossBar(message, colour, style, flags);
        bar.setProgress(progress);
        return bar;
    }
    
    
    public static Builder builder(Server server) {
        return new Builder(new BossBarSupplier(server, "", BLUE, SEGMENTED_10, 1));
    }
    
    
    public static class Builder {
        
        private BossBarSupplier supplier;
        
        
        private Builder(BossBarSupplier supplier) {
            this.supplier = supplier;
        }
        
        
        public Builder message(String message) {
            supplier.message = message;
            return this;
        }
        
        public Builder colour(BarColor colour) {
            supplier.colour = colour;
            return this;
        }
        
        public Builder style(BarStyle style) {
            supplier.style = style;
            return this;
        }
        
        public Builder flags(BarFlag... flags) {
            supplier.flags = flags;
            return this;
        }
        
        public Builder progress(double progress) {
            supplier.progress = progress;
            return this;
        }
        
        
        public BossBarSupplier build() {
            return supplier;
        }
        
    }

}
