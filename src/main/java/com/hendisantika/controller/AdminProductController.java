package com.hendisantika.controller;

import com.hendisantika.model.Category;
import com.hendisantika.model.Product;
import com.hendisantika.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminProductController {
    private final ProductService productService;

    @GetMapping("/admin/index")
    public String showExampleView(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/index";
    }

    @GetMapping("/admin/product")
    public String showAddProduct(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product";
    }

    @PostMapping("/Admin/addP")
    public String saveProduct(@RequestParam("file") MultipartFile file,
                              @RequestParam("pname") String name,
                              @RequestParam("price") Double price,
                              @RequestParam("desc") String desc,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("brand") String brand,
                              @RequestParam("categories") String categories) {

        productService.saveProductToDB(file, name, desc, quantity, price, brand, categories);
        return "redirect:/Admin/product";
    }

}
