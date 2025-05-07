package epicode.exercises;

import epicode.model.Order;
import epicode.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExercises {

    // #1 Books >100
    public static List<Product> booksOver100(List<Product> prods) {
        return prods.stream()
                .filter(p -> p.getCategory().equals("Books") && p.getPrice() > 100)
                .collect(Collectors.toList());
    }

    // #2 Orders con almeno un Baby
    public static List<Order> ordersWithBaby(List<Order> orders) {
        return orders.stream()
                .filter(o -> o.getProducts().stream()
                        .anyMatch(p -> p.getCategory().equals("Baby")))
                .collect(Collectors.toList());
    }

    // #3 Boys con 10% di sconto
    public static List<Product> boysWithDiscount(List<Product> prods) {
        return prods.stream()
                .filter(p -> p.getCategory().equals("Boys"))
                .map(p -> new Product(
                        p.getId(), p.getName(), p.getCategory(), p.getPrice() * 0.9))
                .collect(Collectors.toList());
    }

    // #4 Prodotti di tier-2 tra due date
    public static List<Product> productsByTierBetween(
            List<Order> orders, int tier, LocalDate d1, LocalDate d2) {
        return orders.stream()
                .filter(o -> o.getCustomer().getTier() == tier)
                .filter(o -> !o.getOrderDate().isBefore(d1) && !o.getOrderDate().isAfter(d2))
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}