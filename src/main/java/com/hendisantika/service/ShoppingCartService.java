package com.hendisantika.service;

import com.hendisantika.model.CartItem;
import com.hendisantika.model.ShoppingCart;
import com.hendisantika.repository.CartItemRepository;
import com.hendisantika.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;

    public ShoppingCart addShoppingCartFirstTime(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setSessionToken(sessionToken);
        shoppingCart.setDate(new Date());
        return shoppingCartRepository.save(shoppingCart);

    }
}
