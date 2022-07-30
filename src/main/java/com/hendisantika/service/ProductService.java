package com.hendisantika.service;

import com.hendisantika.repository.CategoryRepository;
import com.hendisantika.repository.CouponRepository;
import com.hendisantika.repository.ProductRepository;
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
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CouponRepository couponRepository;
}
