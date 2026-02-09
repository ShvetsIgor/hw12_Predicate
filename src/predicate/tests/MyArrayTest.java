package predicate.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import predicate.MyArray;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayTest {

    private MyArray<Integer> numbers;
    private MyArray<String> strings;

    private MyArray<Integer> other1_20;
    private MyArray<Integer> otherEven;

    private final Integer[] arNumbers     = {10, 7, 11, -2, 13, 10, 2000};
    private final String[]  arStrings     = {"abc", "lmn", "fg", "abc"};
    private final Integer[] arEvenNumbers = {10, -2, 10, 2000};
    private final Integer[] arOddNumbers  = {7, 11, 13};

    @BeforeEach
    public void setUp() {
        numbers = new MyArray<>(1);
        for (Integer n : arNumbers) {
            numbers.add(n);
        }

        strings = new MyArray<>();
        for (String s : arStrings) {
            strings.add(s);
        }

        other1_20 = new MyArray<>();
        for (int i = 1; i <= 20; i++) {
            other1_20.add(i);
        }

        otherEven = new MyArray<>();
        for (Integer n : arEvenNumbers) {
            otherEven.add(n);
        }
    }

    // ================== BASIC & EXISTING TESTS ==================

    @Test
    public void setUpTest() {
        int sizeNumbers = numbers.size();
        int sizeStrings = strings.size();

        assertEquals(arNumbers.length, sizeNumbers);
        assertEquals(arStrings.length, sizeStrings);

        // проверяем содержимое numbers
        for (int i = 0; i < sizeNumbers; i++) {
            assertEquals(arNumbers[i], numbers.get(i));
        }

        // проверяем содержимое strings
        for (int i = 0; i < sizeStrings; i++) {
            assertEquals(arStrings[i], strings.get(i));
        }
    }

    @Test
    public void indexOfTest() {
        // для чисел
        assertEquals(0, numbers.indexOf(10));
        assertEquals(-1, numbers.indexOf(1000));

        // для строк
        assertEquals(0, strings.indexOf("abc"));
        assertEquals(-1, strings.indexOf("kuku"));

        // проверка с "новым" объектом String
        String abc = new String("abc");
        assertEquals(0, strings.indexOf(abc));

        // проверка поиска 2000
        assertEquals(6, numbers.indexOf(2000));
    }

    @Test
    public void removeAtIndexTest() {
        Integer[] arNumbersNo_2 = {10, 7, 11, 13, 10, 2000};

        assertNull(numbers.remove(60), "remove with invalid index should return null");
        assertEquals(Integer.valueOf(-2), numbers.remove(3),
                "remove(3) should return -2");

        Integer[] actual = new Integer[numbers.size()];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = numbers.get(i);
        }

        assertArrayEquals(arNumbersNo_2, actual,
                "Array after removing element at index 3 is incorrect");
    }

    @Test
    public void removeObjectTest() {
        Integer[] arNumbersNo_2 = {10, 7, 11, 13, 10, 2000};

        // важно: (Integer)(-2), чтобы вызвать remove(E obj), а не remove(int index)
        assertTrue(numbers.remove((Integer) (-2)), "remove(-2) should return true");
        assertFalse(numbers.remove((Integer) (-20)), "remove(-20) should return false");

        assertArrayEquals(arNumbersNo_2, numbers.toArray(),
                "Array after removing -2 is incorrect");

        String abc = new String("abc");
        assertTrue(strings.remove(abc), "remove(\"abc\") should return true");
        assertFalse(strings.remove("kuku"), "remove(\"kuku\") should return false");

        String[] arStrings_abc = {"lmn", "fg", "abc"};
        assertArrayEquals(arStrings_abc, strings.toArray(),
                "Array after removing first \"abc\" is incorrect");
    }

    @Test
    public void addAtIndexTest() {
        Integer[] arNumbers5 = {10, 7, 11, 5, -2, 13, 10, 2000};

        assertTrue(numbers.add(3, 5), "add(3, 5) should return true");

        int sizeNumbers = numbers.size();
        for (int i = 0; i < sizeNumbers; i++) {
            assertEquals(arNumbers5[i], numbers.get(i),
                    "Element at index " + i + " is incorrect");
        }

        // добавление за пределами size — должно вернуть false
        assertFalse(numbers.add(numbers.size() + 1, 100),
                "add(size+1, ...) should return false");
    }

    @Test
    public void containsTest() {
        assertTrue(numbers.contains(2000), "numbers should contain 2000");
        assertFalse(numbers.contains(100), "numbers should not contain 100");

        assertTrue(strings.contains("abc"), "strings should contain \"abc\"");
        assertFalse(strings.contains("kuku"), "strings should not contain \"kuku\"");
    }

    @Test
    public void toArrayTest() {
        assertArrayEquals(arNumbers, numbers.toArray(), "numbers.toArray() is incorrect");
        assertArrayEquals(arStrings, strings.toArray(), "strings.toArray() is incorrect");
    }

    @Test
    public void lastIndexOfTest() {
        assertEquals(5, numbers.lastIndexOf(10), "lastIndexOf(10) should return 5");
        assertEquals(-1, numbers.lastIndexOf(1000), "lastIndexOf(1000) should return -1");

        assertEquals(3, strings.lastIndexOf("abc"), "lastIndexOf(\"abc\") should return 3");
        assertEquals(-1, strings.lastIndexOf("kuku"), "lastIndexOf(\"kuku\") should return -1");

        // доп. проверка indexOf(2000)
        assertEquals(6, numbers.indexOf(2000), "indexOf(2000) should return 6");
    }

    @Test
    public void addAllTest() {
        Integer[] arExpected = {
                10, 7, 11, -2, 13, 10, 2000,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20
        };

        numbers.addAll(other1_20);

        assertArrayEquals(arExpected, numbers.toArray(),
                "addAll(other1_20) should append 1..20 at the end");
    }

    @Test
    public void addAllAtIndexTest() {
        Integer[] arExpected = {
                10, 7, 11,
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                -2, 13, 10, 2000
        };

        numbers.addAll(3, other1_20);

        assertArrayEquals(arExpected, numbers.toArray(),
                "addAll(3, other1_20) should insert 1..20 at index 3");
    }

    @Test
    public void removeAllTest() {
        // сначала удаляем все элементы, которые содержатся в otherEven
        assertTrue(numbers.removeAll(otherEven), "removeAll(otherEven) should return true");
        assertArrayEquals(arOddNumbers, numbers.toArray(),
                "After removeAll(otherEven) only odd numbers should remain");

        // второй вызов removeAll не должен ничего менять и должен вернуть false
        assertFalse(numbers.removeAll(otherEven), "Second removeAll(otherEven) should return false");
    }

    // ================== EXTRA TESTS FOR INTEGER BULK METHODS ==================

    // --------- removeAllStrict (Integer) ---------

    @Test
    public void removeAllStrict_allElementsExist_shouldRemoveAndReturnTrue() {
        // otherEven: {10, -2, 10, 2000} — все присутствуют в numbers
        boolean changed = numbers.removeAllStrict(otherEven);

        assertTrue(changed, "removeAllStrict should return true when all elements exist");
        assertArrayEquals(arOddNumbers, numbers.toArray(),
                "After removeAllStrict(otherEven) only odd numbers should remain");
        assertEquals(arOddNumbers.length, numbers.size(),
                "Size should be equal to oddNumbers.length");
    }

    @Test
    public void removeAllStrict_missingElements_shouldNotChangeAndReturnFalse() {
        // other1_20 содержит элементы, которых нет в numbers
        Integer[] original = Arrays.copyOf(arNumbers, arNumbers.length);

        boolean changed = numbers.removeAllStrict(other1_20);

        assertFalse(changed, "removeAllStrict should return false if at least one element is missing");
        assertArrayEquals(original, numbers.toArray(),
                "Array should not change when precondition is not met");
    }

    @Test
    public void removeAllStrict_emptyOther_shouldReturnFalseAndNotChange() {
        Integer[] original = Arrays.copyOf(arNumbers, arNumbers.length);

        MyArray<Integer> empty = new MyArray<>();
        boolean changed = numbers.removeAllStrict(empty);

        assertFalse(changed, "removeAllStrict(empty) should return false");
        assertArrayEquals(original, numbers.toArray(),
                "Array should not change when other is empty");
    }

    // --------- removeAllAndReturn (Integer) ---------

    @Test
    public void removeAllAndReturn_removeEvenNumbersAndReturnThem() {
        // numbers: [10, 7, 11, -2, 13, 10, 2000]
        // otherEven: [10, -2, 10, 2000]

        MyArray<Integer> removed = numbers.removeAllAndReturn(otherEven);

        // проверяем оставшиеся числа
        assertArrayEquals(arOddNumbers, numbers.toArray(),
                "Remaining elements should be only odd numbers");
        assertEquals(arOddNumbers.length, numbers.size(),
                "Size should be equal to oddNumbers.length");

        // проверяем удалённые
        Integer[] expectedRemoved = {10, -2, 10, 2000};
        assertArrayEquals(expectedRemoved, removed.toArray(),
                "Removed elements should appear in the original order");
        assertEquals(expectedRemoved.length, removed.size(),
                "Removed size should be equal to number of removed elements");
    }

    @Test
    public void removeAllAndReturn_noMatches_returnsEmptyAndDoesNotChange() {
        Integer[] original = Arrays.copyOf(arNumbers, arNumbers.length);

        MyArray<Integer> other = new MyArray<>();
        other.add(999);

        MyArray<Integer> removed = numbers.removeAllAndReturn(other);

        assertEquals(0, removed.size(), "Removed must be empty when no matches");
        assertArrayEquals(original, numbers.toArray(),
                "Array must not change when no matches");
    }

    @Test
    public void removeAllAndReturn_nullOrEmptyOther_returnsEmptyAndDoesNotChange() {
        Integer[] original = Arrays.copyOf(arNumbers, arNumbers.length);

        MyArray<Integer> removedNull = numbers.removeAllAndReturn(null);
        assertEquals(0, removedNull.size(), "Removed for null other must be empty");
        assertArrayEquals(original, numbers.toArray(),
                "Array must not change for null other");

        MyArray<Integer> empty = new MyArray<>();
        MyArray<Integer> removedEmpty = numbers.removeAllAndReturn(empty);
        assertEquals(0, removedEmpty.size(), "Removed for empty other must be empty");
        assertArrayEquals(original, numbers.toArray(),
                "Array must not change for empty other");
    }

    @Test
    public void removeAllAndReturn_withDuplicatesInSource() {
        // numbers: [10, 7, 11, -2, 13, 10, 2000]
        MyArray<Integer> other = new MyArray<>();
        other.add(10); // удаляем все 10

        MyArray<Integer> removed = numbers.removeAllAndReturn(other);

        Integer[] expectedRemaining = {7, 11, -2, 13, 2000};
        Integer[] expectedRemoved = {10, 10};

        assertArrayEquals(expectedRemaining, numbers.toArray(),
                "All occurrences of 10 should be removed from numbers");
        assertArrayEquals(expectedRemoved, removed.toArray(),
                "Removed array should contain all removed duplicates in order");
        assertEquals(2, removed.size(),
                "Removed size should equal number of removed elements");
    }

    // ================== STRING BULK METHODS TESTS ==================

    // --------- addAll / addAll(index) (String) ---------

    @Test
    public void stringAddAll_appendToEnd_success() {
        MyArray<String> other = new MyArray<>();
        other.add("zzz");
        other.add("yyy");

        boolean result = strings.addAll(other);

        assertTrue(result, "addAll with non-empty other should return true");
        assertEquals(arStrings.length + 2, strings.size(),
                "Size should increase by other.size()");

        String[] expected = {"abc", "lmn", "fg", "abc", "zzz", "yyy"};
        assertArrayEquals(expected, strings.toArray(),
                "Elements should be appended at the end in correct order");
    }

    @Test
    public void stringAddAll_withNullOther_returnsFalseAndDoesNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        boolean result = strings.addAll(null);

        assertFalse(result, "addAll(null) should return false");
        assertArrayEquals(original, strings.toArray(),
                "Array must not change when other is null");
    }

    @Test
    public void stringAddAll_withEmptyOther_returnsFalseAndDoesNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> empty = new MyArray<>();
        boolean result = strings.addAll(empty);

        assertFalse(result, "addAll(empty) should return false");
        assertArrayEquals(original, strings.toArray(),
                "Array must not change when other is empty");
    }

    @Test
    public void stringAddAll_atIndexMiddle_success() {
        MyArray<String> other = new MyArray<>();
        other.add("X");
        other.add("Y");

        boolean result = strings.addAll(1, other);

        assertTrue(result, "addAll(1, other) should return true for valid index");
        assertEquals(arStrings.length + 2, strings.size(),
                "Size must increase by other.size()");

        String[] expected = {
                "abc",
                "X", "Y",
                "lmn", "fg", "abc"
        };
        assertArrayEquals(expected, strings.toArray(),
                "Elements must be correctly inserted at index 1");
    }

    @Test
    public void stringAddAll_withInvalidIndex_shouldReturnFalseAndNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> other = new MyArray<>();
        other.add("X");

        boolean negative = strings.addAll(-1, other);
        boolean tooBig = strings.addAll(strings.size() + 1, other);

        assertFalse(negative, "addAll with negative index should return false");
        assertFalse(tooBig, "addAll with index > size should return false");
        assertArrayEquals(original, strings.toArray(),
                "Array must not change for invalid indices");
    }

    // --------- removeAll (String) ---------

    @Test
    public void stringRemoveAll_removeExistingElements() {
        // strings: ["abc", "lmn", "fg", "abc"]
        MyArray<String> other = new MyArray<>();
        other.add("abc");

        boolean changed = strings.removeAll(other);

        assertTrue(changed, "removeAll should return true when at least one element is removed");
        String[] expectedRemaining = {"lmn", "fg"};
        assertArrayEquals(expectedRemaining, strings.toArray(),
                "Array should contain only non-removed strings");
    }

    @Test
    public void stringRemoveAll_noMatches_shouldReturnFalseAndNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> other = new MyArray<>();
        other.add("zzz");

        boolean changed = strings.removeAll(other);

        assertFalse(changed, "removeAll should return false when no elements are removed");
        assertArrayEquals(original, strings.toArray(),
                "Array should not change when there are no matches");
    }

    @Test
    public void stringRemoveAll_withNullOrEmptyOther_shouldReturnFalse() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        boolean changedNull = strings.removeAll(null);
        assertFalse(changedNull, "removeAll(null) should return false");

        MyArray<String> empty = new MyArray<>();
        boolean changedEmpty = strings.removeAll(empty);
        assertFalse(changedEmpty, "removeAll(empty) should return false");

        assertArrayEquals(original, strings.toArray(),
                "Array must not change when other is null or empty");
    }

    // --------- removeAllStrict (String) ---------

    @Test
    public void stringRemoveAllStrict_successWhenAllElementsExist() {
        // strings: ["abc", "lmn", "fg", "abc"]
        MyArray<String> other = new MyArray<>();
        other.add("abc");
        other.add("fg");

        boolean changed = strings.removeAllStrict(other);

        assertTrue(changed, "removeAllStrict should return true when all elements exist");
        String[] expected = {"lmn"};
        assertArrayEquals(expected, strings.toArray(),
                "Only 'lmn' should remain after removeAllStrict");
    }

    @Test
    public void stringRemoveAllStrict_shouldNotChangeWhenAtLeastOneElementIsMissing() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> other = new MyArray<>();
        other.add("abc");
        other.add("zzz"); // "zzz" нет в исходном массиве

        boolean changed = strings.removeAllStrict(other);

        assertFalse(changed,
                "removeAllStrict should return false when at least one element is missing");
        assertArrayEquals(original, strings.toArray(),
                "Array must not change when precondition is not met");
    }

    @Test
    public void stringRemoveAllStrict_withEmptyOther_shouldReturnFalseAndNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> empty = new MyArray<>();
        boolean changed = strings.removeAllStrict(empty);

        assertFalse(changed, "removeAllStrict(empty) should return false");
        assertArrayEquals(original, strings.toArray(),
                "Array must not change when other is empty");
    }

    // --------- removeAllAndReturn (String) ---------

    @Test
    public void stringRemoveAllAndReturn_removeSomeAndReturnThem() {
        // strings: ["abc", "lmn", "fg", "abc"]
        MyArray<String> other = new MyArray<>();
        other.add("abc");
        other.add("fg");

        MyArray<String> removed = strings.removeAllAndReturn(other);

        String[] expectedRemaining = {"lmn"};
        String[] expectedRemoved   = {"abc", "fg", "abc"};

        assertArrayEquals(expectedRemaining, strings.toArray(),
                "Remaining elements should be only 'lmn'");
        assertArrayEquals(expectedRemoved, removed.toArray(),
                "Removed elements should appear in original order");
        assertEquals(expectedRemoved.length, removed.size(),
                "Removed size should equal number of removed elements");
    }

    @Test
    public void stringRemoveAllAndReturn_noMatches_returnsEmptyAndDoesNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> other = new MyArray<>();
        other.add("zzz");

        MyArray<String> removed = strings.removeAllAndReturn(other);

        assertEquals(0, removed.size(), "Removed should be empty when there are no matches");
        assertArrayEquals(original, strings.toArray(),
                "Array should not change when there are no matches");
    }

    @Test
    public void stringRemoveAllAndReturn_nullOrEmptyOther_returnsEmptyAndDoesNotChange() {
        String[] original = Arrays.copyOf(arStrings, arStrings.length);

        MyArray<String> removedNull = strings.removeAllAndReturn(null);
        assertEquals(0, removedNull.size(), "Removed for null other should be empty");
        assertArrayEquals(original, strings.toArray(),
                "Array should not change for null other");

        MyArray<String> empty = new MyArray<>();
        MyArray<String> removedEmpty = strings.removeAllAndReturn(empty);
        assertEquals(0, removedEmpty.size(), "Removed for empty other should be empty");
        assertArrayEquals(original, strings.toArray(),
                "Array should not change for empty other");
    }

    @Test
    public void stringRemoveAllAndReturn_withDuplicatesInSource() {
        // strings: ["abc", "lmn", "fg", "abc"]
        MyArray<String> other = new MyArray<>();
        other.add("abc"); // удаляем все "abc"

        MyArray<String> removed = strings.removeAllAndReturn(other);

        String[] expectedRemaining = {"lmn", "fg"};
        String[] expectedRemoved   = {"abc", "abc"};

        assertArrayEquals(expectedRemaining, strings.toArray(),
                "All occurrences of 'abc' should be removed from strings");
        assertArrayEquals(expectedRemoved, removed.toArray(),
                "Removed array should contain all removed duplicates in order");
        assertEquals(2, removed.size(),
                "Removed size should equal number of removed elements");
    }

}
