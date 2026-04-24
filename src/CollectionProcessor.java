import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class CollectionProcessor<T> {
    
    public List<T> filter(Collection<? extends T> collection, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : collection) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public <R> List<R> transform(Collection<? extends T> collection, Transformer<T, R> transformer) {
        List<R> result = new ArrayList<>();
        for (T item : collection) {
            result.add(transformer.transform(item));
        }
        return result;
    }

    public boolean allMatch(Collection<? extends T> collection, Predicate<T> predicate) {
        for (T item : collection) {
            if (!predicate.test(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean anyMatch(Collection<? extends T> collection, Predicate<T> predicate) {
        for (T item : collection) {
            if (predicate.test(item)) {
                return true;
            }
        }
        return false;
    }

    @FunctionalInterface
    public interface Transformer<T, R> {
        R transform(T input);
    }
}
