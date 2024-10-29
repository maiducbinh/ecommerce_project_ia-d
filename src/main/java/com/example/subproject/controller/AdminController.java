// src/main/java/com/example/subproject/controller/AdminController.java

package com.example.subproject.controller;

import com.example.subproject.dto.ProductForm;
import com.example.subproject.entity.*;
import com.example.subproject.repository.*;
import com.example.subproject.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClothesRepository clothesRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RatingRepository ratingRepository;

    // Quản lý Sản Phẩm

    @GetMapping("/products")
    public String listProducts(Model model){
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "admin/products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model){
        model.addAttribute("productForm", new ProductForm());
        return "admin/add_product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("productForm") @Valid ProductForm productForm, BindingResult result, Model model){
        if(result.hasErrors()){
            return "admin/add_product";
        }

        String dtype = productForm.getDtype();
        if(dtype.equals("Book")) {
            Book book = new Book();
            book.setName(productForm.getName());
            book.setPrice(productForm.getPrice());
            book.setDescription(productForm.getDescription());
            book.setAuthor(productForm.getAuthor());
            book.setIsbn(productForm.getIsbn());
            book.setPublisher(productForm.getPublisher());
            book.setPages(productForm.getPages());
            bookRepository.save(book);
        } else if(dtype.equals("Clothes")) {
            Clothes clothes = new Clothes();
            clothes.setName(productForm.getName());
            clothes.setPrice(productForm.getPrice());
            clothes.setDescription(productForm.getDescription());
            clothes.setSize(productForm.getSize());
            clothes.setMaterial(productForm.getMaterial());
            clothes.setColor(productForm.getColor());
            clothesRepository.save(clothes);
        } else {
            // Xử lý các loại sản phẩm khác hoặc thông báo lỗi
            model.addAttribute("error", "Loại sản phẩm không hợp lệ.");
            return "admin/add_product";
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model){
        Item item = itemService.getItemById(id);
        if(item == null){
            return "redirect:/admin/products";
        }

        ProductForm productForm = new ProductForm();
        productForm.setId(item.getId());
        productForm.setName(item.getName());
        productForm.setPrice(item.getPrice());
        productForm.setDescription(item.getDescription());

        if(item instanceof Book){
            Book book = (Book) item;
            productForm.setDtype("Book");
            productForm.setAuthor(book.getAuthor());
            productForm.setIsbn(book.getIsbn());
            productForm.setPublisher(book.getPublisher());
            productForm.setPages(book.getPages());
        } else if(item instanceof Clothes){
            Clothes clothes = (Clothes) item;
            productForm.setDtype("Clothes");
            productForm.setSize(clothes.getSize());
            productForm.setMaterial(clothes.getMaterial());
            productForm.setColor(clothes.getColor());
        }

        model.addAttribute("productForm", productForm);
        return "admin/edit_product";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute("productForm") @Valid ProductForm productForm,
                              BindingResult result,
                              Model model){
        if(result.hasErrors()){
            return "admin/edit_product";
        }

        Item existingItem = itemService.getItemById(id);
        if(existingItem == null){
            return "redirect:/admin/products";
        }

        String dtype = productForm.getDtype();
        if(dtype.equals("Book") && existingItem instanceof Book){
            Book book = (Book) existingItem;
            book.setName(productForm.getName());
            book.setPrice(productForm.getPrice());
            book.setDescription(productForm.getDescription());
            book.setAuthor(productForm.getAuthor());
            book.setIsbn(productForm.getIsbn());
            book.setPublisher(productForm.getPublisher());
            book.setPages(productForm.getPages());
            bookRepository.save(book);
        } else if(dtype.equals("Clothes") && existingItem instanceof Clothes){
            Clothes clothes = (Clothes) existingItem;
            clothes.setName(productForm.getName());
            clothes.setPrice(productForm.getPrice());
            clothes.setDescription(productForm.getDescription());
            clothes.setSize(productForm.getSize());
            clothes.setMaterial(productForm.getMaterial());
            clothes.setColor(productForm.getColor());
            clothesRepository.save(clothes);
        } else {
            // Xử lý khi loại sản phẩm thay đổi hoặc thông báo lỗi
            model.addAttribute("error", "Loại sản phẩm không hợp lệ hoặc không khớp với loại hiện tại.");
            return "admin/edit_product";
        }

        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        itemService.deleteItemById(id);
        return "redirect:/admin/products";
    }

    // Quản lý Khách Hàng

    @GetMapping("/customers")
    public String listCustomers(Model model){
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "admin/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model){
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer == null){
            return "redirect:/admin/customers";
        }
        model.addAttribute("customer", customer);
        return "admin/edit_customer";
    }

    @PostMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id,
                               @ModelAttribute("customer") @Valid Customer customer,
                               BindingResult result,
                               Model model,
                               HttpServletRequest request){
        if(result.hasErrors()){
            return "admin/edit_customer";
        }

        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if(existingCustomer == null){
            return "redirect:/admin/customers";
        }

        existingCustomer.setUsername(customer.getUsername());
        existingCustomer.setEmail(customer.getEmail());

        // Cập nhật vai trò
        String[] roles = request.getParameterValues("roles");
        if(roles != null){
            Set<Role> updatedRoles = new HashSet<>();
            for(String roleName : roles){
                Role role = roleRepository.findByName(roleName).orElse(null);
                if(role != null){
                    updatedRoles.add(role);
                }
            }
            existingCustomer.setRoles(updatedRoles);
        }

        customerRepository.save(existingCustomer);
        return "redirect:/admin/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id){
        customerRepository.deleteById(id);
        return "redirect:/admin/customers";
    }

    // Các chức năng quản trị khác có thể thêm ở đây
}
