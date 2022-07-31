package com.hendisantika.controller;

import com.hendisantika.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/31/22
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class WishListController {
    private final WishListService wishListService;

    @GetMapping("/addToWishlist/{id}")
    public String addToWishList(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionTokenWishList");
        if (sessionToken == null) {

            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionTokenWishList", sessionToken);
            wishListService.addToWishFirstTime(id, sessionToken);
        } else {
            wishListService.addToExistingWishList(id, sessionToken);
        }

        return "redirect:/";
    }

    @GetMapping("/removeWishListItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        log.info("got here ");
        wishListService.removeItemWishList(id, sessionToken);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/clearWishList")
    public String clearShoppingString(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionTokenWishList");
        request.getSession(false).removeAttribute("sessionTokenWishList");
        wishListService.clearWishList(sessionToken);
        return "redirect:/shoppingCart";
    }
}
