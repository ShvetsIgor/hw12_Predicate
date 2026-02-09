package predicate.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import predicate.MyArray;

import predicate.myPredicates.IsNullPredicate;
import predicate.myPredicates.isEvenPredicate;

import static org.junit.jupiter.api.Assertions.*;


public class MyArrayPredicateTest {

    @Disabled
    @Test
    void example() {
        //Arrange - подготовка
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        //Act - Действией
        int res = arr.indexOf(new isEvenPredicate());
        //Assert - проверка

        assertEquals(1, res);
    }

    //========================== removeIf ===========================

    //@DisplayName("removeIf - true")
    @Test
    void removeIf_removesAllMatching_andReturnsTrue() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);

        boolean changed = arr.removeIf(new isEvenPredicate());

        assertTrue(changed);
        assertEquals(2, arr.size());
        assertEquals(1, arr.get(0));
        assertEquals(3, arr.get(1));
    }

    // @DisplayName("removeIf - False")
    @Test
    void removeIf_returnsFalseWhenNoElementsRemoved() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(3);
        arr.add(5);
        arr.add(7);

        boolean changed = arr.removeIf(new isEvenPredicate());

        assertFalse(changed);
        assertEquals(4, arr.size());
        assertEquals(1, arr.get(0));
        assertEquals(5, arr.get(2));
    }

    @Test
    void removeIf_returnsTrueWhenNullElementsRemoved() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        //null
        //null
        arr.add(5);
        arr.add(7);
        arr.add(9);
        arr.add(1, null);
        arr.add(2, null);
        //всего 6 элементов

        boolean changed = arr.removeIf(new IsNullPredicate<>());

        assertTrue(changed);
        assertEquals(4, arr.size());
        assertEquals(5, arr.get(1));
    }

    // @DisplayName("removeIf - false")
    @Test
    void removeIf_returnsFalseWhenPredicateIsNull() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);

        boolean changed = arr.removeIf(null);

        assertFalse(changed);
        assertEquals(2, arr.size());
        assertEquals(2, arr.get(1));
    }


    // ========================= indexOf =============================
    @Test
    void indexOf_returnsFirstMatchingIndex() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);

        int index = arr.indexOf(new isEvenPredicate());

        assertEquals(1, index);
    }

    @Test
    void indexOf_returnsMinus1WhenNoMatch() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(3);
        arr.add(5);
        arr.add(7);

        int index = arr.indexOf(new isEvenPredicate());

        assertEquals(-1, index);
    }

    @Test
    void indexOf_returnsMinus1WhenPredicateIsNull() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(2);

        int index = arr.indexOf((Integer) null);

        assertEquals(-1, index);
    }

    // ========================= lastIndexOf =============================

    @Test
    void lastIndexOf_returnsFirstMatchingIndex() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);

        int lastIndex = arr.lastIndexOf(new isEvenPredicate());

        assertEquals(3, lastIndex);
    }

    @Test
    void lastIndexOf_returnsMinus1WhenNoMatch() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(3);
        arr.add(5);

        int lastIndex = arr.lastIndexOf(new isEvenPredicate());

        assertEquals(-1, lastIndex);
    }

    @Test
    void lastIndexOf_returnsMinus1WhenPredicateIsNull() {
        MyArray<Integer> arr = new MyArray<>();
        arr.add(1);
        arr.add(2);
        arr.add(5);

        int lastIndex = arr.lastIndexOf((Integer) null);

        assertEquals(-1, lastIndex);
    }
}
