package com.hendisantika.controller;

import com.hendisantika.model.Product;
import com.hendisantika.model.ShoppingCart;
import com.hendisantika.service.ProductService;
import com.hendisantika.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/")
    public String showIndex(Model model, HttpSession session) {
        log.info("Loading index page with all required data");

        // Get all products
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        // Get all categories
        model.addAttribute("categories", productService.getAllCategories());

        // Get all brands
        model.addAttribute("brands", productService.getAllBrands());

        // Get featured product (product with biggest discount)
        Product featured = productService.getProductWithBiggestDiscount();
        model.addAttribute("featured", featured);

        // Get shopping cart for the session
        String sessionToken = session.getId();
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        model.addAttribute("shoppingCart", shoppingCart);

        log.info("Index page loaded with {} products, {} categories, {} brands",
                products.size(),
                productService.getAllCategories().size(),
                productService.getAllBrands().size());

        return "index";
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ModelAndView search(@RequestParam("value") String value) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("fragments/searchFragment");
        List<Product> products = productService.searchProductByNameLike(value);
        mv.addObject("products", products);
        return mv;
    }
}
