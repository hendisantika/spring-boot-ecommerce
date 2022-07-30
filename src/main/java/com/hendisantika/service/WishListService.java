package com.hendisantika.service;

import com.hendisantika.repository.WishListItemRepository;
import com.hendisantika.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
