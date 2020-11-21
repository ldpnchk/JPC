package com.lidapinchuk.task01;

import java.util.*;

import static com.lidapinchuk.task01.constants.ExceptionConstants.*;
import static java.lang.String.format;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.copyOfRange;

public class CustomArrayList<T> implements List<T> {

    private Object[] elements;
    private int actualSize;

    public CustomArrayList() {
        elements = new Object[10];
    }

    public CustomArrayList(T[] elements) {
        this.elements = elements;
        actualSize = elements.length;
    }

    public int size() {
        return actualSize;
    }

    public boolean isEmpty() {
        return actualSize == 0;
    }

    public void clear() {
        elements = new Object[10];
        actualSize = 0;
    }

    public boolean contains(Object o) {

        return containsElement(o);
    }

    private boolean containsElement(Object o) {

        if (o == null) {
            for (int i = 0; i < actualSize; i++) {
                if (elements[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < actualSize; i++) {
                if (o.equals(elements[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsAll(Collection<?> c) {

        for (Object o : c) {
            if (!containsElement(o)) {
                return false;
            }
        }

        return true;
    }

    public T get(int index) {

        checkBoundsExclusively(index);

        return (T) elements[index];
    }

    private void checkBoundsExclusively(int index) {

        if (index < 0 || index >= actualSize) {
            throw new IndexOutOfBoundsException(format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }
    }

    private void checkBoundsInclusively(int index) {

        if (index < 0 || index > actualSize) {
            throw new IndexOutOfBoundsException(format(INDEX_OUT_OF_BOUNDS_MESSAGE, index));
        }
    }

    public int indexOf(Object o) {

        return getIndexOfElement(o);
    }

    private int getIndexOfElement(Object o) {

        if (o == null) {
            for (int i = 0; i < actualSize; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < actualSize; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int lastIndexOf(Object o) {

        if (o == null) {
            for (int i = actualSize - 1; i >= 0; i--) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = actualSize - 1; i >= 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public boolean add(T t) {

        increaseSizeIfNecessary(1);

        elements[actualSize] = t;

        actualSize++;
        return true;
    }

    private void increaseSizeIfNecessary(int number) {

        int newActualSize = actualSize + number;

        if (newActualSize > elements.length) {
            int newSize = elements.length;
            while (newSize < newActualSize) {
                newSize *= 2;
            }

            elements = copyOf(elements, newSize);
        }
    }

    public void add(int index, T element) {

        checkBoundsInclusively(index);

        increaseSizeIfNecessary(1);

        moveElementsForward(index, 1);

        elements[index] = element;
        actualSize++;
    }

    private void moveElementsForward(int startIndex, int number) {

        for (int i = actualSize - 1; i >= startIndex; i--) {
            elements[i + number] = elements[i];
        }
    }

    public boolean addAll(Collection<? extends T> c) {

        if (c.isEmpty()) {
            return false;
        }

        increaseSizeIfNecessary(c.size());

        for (Object o : c) {
            elements[actualSize] = o;
            actualSize++;
        }

        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {

        checkBoundsInclusively(index);

        if (c.isEmpty()) {
            return false;
        }

        increaseSizeIfNecessary(c.size());

        moveElementsForward(index, c.size());

        int currentIndex = index;
        for (Object o : c) {
            elements[currentIndex] = o;
            currentIndex++;
        }

        actualSize += c.size();
        return true;
    }

    public T set(int index, T element) {

        checkBoundsExclusively(index);

        Object oldElement = elements[index];
        elements[index] = element;

        return (T) oldElement;
    }

    public boolean remove(Object o) {

        int index = getIndexOfElement(o);
        if (index == -1) {
            return false;
        }

        moveBackward(index, 1);
        trimTail(actualSize - 1);
        return true;
    }

    private void decreaseSizeIfNecessary() {

        int newSize = elements.length / 2;
        if (actualSize <= newSize) {
            elements = copyOf(elements, newSize);
            actualSize = newSize;
        }
    }

    public T remove(int index) {

        checkBoundsExclusively(index);

        Object oldElement = elements[index];

        moveBackward(index, 1);
        trimTail(actualSize - 1);
        return (T) oldElement;
    }

    private void moveBackward(int startIndex, int number) {
        for (int i = startIndex; i < actualSize - 1; i++) {
            elements[i] = elements[i + number];
        }
    }

    public boolean removeAll(Collection<?> c) {

        boolean wasModified = false;
        int lastIndex = 0;
        for (int i = 0; i < actualSize; i++) {
            if (c.contains(elements[i])) {
                wasModified = true;
            } else {
                if (lastIndex != i) {
                    elements[lastIndex] = elements[i];
                }

                lastIndex++;
            }
        }

        if (!wasModified) {
            return false;
        }

        trimTail(lastIndex);
        return true;
    }

    public boolean retainAll(Collection<?> c) {

        boolean wasModified = false;
        int lastIndex = 0;
        for (int i = 0; i < actualSize; i++) {
            if (!c.contains(elements[i])) {
                wasModified = true;
            } else {
                if (lastIndex != i) {
                    elements[lastIndex] = elements[i];
                }

                lastIndex++;
            }
        }

        if (!wasModified) {
            return false;
        }

        trimTail(lastIndex);
        return true;
    }

    private void trimTail(int lastIndex) {

        for (int i = lastIndex; i < actualSize; i++) {
            elements[i] = null;
        }
        actualSize = lastIndex;

        decreaseSizeIfNecessary();
    }

    public Object[] toArray() {
        return copyOf(elements, actualSize);
    }

    public <T1> T1[] toArray(T1[] a) {

        if (a.length < actualSize) {
            return (T1[]) copyOf(elements, actualSize);
        }

        for (int i = 0; i < a.length; i++) {
            if (i < actualSize) {
                a[i] = (T1) elements[i];
            } else {
                a[i] = null;
            }
        }
        return a;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        checkSubListIndexes(fromIndex, toIndex);

        T[] subArray = (T[]) copyOfRange(elements, fromIndex, toIndex);
        return new CustomArrayList<>(subArray);
    }

    private void checkSubListIndexes(int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(format(INDEX_OUT_OF_BOUNDS_MESSAGE, fromIndex));
        } else if (toIndex > actualSize) {
            throw new IndexOutOfBoundsException(format(INDEX_OUT_OF_BOUNDS_MESSAGE, toIndex));
        } else if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException(format(INVALID_INDEXES_MESSAGE, fromIndex, toIndex));
        }
    }

    public Iterator<T> iterator() {
        return new CustomArrayListListIterator();
    }

    public ListIterator<T> listIterator() {
        return new CustomArrayListListIterator();
    }

    private class CustomArrayListListIterator implements ListIterator<T> {

        int currentIndex;
        int previousIndex = -1;

        CustomArrayListListIterator () {

        }

        CustomArrayListListIterator (int index) {
            this.currentIndex = index;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < actualSize - 1;
        }

        @Override
        public T next() {
            if (currentIndex > actualSize - 1) {
                throw new NoSuchElementException(format(NO_SUCH_ELEMENT_MESSAGE, currentIndex));
            }
            previousIndex = currentIndex;
            currentIndex++;
            return (T) elements[previousIndex];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex != 0;
        }

        @Override
        public T previous() {
            if (currentIndex == 0) {
                throw new NoSuchElementException(format(NO_SUCH_ELEMENT_MESSAGE, currentIndex));
            }
            previousIndex = currentIndex;
            currentIndex--;
            return (T) elements[previousIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex + 1;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            if (previousIndex == -1) {
                throw new IllegalStateException(ITERATOR_ILLEGAL_STATE_MESSAGE);
            }

            CustomArrayList.this.remove(previousIndex);
            currentIndex = previousIndex;
            previousIndex = -1;
        }

        @Override
        public void set(T t) {
            if (previousIndex == -1) {
                throw new IllegalStateException(ITERATOR_ILLEGAL_STATE_MESSAGE);
            }

            CustomArrayList.this.set(previousIndex, t);
        }

        @Override
        public void add(T t) {

            int i = currentIndex;
            CustomArrayList.this.add(i, t);
            currentIndex = i + 1;
            previousIndex = -1;
        }
    }

    public ListIterator<T> listIterator(int index) {
        return new CustomArrayListListIterator(index);
    }

}
