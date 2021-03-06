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
package com.karuslabs.commons.item.builders;

import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A potion builder.
 */
public final class PotionBuilder extends Builder<PotionMeta, PotionBuilder> {
    
    /**
     * Creates a {@code PotionBuilder} for a lingering potion.
     * 
     * @return a {@code PotionBuilder}
     */
    public static PotionBuilder lingering() {
        return new PotionBuilder(Material.LINGERING_POTION);
    }
    
    /**
     * Creates a {@code PotionBuilder} for a potion.
     * 
     * @return a {@code PotionBuilder}
     */
    public static PotionBuilder potion() {
        return new PotionBuilder(Material.POTION);
    }
    
    /**
     * Creates a {@code PotionBuilder} for a splash potion.
     * 
     * @return a {@code PotionBuilder}
     */
    public static PotionBuilder splash() {
        return new PotionBuilder(Material.SPLASH_POTION);
    }
    
    PotionBuilder(Material material) {
        super(material);
    }
    
    PotionBuilder(Builder<ItemMeta, ?> source) {
        super(source);
    }
    
    /**
     * Sets the colour.
     * 
     * @param colour the colour
     * @return {@code this}
     */
    public PotionBuilder colour(@Nullable Color colour) {
        meta.setColor(colour);
        return this;
    }
    
    /**
     * Sets the base potion data.
     * 
     * @param data the data
     * @return {@code this}
     */
    public PotionBuilder data(PotionData data) {
        meta.setBasePotionData(data);
        return this;
    }
    
    /**
     * Adds the given potion effect.
     * 
     * @param effect the potion effect
     * @return {@code this}
     */
    public PotionBuilder effect(PotionEffect effect) {
        meta.addCustomEffect(effect, true);
        return this;
    }
    
    @Override
    PotionBuilder self() {
        return this;
    }
    
}
