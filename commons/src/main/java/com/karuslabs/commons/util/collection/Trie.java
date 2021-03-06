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
package com.karuslabs.commons.util.collection;

import com.karuslabs.annotations.Lazy;

import java.util.*;
import java.util.function.Function;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A {@code Trie} that associates a value with a string. The average time complexity 
 * for look-up is {@code O(m)} where {@code m} is the length of the string to which 
 * a value is mapped.
 * <br><br>
 * <b>Implementation details:</b><br>
 * An entry is created for each character in a string. It contains an array and 
 * map of children entries, both of which are lazily initialised. Visible ASCII 
 * characters are used as an index in the array of children entries while other 
 * characters are used as a key in the map of children entries. Entries are traversed
 * based on the next character of a string. This makes the time complexity of look-up 
 * and other operations relative to the length of the string.
 * 
 * @param <V> the type of the values
 */
public class Trie<V> extends AbstractMap<String, V> {
    
    private final TrieEntry<V> root;
    private int size;
    int modifications;
    @Lazy EntrySet entries;
    @Lazy KeySet keys;
    @Lazy ValueCollection values;
    
    /**
     * Creates a {@code Trie}.
     */
    public Trie() {
        root = new TrieEntry<>((char) 0, null);
        size = 0;
        modifications = 0;
    }
    
    /**
     * Returns the entries whose keys start with the given prefix.
     * 
     * @param prefix the prefix
     * @return the entries whose keys start with the given prefix, or an empty set 
     *         if this trie contains no entries that start with the given prefix
     */
    public Set<Entry<String, V>> prefixEntries(String prefix) {
        return prefixed(prefix, entry -> entry, new HashSet<>());
    }
    
    /**
     * Returns the keys that start with the given prefix.
     * 
     * @param prefix the prefix
     * @return the keys that start with the given prefix, or an empty set if this 
     *         trie contains no keys that start with the given prefix
     */
    public Set<String> prefixedKeys(String prefix) {
        return prefixed(prefix, entry -> entry.getKey(), new HashSet<>());
    }
    
    /**
     * Returns the values whose associated keys start with the given prefix.
     * 
     * @param prefix the prefix
     * @return the values whose associated keys start with the given prefix, or
     *         an empty collection if this trie contains no keys that start with
     *         the given prefix
     */
    public Collection<V> prefixedValues(String prefix) {
        return prefixed(prefix, entry -> entry.getValue(), new ArrayList<>());
    }
    
    /**
     * Recursively maps the entries whose keys start with the prefix to the {@code collection}
     * using the {@code mapper}.
     * 
     * @param <C> the type the collection
     * @param <T> the type of the mapped elements
     * @param prefix the prefix
     * @param mapper the mapper
     * @param collection the collection
     * @return the collection of mapped elements
     */
    <C extends Collection<T>, T> C prefixed(String prefix, Function<Entry<String, V>, T> mapper, C collection) {
        var entry = root;
        for (var character : prefix.toCharArray()) {
            entry = entry.child(character);
            if (entry == null) {
                return collection;
            }
        }
        
        map(entry, mapper, collection);
        return collection;
    }
    
    /**
     * Recursively maps the entries to {@code leaves} using the given {@code mapper}.
     * 
     * @param <C> the type of the collection
     * @param <T> the type of the mapped elements
     * @param entry the current entry
     * @param mapper the mapping function
     * @param leaves the collection
     */
    private <C extends Collection<T>, T> void map(TrieEntry<V> entry, Function<Entry<String, V>, T> mapper, C leaves) {
        if (entry.key != null) {
            leaves.add(mapper.apply(entry));
        }
        
        if (entry.ascii != null) {
            for (var child : entry.ascii) {
                if (child != null) {
                    map(child, mapper, leaves);
                }
            }
        }
        
        if (entry.expanded != null) {
            for (var child : entry.expanded.values()) {
                map(child, mapper, leaves);
            }
        }
    }


    @Override
    public boolean containsValue(Object value) {
        return contains(root, value);
    }
    
    /**
     * Recursively checks if the given entry or its children contains {@code value}.
     * 
     * @param entry the current entry
     * @param value the value
     * @return {@code true} if the {@code value} is present; else {@code false}
     */
    private boolean contains(TrieEntry<V> entry, Object value) {
        if (entry.key != null && Objects.equals(entry.value, value)) {
            return true;
        }
        
        if (entry.ascii != null) {
            for (var child : entry.ascii) {
                if (child != null && contains(child, value)) {
                    return true;
                }
            }
            
        }
        
        if (entry.expanded != null) {
            for (var child : entry.expanded.values()) {
                if (contains(child, value)) {
                    return true;
                }
            }
            
        }
        
        return false;
    }

    
    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }
    
    @Override
    public @Nullable V get(Object key) {
        var entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }
    
    @Nullable TrieEntry<V> getEntry(Object key) {
        if (key == null) {
            throw new NullPointerException("Null keys are not permitted in a trie");
        }
        
        var entry = root;
        for (char character : ((String) key).toCharArray()) {
            entry = entry.child(character);
            if (entry == null) {
                return null;
            }
        }
        
        return entry.getKey() == null ? null : entry;
    }

        
    @Override
    public void putAll(Map<? extends String, ? extends V> map) {
        for (var entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public @Nullable V put(String key, V value) {
        var entry = root;
        var array = key.toCharArray();
        
        int i = 0;
        for (; i < array.length - 1; i++) {
            var next = entry.child(array[i]);
            if (next == null) {
                break;
            }
            
            entry = next;
        }
        
        for (; i < array.length - 1; i++) {
            entry = entry.add(array[i]);
        }
        
        var replaced = entry.set(array[array.length - 1], key, value);
        modifications++;
        
        if (replaced == null) {
            size++;
            return null;
            
        } else {
            return replaced.value;
        }
    }

    
    @Override
    public @Nullable V remove(Object key) {
        var entry = getEntry(key);
        return entry != null ? removeEntry(entry) : null;
    }
    
    private @Nullable V removeEntry(TrieEntry<V> entry) {
        var removed = entry;
        var value = removed.value;
        
        if (entry.children == 0) {
            do {
                entry.parent.remove(entry.character);
                entry = entry.parent;
            } while (entry.key == null && entry != root && entry.parent.children == 1);
            
        } else {
            entry.key = null;
            entry.value = null;
        }
        
        size--;
        modifications++;
        return value;
    }
    

    @Override
    public void clear() {
        size = 0;
        modifications++;
        root.clear();
    }
    
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    
    @Override
    public Set<Entry<String, V>> entrySet() {
        if (entries == null) {
            entries = new EntrySet();
        }
        
        return entries;
    }
    
    @Override
    public Set<String> keySet() {
        if (keys == null) {
            keys = new KeySet();
        }
        
        return keys;
    }

    @Override
    public Collection<V> values() {
        if (values == null) {
            values = new ValueCollection();
        }
        return values;
    }
    
    
    final class EntrySet extends AbstractSet<Entry<String, V>> {
        
        @Override
        public boolean contains(Object object) {
            var entry = (Entry<String, V>) object;
            var found = getEntry(entry.getKey());
            
            return entry.equals(found);
        }
        
        @Override
        public boolean remove(Object object) {
            var other = (Entry<String, V>) object;
            var entry = getEntry(other.getKey());
            
            if (entry != null && Objects.equals(entry.getValue(), other.getValue())) {
                removeEntry(entry);
                return true;
                
            } else {
                return false;
            }
        }
        
        @Override
        public Iterator<Entry<String, V>> iterator() {
            return new EntryIterator();
        }
        
        @Override
        public int size() {
            return size;
        }
        
    }
    
    final class KeySet extends AbstractSet<String> {
        
        @Override
        public boolean contains(Object key) {
            return containsKey(key);
        }
        
        @Override
        public boolean remove(Object key) {
            return Trie.this.remove(key) != null;
        }
        
        @Override
        public Iterator<String> iterator() {
            return new KeyIterator();
        }

        @Override
        public int size() {
            return size;
        }
        
    }
    
    final class ValueCollection extends AbstractCollection<V> {

        @Override
        public boolean contains(Object value) {
            return containsValue(value);
        }
        
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return size;
        }
        
    }
    
    
    abstract class TrieIterator<T> implements Iterator<T> {
        
        int expectedModifications;
        private final Deque<TrieEntry<V>> queue;
        private @Lazy TrieEntry<V> returned;
        
        TrieIterator() {
            expectedModifications = modifications;
            queue = new ArrayDeque<>();
            children(root);
        }
        
        @Override
        public T next() {
             if (expectedModifications != modifications) {
                throw new ConcurrentModificationException();
                
            } else if (queue.isEmpty()) {
                throw new NoSuchElementException();
            }
             
            return get(returned = nextEntry());
        }
        
        private TrieEntry<V> nextEntry() {
            TrieEntry<V> entry;
            do {
                entry = queue.pollLast();
                if (entry.children > 0) {
                    children(entry);
                }
                
            } while (entry.key == null);
            
            return entry;
        }

        private void children(TrieEntry<V> entry) {
            if (entry.ascii != null) {
                for (var child : entry.ascii) {
                    if (child != null) {
                        queue.add(child);
                    }
                }
            }
            
            if (entry.expanded != null) {
                for (var child : entry.expanded.values()) {
                    queue.add(child);
                }
            }
        }
                
        abstract T get(TrieEntry<V> entry);
        
        
        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public void remove() {
            if (expectedModifications != modifications) {
                throw new ConcurrentModificationException();
                
            } else if (returned == null) {
                throw new IllegalStateException();
            }
            
            removeEntry(returned);
            expectedModifications = modifications;
            returned = null;
        }
        
    }
    
    final class EntryIterator extends TrieIterator<Entry<String, V>> {

        @Override
        Entry<String, V> get(TrieEntry<V> entry) {
            return entry;
        }
        
    }
    
    final class KeyIterator extends TrieIterator<String> {

        @Override
        String get(TrieEntry<V> entry) {
            return entry.key;
        }
        
    }
    
    final class ValueIterator extends TrieIterator<V> {

        @Override
        V get(TrieEntry<V> entry) {
            return entry.value;
        }
        
    }
    
}
