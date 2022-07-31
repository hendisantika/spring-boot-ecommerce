package com.hendisantika.controller;

import com.hendisantika.service.ProductService;
import com.hendisantika.service.ShoppingCartService;
import com.hendisantika.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

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

}
