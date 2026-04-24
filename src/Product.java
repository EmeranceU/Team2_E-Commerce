package Ecommerce;

import java.util.Objects;

public class Product {
    private final String productId;
    private String name;
    private double price;
    private int stockLevel;

    public Product(String productId, String name, double price, int stockLevel) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stockLevel < 0) {
            throw new IllegalArgumentException("Stock level cannot be negative");
        }
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void reduceStock(int quantity) throws InsufficientStockException {
        if (quantity > stockLevel) {
            throw new InsufficientStockException(
                "Insufficient stock for product " + name + ". Available: " + stockLevel + ", Requested: " + quantity
            );
        }
        this.stockLevel -= quantity;
    }

    public void addStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Cannot add negative stock");
        }
        this.stockLevel += quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockLevel=" + stockLevel +
                '}';
    }
}
