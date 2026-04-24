import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {
    void add(T entity);
    Optional<T> findById(ID id);
    Collection<T> findAll();
    boolean exists(ID id);
    void remove(ID id);
    int count();
}
