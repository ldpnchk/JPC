package com.lidapinchuk;

import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.*;

public class CustomArrayListTest {

    CustomArrayList<String> emptyList;
    CustomArrayList<String> filledList;

    @Before
    public void initLists() {
        emptyList = new CustomArrayList<>();
        filledList = new CustomArrayList<>(new String[]{
                "James", "Anna", null, "Tom", "Anna", "Emma", null, "Anna", "Victor", "Tom"
        });
    }

    @Test
    public void testSize_1() {

        int actualResult = emptyList.size();

        assertEquals(0, actualResult);
    }

    @Test
    public void testSize_2() {

        int actualResult = filledList.size();

        assertEquals(10, actualResult);
    }

    @Test
    public void testIsEmpty_1() {

        boolean actualResult = emptyList.isEmpty();

        assertTrue(actualResult);
    }

    @Test
    public void testIsEmpty_2() {

        boolean actualResult = filledList.isEmpty();

        assertFalse(actualResult);
    }

    @Test
    public void testClear() {

        filledList.clear();

        assertTrue(filledList.isEmpty());
        assertEquals(0, filledList.size());
    }

    @Test
    public void testContains_1() {

        boolean actualResult = filledList.contains("Anna");

        assertTrue(actualResult);
    }

    @Test
    public void testContains_2() {

        boolean actualResult = filledList.contains("Jane");

        assertFalse(actualResult);
    }

    @Test
    public void testContains_3() {

        boolean actualResult = filledList.contains(null);

        assertTrue(actualResult);
    }

    @Test
    public void testContainsAll_1() {

        boolean actualResult = filledList.containsAll(asList("Anna", "Emma", null));

        assertTrue(actualResult);
    }

    @Test
    public void testContainsAll_2() {

        boolean actualResult = filledList.containsAll(asList("Anna", "Jane"));

        assertFalse(actualResult);
    }

    @Test
    public void testGet_1() {

        String actualResult = filledList.get(3);

        assertEquals("Tom", actualResult);
    }

    @Test
    public void testGet_2() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.get(-1);
            }
        });
    }

    @Test
    public void testGet_3() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.get(10);
            }
        });
    }

    @Test
    public void testIndexOf_1() {

        int actualResult = filledList.indexOf("Tom");

        assertEquals(3, actualResult);
    }

    @Test
    public void testIndexOf_2() {

        int actualResult = filledList.indexOf("Jane");

        assertEquals(-1, actualResult);
    }

    @Test
    public void testIndexOf_3() {

        int actualResult = filledList.indexOf(null);

        assertEquals(2, actualResult);
    }

    @Test
    public void testLastIndexOf_1() {

        int actualResult = filledList.lastIndexOf("Tom");

        assertEquals(9, actualResult);
    }

    @Test
    public void testLastIndexOf_2() {

        int actualResult = filledList.lastIndexOf("Jane");

        assertEquals(-1, actualResult);
    }

    @Test
    public void testLastIndexOf_3() {

        int actualResult = filledList.lastIndexOf(null);

        assertEquals(6, actualResult);
    }

    @Test
    public void testAdd_1() {

        boolean actualResult = filledList.add("Jane");

        assertTrue(actualResult);
        assertEquals(11, filledList.size());
        assertEquals("Jane", filledList.get(10));
    }

    @Test
    public void testAddIndex_1() {

        filledList.add(2, "Jane");

        assertEquals(11, filledList.size());
        assertEquals("Jane", filledList.get(2));
    }

    @Test
    public void testAddIndex_2() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.add(11, "Jane");
            }
        });
    }

    @Test
    public void testAddIndex_3() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.add(-1, "Jane");
            }
        });
    }

    @Test
    public void testAddAll_1() {

        boolean actualResult = filledList.addAll(asList("Jane", null));

        assertTrue(actualResult);
        assertEquals(12, filledList.size());
        assertEquals("Jane", filledList.get(10));
        assertEquals(null, filledList.get(11));
    }

    @Test
    public void testAddAll_2() {

        boolean actualResult = filledList.addAll(EMPTY_LIST);

        assertFalse(actualResult);
        assertEquals(10, filledList.size());
    }

    @Test
    public void testAddAll_3() {

        assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.addAll(null);
            }
        });
    }

    @Test
    public void testAddAllIndex_1() {

        boolean actualResult = filledList.addAll(9, asList("Jane", null));

        assertTrue(actualResult);
        assertEquals(12, filledList.size());
        assertEquals("Jane", filledList.get(9));
        assertEquals(null, filledList.get(10));
    }

    @Test
    public void testAddAllIndex_2() {

        boolean actualResult = filledList.addAll(EMPTY_LIST);

        assertFalse(actualResult);
        assertEquals(10, filledList.size());
    }

    @Test
    public void testAddAllIndex_3() {

        assertThrows(NullPointerException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.addAll(0, null);
            }
        });
    }

    @Test
    public void testSet_1() {

        String actualResult = filledList.set(9, "Jane");

        assertEquals("Tom", actualResult);
        assertEquals(10, filledList.size());
        assertEquals("Jane", filledList.get(9));
    }

    @Test
    public void testSet_2() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.set(10, "Jane");
            }
        });
    }

    @Test
    public void testSet_3() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.set(-1, "Jane");
            }
        });
    }

    @Test
    public void testRemove_1() {

        boolean actualResult = filledList.remove("Tom");

        assertTrue(actualResult);
        assertEquals(9, filledList.size());
        assertEquals("Anna", filledList.get(3));
    }

    @Test
    public void testRemove_2() {

        boolean actualResult = filledList.remove("Jane");

        assertFalse(actualResult);
        assertEquals(10, filledList.size());
    }

    @Test
    public void testRemove_3() {

        boolean actualResult = filledList.remove(null);

        assertTrue(actualResult);
        assertEquals(9, filledList.size());
        assertEquals("Tom", filledList.get(2));
    }

    @Test
    public void testRemoveByIndex_1() {

        String actualResult = filledList.remove(9);

        assertEquals("Tom", actualResult);
        assertEquals(9, filledList.size());
    }

    @Test
    public void testRemoveByIndex_2() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.remove(10);
            }
        });
    }

    @Test
    public void testRemoveByIndex_3() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.remove(-1);
            }
        });
    }

    @Test
    public void testRemoveAll_1() {

        boolean actualResult = filledList.removeAll(asList("Tom", null, "Victor"));

        assertTrue(actualResult);
        assertEquals(5, filledList.size());
        assertFalse(filledList.contains("Tom") || filledList.contains(null) || filledList.contains("Victor"));
    }

    @Test
    public void testRemoveAll_2() {

        boolean actualResult = filledList.removeAll(asList("Jane", "Jack"));

        assertFalse(actualResult);
        assertEquals(10, filledList.size());
    }

    @Test
    public void testRetainAll_1() {

        boolean actualResult = filledList.retainAll(asList("Tom", "Anna", null));

        assertTrue(actualResult);
        assertEquals(7, filledList.size());
        assertFalse(filledList.contains("James") || filledList.contains("Emma") || filledList.contains("Victor"));
    }

    @Test
    public void testRetainAll_2() {

        boolean actualResult = filledList.retainAll(asList("Tom", "Anna", null, "James", "Emma", "Victor"));

        assertFalse(actualResult);
        assertEquals(10, filledList.size());
    }

    @Test
    public void testToArray_1() {

        Object[] actualResultArray = emptyList.toArray();

        assertArrayEquals(new Object[]{}, actualResultArray);
    }

    @Test
    public void testToArray_2() {

        Object[] actualResultArray = filledList.toArray();

        assertArrayEquals(new Object[]{
                "James", "Anna", null, "Tom", "Anna", "Emma", null, "Anna", "Victor", "Tom"
        }, actualResultArray);
    }

    @Test
    public void testToArrayExisting_1() {

        Object[] actualResultArray = emptyList.toArray(new Object[]{});

        assertArrayEquals(new Object[]{}, actualResultArray);
    }

    @Test
    public void testToArrayExisting_2() {

        Object[] actualResultArray = emptyList.toArray(new Object[]{1, 2, 3});

        assertArrayEquals(new Object[]{null, null, null}, actualResultArray);
    }

    @Test
    public void testToArrayExisting_3() {

        Object[] actualResultArray = filledList.toArray(new Object[]{
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"
        });

        assertArrayEquals(new Object[]{
                "James", "Anna", null, "Tom", "Anna", "Emma", null, "Anna", "Victor", "Tom", null, null
        }, actualResultArray);
    }

    @Test
    public void testToArrayExisting_4() {

        Object[] actualResultArray = filledList.toArray(new Object[]{"a", "b", "c"});

        assertArrayEquals(new Object[]{
                "James", "Anna", null, "Tom", "Anna", "Emma", null, "Anna", "Victor", "Tom"
        }, actualResultArray);
    }

    @Test
    public void testSubList_1() {

        List<String> actualResult = filledList.subList(2, 5);

        assertEquals(3, actualResult.size());
        assertEquals(null, actualResult.get(0));
        assertEquals("Tom", actualResult.get(1));
        assertEquals("Anna", actualResult.get(2));
    }

    @Test
    public void testSubList_2() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.subList(2, 1);
            }
        });
    }

    @Test
    public void testSubList_3() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.subList(-1, 2);
            }
        });
    }

    @Test
    public void testSubList_4() {

        assertThrows(IndexOutOfBoundsException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                filledList.subList(2, 11);
            }
        });
    }

    @Test
    public void testIteratorHasNext_1() {

        Iterator<String> listIterator = filledList.iterator();

        assertTrue(listIterator.hasNext());
    }

    @Test
    public void testIteratorHasNext_2() {

        Iterator<String> listIterator = filledList.iterator();

        for (int i = 0; i < 10; i++) {
            listIterator.next();
        }

        assertFalse(listIterator.hasNext());
    }

    @Test
    public void testIteratorHasNext_3() {

        Iterator<String> listIterator = emptyList.iterator();

        assertFalse(listIterator.hasNext());
    }

    @Test
    public void testIteratorNext_1() {

        Iterator<String> listIterator = filledList.iterator();

        assertEquals("James", listIterator.next());
        assertEquals("Anna", listIterator.next());
    }

    @Test
    public void testIteratorNext_2() {

        final Iterator<String> listIterator = filledList.iterator();

        for (int i = 0; i < 10; i++) {
            listIterator.next();
        }

        assertThrows(NoSuchElementException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                listIterator.next();
            }
        });
    }

    @Test
    public void testIteratorNext_3() {

        final Iterator<String> listIterator = emptyList.iterator();

        assertThrows(NoSuchElementException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                listIterator.next();
            }
        });
    }

    @Test
    public void testIteratorHasPrevious_1() {

        ListIterator<String> listIterator = filledList.listIterator();

        assertFalse(listIterator.hasPrevious());
    }

    @Test
    public void testIteratorHasPrevious_2() {

        ListIterator<String> listIterator = filledList.listIterator();

        listIterator.next();

        assertTrue(listIterator.hasPrevious());
    }

    @Test
    public void testIteratorPrevious_1() {

        final ListIterator<String> listIterator = filledList.listIterator();

        assertThrows(NoSuchElementException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                listIterator.previous();
            }
        });
    }

    @Test
    public void testIteratorPrevious_2() {

        ListIterator<String> listIterator = filledList.listIterator();

        listIterator.next();

        assertEquals("Anna", listIterator.previous());
    }

    @Test
    public void testIteratorNextIndex_1() {

        ListIterator<String> listIterator = filledList.listIterator();

        assertEquals(1, listIterator.nextIndex());
    }

    @Test
    public void testIteratorPreviousIndex_1() {

        ListIterator<String> listIterator = filledList.listIterator();

        assertEquals(-1, listIterator.previousIndex());
    }

    @Test
    public void testIteratorAdd_1() {

        ListIterator<String> listIterator = filledList.listIterator();

        listIterator.add("James");
        assertEquals(11, filledList.size());
        assertEquals("James", filledList.get(0));
    }

    @Test
    public void testIteratorSet_1() {

        final ListIterator<String> listIterator = filledList.listIterator();

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                listIterator.set("Jane");
            }
        });
    }

    @Test
    public void testIteratorSet_2() {

        ListIterator<String> listIterator = filledList.listIterator();

        listIterator.next();
        listIterator.set("Jane");
        assertEquals(10, filledList.size());
        assertEquals("Jane", filledList.get(0));
    }

    @Test
    public void testIteratorRemove_1() {

        final ListIterator<String> listIterator = filledList.listIterator();

        assertThrows(IllegalStateException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                listIterator.remove();
            }
        });
    }

    @Test
    public void testIteratorRemove_2() {

        ListIterator<String> listIterator = filledList.listIterator();

        listIterator.next();
        listIterator.remove();
        assertEquals(9, filledList.size());
        assertEquals("Anna", filledList.get(0));
    }

    @Test
    public void testIteratorByIndex_1() {

        Iterator<String> listIterator = filledList.listIterator(9);

        assertFalse(listIterator.hasNext());
    }

}
