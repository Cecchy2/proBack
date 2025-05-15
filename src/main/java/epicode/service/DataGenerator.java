package epicode.service;

import com.github.javafaker.Faker;
import epicode.model.Customer;
import epicode.model.Order;
import epicode.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static List<Customer> customers(int n) {
        List<Customer> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Customer c = new Customer(
                    (long) i,
                    faker.name().fullName(),
                    faker.number().numberBetween(1, 3)
            );
            result.add(c);
        }
        return result;
    }

    public static List<Product> products(int n) {
        String[] cats = {"Books", "Baby", "Boys", "Electronics", "Toys"};
        List<Product> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String cat = cats[faker.number().numberBetween(0, cats.length)];
            double price = faker.number().randomDouble(2, 10, 200);
            Product p = new Product(
                    (long) i,
                    faker.commerce().productName(),
                    cat,
                    price
            );
            result.add(p);
        }
        return result;
    }

    public static List<Order> orders(List<Customer> custs, List<Product> prods, int n) {
        AtomicLong idGen = new AtomicLong(1);
        Faker faker = new Faker();
        List<Order> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            long id = idGen.getAndIncrement();

            // cliente casuale
            Customer c = custs.get(faker.number().numberBetween(0, custs.size()));

            // numero di articoli casuale
            int itemCount = faker.number().numberBetween(1, 5);
            List<Product> items = new ArrayList<>();
            for (int j = 0; j < itemCount; j++) {
                items.add(prods.get(faker.number().numberBetween(0, prods.size())));
            }

            // date
            LocalDate od = LocalDate.now().minusDays(faker.number().numberBetween(1, 120));
            LocalDate dd = od.plusDays(faker.number().numberBetween(1, 30));

            Order o = new Order(
                    id,
                    faker.options().option("PENDING", "DELIVERED", "SHIPPED"),
                    od,
                    dd,
                    items,
                    c
            );
            result.add(o);
        }

        return result;
    }
}