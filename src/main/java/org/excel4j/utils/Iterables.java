package org.excel4j.utils;

public class Iterables {

    public static <T> T fistItemOrDefault(Iterable<T> iterable, T def) {
        return iterable.iterator().hasNext() ? iterable.iterator().next() : def;
    }
}
