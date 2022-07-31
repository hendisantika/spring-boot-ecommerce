package com.hendisantika.controller;

import com.hendisantika.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    private final ProductService productService;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}
