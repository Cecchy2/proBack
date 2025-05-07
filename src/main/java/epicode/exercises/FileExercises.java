package epicode.exercises;

import epicode.model.Customer;
import epicode.model.Order;
import epicode.model.Product;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileExercises {

    // #1 Raggruppa ordini per cliente
    public static Map<Customer, List<Order>> ordersByCustomer(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
    }

    // #2 Totale vendite per cliente
    public static Map<Customer, Double> totalSalesByCustomer(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.flatMapping(
                                o -> o.getProducts().stream(),
                                Collectors.summingDouble(Product::getPrice)
                        )
                ));
    }

    // #3 Prodotto più costoso
    public static Optional<Product> mostExpensiveProduct(List<Product> prods) {
        return prods.stream()
                .max(Comparator.comparingDouble(Product::getPrice));
    }

    // #4 Media importi ordini
    public static double averageOrderAmount(List<Order> orders) {
        return orders.stream()
                .mapToDouble(o ->
                        o.getProducts().stream()
                                .mapToDouble(Product::getPrice)
                                .sum()
                )
                .average()
                .orElse(0.0);
    }

    // #5 Somma importi per categoria
    public static Map<String, Double> sumByCategory(List<Product> prods) {
        return prods.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)
                ));
    }

    // EXTRA #6: salva su disco
    public static void saveProductsToDisk(List<Product> prods, String filename) throws IOException {
        String data = prods.stream()
                .map(p -> p.getName() + "@" + p.getCategory() + "@" + p.getPrice())
                .collect(Collectors.joining("#"));
        Path path = Paths.get(filename);
        Files.writeString(path, data, StandardCharsets.UTF_8);
    }

    // EXTRA #7: leggi da disco
    public static List<Product> readProductsFromDisk(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) return Collections.emptyList();
        String data = Files.readString(path, StandardCharsets.UTF_8);
        if (data.isBlank()) return Collections.emptyList();
        return Arrays.stream(data.split("#"))
                .map(tok -> {
                    String[] parts = tok.split("@");
                    return new Product(
                            null,
                            parts[0],
                            parts[1],
                            Double.parseDouble(parts[2])
                    );
                })
                .collect(Collectors.toList());
    }
}