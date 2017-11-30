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
package com.karuslabs.commons.animation.particles.effects;

import com.karuslabs.commons.animation.particles.Particles;
import com.karuslabs.commons.animation.particles.effect.*;
import com.karuslabs.commons.annotation.Immutable;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import static com.karuslabs.commons.world.Vectors.randomCircle;
import static java.lang.Math.*;


@Immutable
public class Tornado implements Task<Tornado> {
    
    private Particles tornado;
    private Particles cloud;
    private float height;
    private float radius;
    private float cloudSize;
    private double yOffset;
    private double distance;
    
    
    public Tornado(Particles tornado, Particles cloud) {
        this(tornado, cloud, 5F, 5F, 2.5F, 0.8, 0.375);
    }
    
    public Tornado(Particles tornado, Particles cloud, float height, float radius, float cloudSize, double yOffset, double distance) {
        this.tornado = tornado;
        this.cloud = cloud;
        this.height = height;
        this.radius = radius;
        this.cloudSize = cloudSize;
        this.yOffset = yOffset;
        this.distance = distance;
    }
    
    
    @Override
    public void render(Context context) {
        Location location = context.getOrigin().getLocation();
        
        renderCloud(context, ThreadLocalRandom.current(), location);
        renderTornado(context, location.add(0, 0.2, 0));

        location.subtract(0, yOffset + 0.2, 0);
    }
    
    void renderCloud(Context context, ThreadLocalRandom random, Location location) {
        Vector vector = context.getVector();
        for (int i = 0; i < 100 * cloudSize; i += cloud.getAmount()) {
            randomCircle(vector).multiply(random.nextDouble() * cloudSize);
            context.render(cloud, location, vector.setY(vector.getY() + yOffset));
        }
    }
    
    void renderTornado(Context context, Location location) {
        double r = 0.45 * (radius * (2.35 / height));
        for (double y = 0; y < height; y += distance) {
            double fr = r * y;
            if (fr > radius) {
                fr = radius;
            }
            renderTornadoPortion(context, location, fr, y);
        }
    }
    
    void renderTornadoPortion(Context context, Location location, double radius, double y) {
        Vector vector = context.getVector().setY(y);
        double amount = radius * 64;
        double inc = 2 * PI / amount;
        
        for (int i = 0; i < amount; i += tornado.getAmount()) {
            double angle = i * inc;
            vector.setX(radius * cos(angle)).setZ(radius * sin(angle));
            context.render(tornado, location, vector);
        }
    }


    @Override
    public @Immutable Tornado get() {
        return this;
    }
    
}
