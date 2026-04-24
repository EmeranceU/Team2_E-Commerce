import java.util.Scanner;

public class Main {
    private static final InventoryService inventoryService;
    private static final OrderService orderService;
    private static final Scanner scanner = new Scanner(System.in);

    static {
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        inventoryService = new InventoryService(productRepository);
        orderService = new OrderService(orderRepository, inventoryService);
        
        initializeSampleData();
    }

    public static void main(String[] args) {
        System.out.println("=== E-Commerce Inventory & Order Management System ===\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        viewAllProducts();
                        break;
                    case 3:
                        createAndProcessOrder();
                        break;
                    case 4:
                        viewAllOrders();
                        break;
                    case 5:
                        viewLowStockProducts();
                        break;
                    case 6:
                        running = false;
                        System.out.println("\nThank you for using the system!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println();
        }
        
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Create Order");
        System.out.println("4. View All Orders");
        System.out.println("5. View Low Stock Products");
        System.out.println("6. Exit");
        System.out.println("==========================");
    }

    private static void addProduct() {
        System.out.println("\n--- Add New Product ---");
        String productId = getStringInput("Enter Product ID: ");
        String name = getStringInput("Enter Product Name: ");
        double price = getDoubleInput("Enter Price: ");
        int stock = getIntInput("Enter Stock Level: ");
        
        try {
            Product product = new Product(productId, name, price, stock);
            inventoryService.addProduct(product);
            System.out.println("Product added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add product: " + e.getMessage());
        }
    }

    private static void viewAllProducts() {
        System.out.println("\n--- All Products ---");
        var products = inventoryService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private static void createAndProcessOrder() {
        System.out.println("\n--- Create Order ---");
        String orderId = getStringInput("Enter Order ID: ");
        String customerId = getStringInput("Enter Customer ID: ");
        
        try {
            Order order = orderService.createOrder(orderId, customerId);
            
            while (true) {
                System.out.println("\n1. Add item to order");
                System.out.println("2. Process order");
                System.out.println("3. Cancel order");
                int choice = getIntInput("Choose option: ");
                
                if (choice == 1) {
                    String productId = getStringInput("Enter Product ID: ");
                    int quantity = getIntInput("Enter Quantity: ");
                    try {
                        orderService.addItemToOrder(order, productId, quantity);
                        System.out.println("Item added successfully!");
                    } catch (ProductNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else if (choice == 2) {
                    System.out.println("\nProcessing order...");
                    orderService.processOrder(order);
                    System.out.println("Order processed successfully!");
                    System.out.println("Order Total: RWF " + String.format("%.2f", order.getTotalAmount()));
                    break;
                } else if (choice == 3) {
                    System.out.println("Order cancelled.");
                    break;
                } else {
                    System.out.println("Invalid option.");
                }
            }
            
        } catch (InvalidOrderException e) {
            System.out.println("Order rejected: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("Product error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("Stock error: " + e.getMessage());
        }
    }

    private static void viewAllOrders() {
        System.out.println("\n--- All Orders ---");
        var orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private static void viewLowStockProducts() {
        int threshold = getIntInput("\nEnter stock threshold: ");
        var lowStockProducts = inventoryService.getLowStockProducts(threshold);
        
        System.out.println("\n--- Low Stock Products (below " + threshold + ") ---");
        if (lowStockProducts.isEmpty()) {
            System.out.println("No low stock products found.");
        } else {
            for (Product product : lowStockProducts) {
                System.out.println(product);
            }
        }
        
        CollectionProcessor<Product> processor = new CollectionProcessor<>();
        boolean anyLowStock = processor.anyMatch(lowStockProducts, p -> p.getStockLevel() < 5);
        if (anyLowStock) {
            System.out.println("\nWarning: Some products are critically low (below 5 units)!");
        }
    }

    private static void initializeSampleData() {
        inventoryService.addProduct(new Product("P001", "Laptop", 1200000, 15));
        inventoryService.addProduct(new Product("P002", "Mouse", 35000, 50));
        inventoryService.addProduct(new Product("P003", "Keyboard", 95000, 30));
        inventoryService.addProduct(new Product("P004", "Monitor", 350000, 20));
        inventoryService.addProduct(new Product("P005", "Headphones", 180000, 5));
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
