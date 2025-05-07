package epicode.service;

import com.github.javafaker.Faker;
import epicode.model.Customer;
import epicode.model.Order;
import epicode.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static List<Customer> customers(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> new Customer(
                        (long) i,
                        faker.name().fullName(),
                        faker.number().numberBetween(1, 3)
                ))
                .collect(Collectors.toList());
    }

    public static List<Product> products(int n) {
        String[] cats = {"Books", "Baby", "Boys", "Electronics", "Toys"};
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    String cat = cats[faker.number().numberBetween(0, cats.length)];
                    double price = faker.number().randomDouble(2, 10, 200);
                    return new Product(
                            (long) i,
                            faker.commerce().productName(),
                            cat,
                            price
                    );
                })
                .collect(Collectors.toList());
    }

    public static List<Order> orders(List<Customer> custs, List<Product> prods, int n) {
        AtomicLong idGen = new AtomicLong(1);
        Faker faker = new Faker();

        return Stream.generate(() -> {
                    long id = idGen.getAndIncrement();
                    Customer c = custs.get(faker.number().numberBetween(0, custs.size()));
                    int itemCount = faker.number().numberBetween(1, 5);
                    List<Product> items = IntStream.range(0, itemCount)
                            .mapToObj(i -> prods.get(faker.number().numberBetween(0, prods.size())))
                            .collect(Collectors.toList());
                    LocalDate od = LocalDate.now().minusDays(faker.number().numberBetween(1, 120));
                    return new Order(
                            id,
                            faker.options().option("PENDING", "DELIVERED", "SHIPPED"),
                            od,
                            od.plusDays(faker.number().numberBetween(1, 30)),
                            items,
                            c
                    );
                })
                .limit(n)
                .collect(Collectors.toList());
    }
}