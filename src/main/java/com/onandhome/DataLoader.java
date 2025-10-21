package com.onandhome;

import com.onandhome.product.ProductRepository;
import com.onandhome.product.entity.Product;
import com.onandhome.user.entity.User;
import com.onandhome.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    public DataLoader(ProductRepository productRepo, UserRepository userRepo){
        this.productRepo = productRepo; this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // 'new Product()' 대신 'Product.createProduct()'를 사용합니다.
        // (카테고리, 상태 값을 추가로 전달해야 합니다.)
        Product product1 = Product.createProduct("스마트폰 A", "좋은 스마트폰", 800000, 10, "전자기기", "ONSALE");
        Product product2 = Product.createProduct("노트북 B", "고성능 노트북", 1200000, 5, "전자기기", "ONSALE");
        Product product3 = Product.createProduct("무선이어폰 C", "음질좋음", 150000, 20, "악세서리", "SOLDOUT");

        // 생성된 객체들을 저장합니다.
        productRepo.save(product1);
        productRepo.save(product2);
        productRepo.save(product3);

        // User 데이터도 필요하다면 여기에 추가...
        // 예: User admin = new User("admin", "password", "ROLE_ADMIN");
        // userRepo.save(admin);
    }
}
