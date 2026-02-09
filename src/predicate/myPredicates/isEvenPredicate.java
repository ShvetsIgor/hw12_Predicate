package predicate.myPredicates;

import java.util.function.Predicate;

public class isEvenPredicate implements Predicate<Integer> {
    @Override
    public boolean test(Integer integer) {
        return integer != null && integer % 2 == 0;
    }
}
