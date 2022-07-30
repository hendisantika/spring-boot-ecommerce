package com.hendisantika.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/30/22
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private int quantity;
    private Double price;
    private String brand;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Coupon discount;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_category", joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")}
            , inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Set<Category> categories = new HashSet<Category>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<Carousel> carousel;

}
