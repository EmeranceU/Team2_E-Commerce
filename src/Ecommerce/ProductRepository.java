package Ecommerce;

import java.util.*;

public class ProductRepository implements Repository<Product, String> {
    private final Map<String, Product> products;

    public ProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    public void add(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.put(product.getProductId(), product);
    }

    @Override
    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public Collection<Product> findAll() {
        return Collections.unmodifiableCollection(products.values());
    }

    @Override
    public boolean exists(String productId) {
        return products.containsKey(productId);
    }

    @Override
    public void remove(String productId) {
        products.remove(productId);
    }

    @Override
    public int count() {
        return products.size();
    }

    public List<Product> findLowStockProducts(int threshold) {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getStockLevel() < threshold) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
    }
}
