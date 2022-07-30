package com.hendisantika.service;

import com.hendisantika.model.Carousel;
import com.hendisantika.model.Category;
import com.hendisantika.model.Coupon;
import com.hendisantika.model.Product;
import com.hendisantika.repository.CategoryRepository;
import com.hendisantika.repository.CouponRepository;
import com.hendisantika.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

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

    public void saveProductToDB(MultipartFile file, String name, String description, int quantity
            , Double price, String brand, String categories) {
        Product p = new Product();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            p.setImage(resizeImageForUse(Base64.getEncoder().encodeToString(file.getBytes()), 400, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setDescription(description);

        p.setName(name);
        p.setPrice(price);
        p.setBrand(brand);
        p.setQuantity(quantity);
        Coupon c = new Coupon();
        c.setDiscount(0);
        p.setDiscount(c);
        p = addCategoriesToProduct(p, categories);
        productRepository.save(p);
    }

    private Product addCategoriesToProduct(Product p, String categories) {
        String[] cates = categories.split(",");
        Category category = null;
        for (String str : cates) {
            category = categoryRepository.findById(Long.parseLong(str)).get();
            p.getCategories().add(category);
        }
        return p;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void changeProductName(Long id, String name) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        p.setName(name);
        productRepository.save(p);
    }

    public void changeProductDescription(Long id, String description) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        p.setDescription(description);
        productRepository.save(p);
    }

    public void changeProductPrice(Long id, Double price) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        p.setPrice(price);
        productRepository.save(p);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addImageToProduct(MultipartFile file, Long id) {

        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        Carousel carousel = new Carousel();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            carousel.setImage(resizeImageForUse(Base64.getEncoder().encodeToString(file.getBytes()), 400, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.getCarousel().add(carousel);
        productRepository.save(p);
    }

    public void changeProductQuantity(Long id, int quantity) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        p.setQuantity(quantity);
        productRepository.save(p);

    }

    public void saveProductDiscount(Long id, int discount) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        if (p.getDiscount() == null) {
            Coupon c = new Coupon();
            c.setDiscount(discount);
            p.setDiscount(c);
        } else {
            p.getDiscount().setDiscount(discount);
        }
        productRepository.save(p);
    }

    public void changeProductDiscount(Long id, int discount) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
        p.getDiscount().setDiscount(discount);
        productRepository.save(p);
    }

    public String resizeImageForUse(String src, int width, int height) {
        BufferedImage image = base64ToBufferedImage(src);
        try {
            image = resizeImage(image, width, height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            return bufferedImageTobase64(image);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(image).size(width, height).outputFormat("JPEG").outputQuality(1).toOutputStream(outputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }

    private BufferedImage base64ToBufferedImage(String base64Img) {
        BufferedImage image = null;
        byte[] decodedBytes = Base64.getDecoder().decode(base64Img);

        try {
            image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return image;
    }

    private String bufferedImageTobase64(BufferedImage image) throws UnsupportedEncodingException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", Base64.getEncoder().wrap(out));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out.toString(StandardCharsets.ISO_8859_1);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with " + id + " not found"));
    }

    public List<Product> searchProductByNameLike(String value) {
        return productRepository.findByNameContainingIgnoreCase(value);
    }

    public List<String> getAllBrands() {
        return productRepository.findAllBrandsDistincts();
    }

    public Product getProductWithBiggestDiscount() {
        Coupon discount = couponRepository.findMax();
        List<Product> products = productRepository.findAll();
        Product featuredProduct = null;
        for (Product p : products) {
            if (p.getDiscount().equals(discount)) {
                featuredProduct = p;
                break;
            }
        }
        return featuredProduct;
    }
}
