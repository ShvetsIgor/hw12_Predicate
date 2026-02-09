package predicate.myPredicates;

import java.util.function.Predicate;

public class IsNullPredicate<E> implements Predicate<E> {
    @Override
    public boolean test(E o) {


        return o == null;
    }
}
