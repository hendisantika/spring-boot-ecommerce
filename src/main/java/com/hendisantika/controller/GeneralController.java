package com.hendisantika.controller;

import com.hendisantika.model.ShoppingCart;
import com.hendisantika.model.WishList;
import com.hendisantika.service.ProductService;
import com.hendisantika.service.ShoppingCartService;
import com.hendisantika.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GeneralController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final WishListService wishlistService;

    @ModelAttribute
    public void populateModel(Model model, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        String sessionTokenWishList = (String) request.getSession(true).getAttribute("sessionTokenWishList");
        if (sessionToken == null) {
            model.addAttribute("shoppingCart", new ShoppingCart());

        } else {
            model.addAttribute("shoppingCart", shoppingCartService.getShoppingCartBySessionToken(sessionToken));

        }

        if (sessionTokenWishList == null) {
            model.addAttribute("wishList", new WishList());

        } else {
            model.addAttribute("wishList", wishlistService.getWishListBySessionToken(sessionTokenWishList));
        }
        model.addAttribute("categories", productService.getAllCategories());

        model.addAttribute("brands", productService.getAllBrands());
        model.addAttribute("featured", productService.getProductWithBiggestDiscount());
    }
}
