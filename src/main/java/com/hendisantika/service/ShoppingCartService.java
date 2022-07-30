package com.hendisantika.service;

import com.hendisantika.model.CartItem;
import com.hendisantika.model.Product;
import com.hendisantika.model.ShoppingCart;
import com.hendisantika.repository.CartItemRepository;
import com.hendisantika.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;

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

    public ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Product p = productService.getProductById(id);
        Boolean productDoesExistInTheCart = false;
        if (shoppingCart != null) {
            Set<CartItem> items = shoppingCart.getItems();
            for (CartItem item : items) {
                if (item.getProduct().equals(p)) {
                    productDoesExistInTheCart = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    shoppingCart.setItems(items);
                    return shoppingCartRepository.saveAndFlush(shoppingCart);
                }

            }
        }
        if (!productDoesExistInTheCart && (shoppingCart != null)) {
            CartItem cartItem1 = new CartItem();
            cartItem1.setDate(new Date());
            cartItem1.setQuantity(quantity);
            cartItem1.setProduct(p);
            shoppingCart.getItems().add(cartItem1);
            return shoppingCartRepository.saveAndFlush(shoppingCart);
        }

        return this.addShoppingCartFirstTime(id, sessionToken, quantity);

    }

    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {
        return shoppingCartRepository.findBySessionToken(sessionToken);
    }

    public CartItem updateShoppingCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        cartItem.setQuantity(quantity);
        return cartItemRepository.saveAndFlush(cartItem);
    }

    public ShoppingCart removeCartIemFromShoppingCart(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Set<CartItem> items = shoppingCart.getItems();
        CartItem cartItem = null;
        for (CartItem item : items) {
            if (item.getId() == id) {
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCart.setItems(items);
        return shoppingCartRepository.save(shoppingCart);
    }

    public void clearShoppingCart(String sessionToken) {
        ShoppingCart sh = shoppingCartRepository.findBySessionToken(sessionToken);
        shoppingCartRepository.delete(sh);
    }
}
