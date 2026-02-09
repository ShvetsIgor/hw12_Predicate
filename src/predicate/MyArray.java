package predicate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

public class MyArray<E> implements IMyArray<E>, Iterable<E> {
	private static final int CAPACITY = 16;
	private Object[] array;
	private int size = 0;

	public MyArray(int capacity) {
		array = new Object[capacity];
		// Object[] array=new Object[capacity];
	}

	public MyArray() {
		this(CAPACITY);
	}

	@Override
	public boolean add(E obj) {
		if (obj == null)
			return false;
		if (size == array.length)
			allocateArray();
		array[size++] = obj;
		return true;
	}

	private void allocateArray() {
		array = Arrays.copyOf(array, array.length * 2);

	}

	@Override
	public boolean add(int index, E obj) {
		if (index < 0 || index > size)
			return false;
		if (index == size)
			return add(obj);
		if (size == array.length)
			allocateArray();
		System.arraycopy(array, index, array, index + 1, size - index);
		// 1 3 5 6 7 (4)=> 2(4) => 1 3 5 5 6 7=> 1 3 4 5 6 7
		array[index] = obj;
		size++;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index < 0 || index >= size)
			return null;
		return (E) array[index];
	}

	@Override
	public int size() {
		return size;
	}

// obj1 obj2 obj3 => set(obj2)=null 
	@Override
	public int indexOf(E obj) {
		if (obj == null) {
			for (int i = 0; i < size; i++) {
				if (array[i] == null)
					return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (obj.equals(array[i]))
					return i;
			}
		}

		return -1;
	}

	@Override
	public int lastIndexOf(E obj) {
		if (obj == null) {
			for (int i = size - 1; i >= 0; i--) {
				if (array[i] == null)
					return i;
			}
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (obj.equals(array[i]))
					return i;
			}
		}

		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			return null;
		E res = (E) array[index];
		if (index < size - 1) {
			System.arraycopy(array, index + 1, array, index, size - index - 1);
		}
		// 1 3 4 5 6 7=> 1 3 5 6 7 7
		array[size - 1] = null;// --size
		size--;
		return res;
	}

	@Override
	public boolean remove(E obj) {
		int index = indexOf(obj);
		if (index < 0)
			return false;
		remove(index);
		return true;
	}

// return obj !=null && indexOf(obj)>=0?remove(index)!=null
	// :obj ==null && indexOf(obj)>=0? remove(index)==null:false;
	@Override
	public boolean contains(E obj) {

		return indexOf(obj) >= 0;
	}

	@Override
	public Object[] toArray() {

		return Arrays.copyOf(array, size);
	}




	// ---------------------- addAll / removeAll ----------------------

    public boolean addAll(MyArray<E> other) {
        // добавляем в конец
        return addAll(size, other);
    }

    public boolean addAll(int index, MyArray<E> other) {
        if (other == null || other.size == 0)
            return false;
        if (index < 0 || index > size)
            return false;

        int otherSize = other.size;

        // гарантируем, что места хватит сразу на всё
        int needed = size + otherSize;
        while (array.length < needed) {
            allocateArray();
        }

        // сдвигаем хвост вправо, освобождая место под otherSize элементов
        System.arraycopy(array, index, array, index + otherSize, size - index);

        // копируем данные из другого массива
        // (имеем доступ к его private-полям, т.к. это тот же класс)
        System.arraycopy(other.array, 0, array, index, otherSize);

        size += otherSize;
        return true;
    }

    public boolean removeAll(MyArray<E> other) {
        if (other == null || other.size == 0 || size == 0)
            return false;

        boolean changed = false;
        int newSize = 0;

        // переписываем только те элементы, которых НЕТ в other
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
			E elem = (E) array[i];
            if (!other.contains(elem)) {
                array[newSize++] = array[i];
            } else {
                changed = true;
            }
        }

        // обнуляем "хвост"
        for (int i = newSize; i < size; i++) {
            array[i] = null;
        }

        size = newSize;
        return changed;
    }
    public boolean removeAllStrict(MyArray<E> other) {
        // нет второго массива / он пустой / текущий пустой
        if (other == null || other.size() == 0 || size == 0) {
            return false;
        }

        // 1. Проверяем, что КАЖДЫЙ элемент other есть в текущем массиве
        //    (по contains, без учёта количества повторов)
        for (int i = 0; i < other.size(); i++) {
            @SuppressWarnings("unchecked")
			E elem = (E) other.get(i);
            if (!this.contains(elem)) {
                // Нашёлся элемент, которого в "this" нет — ничего не трогаем
                return false;
            }
        }

        // 2. Все элементы присутствуют => можно безопасно удалять
        //    Используем уже реализованный removeAll
        return removeAll(other);
    }
    
    public MyArray<E> removeAllAndReturn(MyArray<E> other) {
        MyArray<E> removed = new MyArray<>();

        if (other == null || other.size == 0 || size == 0) {
            return removed; // пустой список удалённых
        }

        int newSize = 0;

        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
			E elem = (E) array[i];
            if (other.contains(elem)) {
                // элемент есть во "втором" массиве -> удаляем его и запоминаем
                removed.add(elem);
            } else {
                // переносим "хороший" элемент в новую позицию
                array[newSize++] = array[i];
            }
        }

        // обнуляем хвост
        for (int i = newSize; i < size; i++) {
            array[i] = null;
        }

        size = newSize;
        return removed;
    }

	@Override
	public Iterator<E> iterator() {
		return new MyArIterator();
	}

	private class MyArIterator implements Iterator<E> {

		int current = 0;

		@Override
		public boolean hasNext() {

			return current < size;
		}

		@Override
		public E next() {

			return (E) array[current++];
		}
	}

	// ========================================================================= HW-12 ================================================================


	@Override
	public boolean removeIf(Predicate<E> predicate) {
		if (predicate == null)
			return false;

		boolean isChanged = false;

		for (int i = 0; i < size; i++) {
			if (predicate.test((E) array[i])) {
				remove(i);
				isChanged = true;
				i--;
			}
		}
		return isChanged;
	}

	@Override
	public int indexOf(Predicate<E> predicate) {
		if (predicate == null)
			return -1;

		for (int i = 0; i < size; i++) {
			if (predicate.test((E) array[i]))
				return i;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Predicate<E> predicate) {

		if (predicate == null)
			return -1;

		for (int i = size-1; i >= 0; i--) {
			if (predicate.test((E) array[i]))
				return i;
		}
		return -1;
	}
}
