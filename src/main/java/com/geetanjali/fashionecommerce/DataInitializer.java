package com.geetanjali.fashionecommerce;

import com.geetanjali.fashionecommerce.entity.Brand;
import com.geetanjali.fashionecommerce.entity.Category;
import com.geetanjali.fashionecommerce.entity.Product;
import com.geetanjali.fashionecommerce.entity.ProductVariant;
import com.geetanjali.fashionecommerce.repository.BrandRepository;
import com.geetanjali.fashionecommerce.repository.CategoryRepository;
import com.geetanjali.fashionecommerce.repository.ProductRepository;
import com.geetanjali.fashionecommerce.repository.ProductVariantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ProductRepository productRepository,
            ProductVariantRepository variantRepository) {

        return args -> {

            if (categoryRepository.count() == 0) {

                Category men = new Category(
                        "Men",
                        "Fashion products for men",
                        "men.jpg"
                );

                Category women = new Category(
                        "Women",
                        "Fashion products for women",
                        "women.jpg"
                );

                Category kids = new Category(
                        "Kids",
                        "Fashion products for kids",
                        "kids.jpg"
                );

                Category accessories = new Category(
                        "Accessories",
                        "Fashion accessories",
                        "accessories.jpg"
                );

                categoryRepository.save(men);
                categoryRepository.save(women);
                categoryRepository.save(kids);
                categoryRepository.save(accessories);
            }

            if (brandRepository.count() == 0) {

                Brand urbanStyle = new Brand(
                        "Urban Style",
                        "Modern fashion brand",
                        "urban-style.jpg"
                );

                Brand trendWear = new Brand(
                        "Trend Wear",
                        "Trendy clothing collection",
                        "trend-wear.jpg"
                );

                Brand classicWear = new Brand(
                        "Classic Wear",
                        "Classic and comfortable fashion",
                        "classic-wear.jpg"
                );

                brandRepository.save(urbanStyle);
                brandRepository.save(trendWear);
                brandRepository.save(classicWear);
            }

            if (productRepository.count() == 0) {

                Category men = categoryRepository
                        .findAll()
                        .stream()
                        .filter(c -> c.getName().equals("Men"))
                        .findFirst()
                        .orElse(null);

                Category women = categoryRepository
                        .findAll()
                        .stream()
                        .filter(c -> c.getName().equals("Women"))
                        .findFirst()
                        .orElse(null);

                Brand urbanStyle = brandRepository
                        .findAll()
                        .stream()
                        .filter(b -> b.getName().equals("Urban Style"))
                        .findFirst()
                        .orElse(null);

                Brand trendWear = brandRepository
                        .findAll()
                        .stream()
                        .filter(b -> b.getName().equals("Trend Wear"))
                        .findFirst()
                        .orElse(null);

                Product shirt = new Product(
                        "Classic Cotton Shirt",
                        "Comfortable cotton shirt for everyday wear.",
                        899.0,
                        "shirt.jpg",
                        men,
                        urbanStyle
                );

                Product jeans = new Product(
                        "Slim Fit Jeans",
                        "Stylish slim fit denim jeans.",
                        1499.0,
                        "jeans.jpg",
                        men,
                        trendWear
                );

                Product dress = new Product(
                        "Elegant Summer Dress",
                        "Lightweight and stylish summer dress.",
                        1299.0,
                        "dress.jpg",
                        women,
                        trendWear
                );

                productRepository.save(shirt);
                productRepository.save(jeans);
                productRepository.save(dress);

                variantRepository.save(
                        new ProductVariant(
                                "M",
                                "White",
                                20,
                                899.0,
                                shirt
                        )
                );

                variantRepository.save(
                        new ProductVariant(
                                "L",
                                "Blue",
                                15,
                                899.0,
                                shirt
                        )
                );

                variantRepository.save(
                        new ProductVariant(
                                "32",
                                "Blue",
                                10,
                                1499.0,
                                jeans
                        )
                );

                variantRepository.save(
                        new ProductVariant(
                                "34",
                                "Black",
                                8,
                                1499.0,
                                jeans
                        )
                );

                variantRepository.save(
                        new ProductVariant(
                                "M",
                                "Red",
                                12,
                                1299.0,
                                dress
                        )
                );

                variantRepository.save(
                        new ProductVariant(
                                "L",
                                "Green",
                                7,
                                1299.0,
                                dress
                        )
                );
            }
        };
    }
}