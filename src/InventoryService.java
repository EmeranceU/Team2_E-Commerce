import java.util.List;

public class InventoryService {
    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.add(product);
    }

    public Product getProduct(String productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }

    public void updateStock(String productId, int quantity) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.addStock(quantity);
    }

    public boolean checkStockAvailability(String productId, int quantity) throws ProductNotFoundException {
        Product product = getProduct(productId);
        return product.getStockLevel() >= quantity;
    }

    public List<Product> getLowStockProducts(int threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    public List<Product> getAllProducts() {
        return List.copyOf(productRepository.findAll());
    }
}
