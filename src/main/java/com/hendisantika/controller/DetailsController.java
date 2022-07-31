package com.hendisantika.controller;

import com.hendisantika.model.Product;
import com.hendisantika.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class DetailsController {
    private final ProductService productService;

    @GetMapping("/detail/{id}")
    public String showIndex(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "details";
    }
}
