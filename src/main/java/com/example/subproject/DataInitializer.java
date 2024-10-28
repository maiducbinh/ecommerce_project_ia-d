package com.example.subproject;

import com.example.subproject.entity.*;
import com.example.subproject.repository.*;
import com.example.subproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra và tạo vai trò nếu chưa tồn tại
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Kiểm tra xem dữ liệu đã tồn tại chưa
        if (customerRepository.count() == 0) {
            // Lấy vai trò ADMIN và USER
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
            Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

            // Tạo người dùng ADMIN
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // Mật khẩu: admin
            admin.setEmail("admin@example.com");
            admin.setRoles(Set.of(adminRole));
            customerRepository.save(admin);

            // Tạo người dùng USER
            Customer user = new Customer();
            user.setUsername("john_doe");
            user.setPassword(passwordEncoder.encode("password")); // Mật khẩu: password
            user.setEmail("john@example.com");
            user.setRoles(Set.of(userRole));
            customerRepository.save(user);

            // Tạo Items
            Book book1 = new Book();
            book1.setName("Effective Java");
            book1.setPrice(45.0);
            book1.setDescription("A comprehensive guide to programming in Java.");
            book1.setAuthor("Joshua Bloch");
            book1.setIsbn("978-0134685991");
            book1.setPublisher("Addison-Wesley");
            book1.setPages(416);
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setName("Spring Boot in Action");
            book2.setPrice(40.0);
            book2.setDescription("An in-depth look at Spring Boot framework.");
            book2.setAuthor("Craig Walls");
            book2.setIsbn("978-1617292545");
            book2.setPublisher("Manning Publications");
            book2.setPages(320);
            bookRepository.save(book2);

            Clothes clothes1 = new Clothes();
            clothes1.setName("T-Shirt");
            clothes1.setPrice(15.0);
            clothes1.setDescription("100% cotton t-shirt.");
            clothes1.setSize("M");
            clothes1.setMaterial("Cotton");
            clothes1.setColor("Blue");
            clothesRepository.save(clothes1);

            Clothes clothes2 = new Clothes();
            clothes2.setName("Jeans");
            clothes2.setPrice(50.0);
            clothes2.setDescription("Denim jeans with a slim fit.");
            clothes2.setSize("32");
            clothes2.setMaterial("Denim");
            clothes2.setColor("Black");
            clothesRepository.save(clothes2);

            // Tạo Cart cho USER
            Cart cart = new Cart();
            cart.setCustomer(user);
            cart.setItems(new HashSet<>());
            cartRepository.save(cart);

            // Thêm Items vào Cart
            cart.getItems().add(book1);
            cart.getItems().add(clothes1);
            cartRepository.save(cart);

            // Tạo Shipments
            Shipment shipment1 = new Shipment();
            shipment1.setMethod("Drone");
            shipment1.setStatus("Shipped");
            shipmentRepository.save(shipment1);

            Shipment shipment2 = new Shipment();
            shipment2.setMethod("Standard");
            shipment2.setStatus("Pending");
            shipmentRepository.save(shipment2);

            // Tạo Payments
            Payment payment1 = new Payment();
            payment1.setMethod("PayPal");
            payment1.setAmount(60.0);
            payment1.setStatus("Completed");
            paymentRepository.save(payment1);

            Payment payment2 = new Payment();
            payment2.setMethod("Credit Card");
            payment2.setAmount(45.0);
            payment2.setStatus("Pending");
            paymentRepository.save(payment2);

            // Tạo Orders
            Order order1 = new Order();
            order1.setCustomer(user);
            order1.setShipment(shipment1);
            order1.setPayment(payment1);
            order1.setCart(cart);
            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setCustomer(user);
            order2.setShipment(shipment2);
            order2.setPayment(payment2);
            order2.setCart(cart);
            orderRepository.save(order2);

            // Tạo Comments
            Comment comment1 = new Comment();
            comment1.setContent("Great book on Java!");
            comment1.setCustomer(user);
            comment1.setItem(book1);
            commentRepository.save(comment1);

            Comment comment2 = new Comment();
            comment2.setContent("Comfortable and stylish.");
            comment2.setCustomer(user);
            comment2.setItem(clothes1);
            commentRepository.save(comment2);

            // Tạo Ratings
            Rating rating1 = new Rating();
            rating1.setScore(5);
            rating1.setCustomer(user);
            rating1.setItem(book1);
            ratingRepository.save(rating1);

            Rating rating2 = new Rating();
            rating2.setScore(4);
            rating2.setCustomer(user);
            rating2.setItem(clothes1);
            ratingRepository.save(rating2);
        }
    }
}
