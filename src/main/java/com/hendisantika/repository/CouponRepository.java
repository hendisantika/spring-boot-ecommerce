package com.hendisantika.repository;

import com.hendisantika.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT DISTINCT c FROM Coupon c where c.discount=(SELECT MAX(discount) From Coupon)")
    Coupon findMax();
}
