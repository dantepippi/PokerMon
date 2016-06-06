package com.dstudios.pokermon;

import java.util.Arrays;

/**
 * Created by pcf-dante on 02/06/2016.
 */

class MyList<E> {
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];

    MyList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    int getSize() {
        return size;
    }
    void add(E e) {
        if (size == elements.length) {
            ensureCapa();
        }
        elements[size++] = e;
    }


    private void ensureCapa() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i>= size || i <0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + size);
        }
        return (E) elements[i];
    }
}
