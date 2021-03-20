package com.custom.cache.interceptor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * A Cache key which is stored as key in the cache.
 *
 */

public class CacheKey implements Serializable {

    /**
     * An empty key.
     */
    public static final CacheKey EMPTY = new CacheKey();

    private final Object[] params;

    // Effectively final, just re-calculated on deserialization
    private transient int hashCode;

    /**
     * Create a new {@link CacheKey} instance.
     * @param elements the elements of the key
     */
    public CacheKey(Object... elements) {
        this.params = elements.clone();
        // Pre-calculate hashCode field
        this.hashCode = Arrays.deepHashCode(this.params);
    }

    @Override
    public boolean equals(Object other) {
        return (this == other ||
                (other instanceof CacheKey && Arrays.deepEquals(this.params, ((CacheKey) other).params)));
    }

    @Override
    public String toString() {
        return "[ FileId= " + params[0] +
                ", FileVersion= " + params[1] +
                " ]";
    }

    @Override
    public final int hashCode() {
        return this.hashCode;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.hashCode = Arrays.deepHashCode(this.params);
    }
}