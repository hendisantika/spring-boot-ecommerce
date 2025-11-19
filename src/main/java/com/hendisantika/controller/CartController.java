package com.hendisantika.controller;

import com.hendisantika.model.ShoppingCart;
import com.hendisantika.model.WishList;
import com.hendisantika.service.ProductService;
import com.hendisantika.service.ShoppingCartService;
import com.hendisantika.service.WishListService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 10:02
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final ShoppingCartService shoppingCartService;
    private final WishListService wishListService;
    private final ProductService productService;

    @PostMapping("/addToCart")
    public String addToCart(HttpServletRequest request, Model model, @RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            shoppingCartService.addShoppingCartFirstTime(id, sessionToken, quantity);
        } else {
            shoppingCartService.addToExistingShoppingCart(id, sessionToken, quantity);
        }
        return "redirect:/";
    }

    @GetMapping("/shoppingCart")
    public String showShoppingCartView(HttpServletRequest request, Model model) {
        log.info("Loading shopping cart page");

        // Get session token
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
        }

        // Get shopping cart
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        model.addAttribute("shoppingCart", shoppingCart);

        // Get wishlist
        WishList wishList = wishListService.getWishListBySessionToken(sessionToken);
        if (wishList == null) {
            wishList = new WishList();
        }
        model.addAttribute("whishList", wishList); // Note: keeping the typo to match template

        // Add categories and brands for the sidebar
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("brands", productService.getAllBrands());

        log.info("Shopping cart page loaded with {} items", shoppingCart.getItemsNumber());

        return "shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id,
                                 @RequestParam("quantity") int quantity) {

        shoppingCartService.updateShoppingCartItem(id, quantity);
        return "redirect:shoppingCart";
    }

    @GetMapping("/removeCartItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        log.info("got here ...");
        shoppingCartService.removeCartIemFromShoppingCart(id, sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearShoppingCart")
    public String clearShoppingString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        request.getSession(false).removeAttribute("sessionToken");
        shoppingCartService.clearShoppingCart(sessionToken);
        return "redirect:/shoppingCart";
    }
}
