package com.hendisantika.repository;

import com.hendisantika.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */
public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findBySessionToken(String sessionToken);
}
