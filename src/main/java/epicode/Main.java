package epicode;

import epicode.exercises.FileExercises;
import epicode.exercises.StreamExercises;
import epicode.model.Customer;
import epicode.model.Order;
import epicode.model.Product;
import epicode.service.DataGenerator;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Genera dati
        List<Customer> custs = DataGenerator.customers(5);
        List<Product> prods = DataGenerator.products(20);
        List<Order> orders = DataGenerator.orders(custs, prods, 10);

        System.out.println(custs);

        // Streams base
        System.out.println("1) Books >100:");
        StreamExercises.booksOver100(prods).forEach(System.out::println);

        System.out.println("\n2) Orders with Baby:");
        StreamExercises.ordersWithBaby(orders).forEach(System.out::println);

        System.out.println("\n3) Boys con 10% sconto:");
        StreamExercises.boysWithDiscount(prods).forEach(System.out::println);

        System.out.println("\n4) Tier-2 tra Feb-Apr 2021:");
        StreamExercises.productsByTierBetween(
                orders, 2,
                LocalDate.of(2021, 2, 1),
                LocalDate.of(2021, 4, 1)
        ).forEach(System.out::println);

        // Map & file
        System.out.println("\n5) Ordini raggruppati per cliente:");
        FileExercises.ordersByCustomer(orders)
                .forEach((c, ol) -> System.out.println(c.getName() + ": " + ol));

        System.out.println("\n6) Totale vendite per cliente:");
        FileExercises.totalSalesByCustomer(orders)
                .forEach((c, sum) -> System.out.println(c.getName() + " -> " + sum));

        System.out.println("\n7) Prodotto più costoso:");
        FileExercises.mostExpensiveProduct(prods).ifPresent(System.out::println);

        System.out.println("\n8) Media importi ordini:");
        System.out.println(FileExercises.averageOrderAmount(orders));

        System.out.println("\n9) Somma per categoria:");
        FileExercises.sumByCategory(prods)
                .forEach((cat, sum) -> System.out.println(cat + ": " + sum));

        // EXTRA file
        String file = "prodotti.txt";
        FileExercises.saveProductsToDisk(prods, file);
        System.out.println("\nSalvati prodotti in " + file);
        System.out.println("Letti da disco:");
        FileExercises.readProductsFromDisk(file).forEach(System.out::println);
    }
}
