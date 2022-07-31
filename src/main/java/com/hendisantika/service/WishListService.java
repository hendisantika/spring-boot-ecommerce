package com.hendisantika.service;

import com.hendisantika.model.Product;
import com.hendisantika.model.WishList;
import com.hendisantika.model.WishListItem;
import com.hendisantika.repository.WishListItemRepository;
import com.hendisantika.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;
    private final WishListItemRepository wishListItemRepository;
    private final ProductService productService;

    public WishList addToWishFirstTime(Long id, String sessionToken) {
        WishList wishlist = new WishList();
        WishListItem item = new WishListItem();

        item.setDate(new Date());
        item.setProduct(productService.getProductById(id));
        wishlist.getItems().add(item);
        wishlist.setSessionToken(sessionToken);
        wishlist.setDate(new Date());
        return wishListRepository.save(wishlist);
    }

    public WishList addToExistingWishList(Long id, String sessionToken) {
        WishList wishList = wishListRepository.findBySessionToken(sessionToken);
        Product p = productService.getProductById(id);
        Boolean productDoesExistInTheCart = false;
        if (wishList != null) {
            Set<WishListItem> items = wishList.getItems();
            for (WishListItem item : items) {
                if (item.getProduct().equals(p)) {
                    productDoesExistInTheCart = true;
                    break;
                }

            }
        }
        if (!productDoesExistInTheCart && (wishList != null)) {
            WishListItem item1 = new WishListItem();
            item1.setDate(new Date());
            item1.setProduct(p);
            wishList.getItems().add(item1);
            return wishListRepository.saveAndFlush(wishList);
        }

        return null;

    }

    public WishList getWishListBySessionToken(String sessionToken) {
        return wishListRepository.findBySessionToken(sessionToken);
    }

    public WishList removeItemWishList(Long id, String sessionToken) {
        WishList WishList = wishListRepository.findBySessionToken(sessionToken);
        Set<WishListItem> items = WishList.getItems();
        WishListItem item = null;
        for (WishListItem item1 : items) {
            if (item1.getId() == id) {
                item = item1;
            }
        }
        items.remove(item);
        wishListItemRepository.delete(item);
        WishList.setItems(items);
        return wishListRepository.save(WishList);
    }

    public void clearWishList(String sessionToken) {
        WishList sh = wishListRepository.findBySessionToken(sessionToken);
        wishListRepository.delete(sh);
    }
}
