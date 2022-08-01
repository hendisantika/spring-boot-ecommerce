package com.hendisantika.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @Transient
    private int productsNumber;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "categories")
    private Set<Product> products;

    public Category(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getProductsNumber() {
        return this.products.size();
    }

}