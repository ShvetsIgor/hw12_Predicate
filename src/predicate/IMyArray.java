package predicate;

import java.util.function.Predicate;

public interface IMyArray<E> {
	boolean add(E obj);
	boolean add(int index, E obj);
	
	E get(int index);
	int size();
	
	int indexOf(E obj);
	int lastIndexOf(E obj);
	
	E remove(int index);
	boolean remove(E obj);
	
	boolean contains(E obj);
	
	Object[] toArray();

	boolean removeIf(Predicate<E> predicate);

	int indexOf(Predicate<E> predicate);

	int lastIndexOf(Predicate<E> predicate);

}
