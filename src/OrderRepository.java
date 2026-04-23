import java.util.*;

public class OrderRepository implements Repository<Order, String> {
    private final Map<String, Order> orders;

    public OrderRepository() {
        this.orders = new LinkedHashMap<>();
    }

    @Override
    public void add(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        orders.put(order.getOrderId(), order);
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public Collection<Order> findAll() {
        return Collections.unmodifiableCollection(orders.values());
    }

    @Override
    public boolean exists(String orderId) {
        return orders.containsKey(orderId);
    }

    @Override
    public void remove(String orderId) {
        orders.remove(orderId);
    }

    @Override
    public int count() {
        return orders.size();
    }

    public List<Order> findByCustomerId(String customerId) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public List<Order> findByStatus(OrderStatus status) {
        List<Order> statusOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getStatus() == status) {
                statusOrders.add(order);
            }
        }
        return statusOrders;
    }
}
