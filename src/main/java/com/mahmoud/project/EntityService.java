package com.mahmoud.project;

import com.mahmoud.project.entity.*;
import com.mahmoud.project.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class EntityService {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private AddressRepository addressRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public void addUser() {
        User user = User.builder()
                .name("Ahmed")
                .email("ahmed@gmail.com")
                .password("1234")
                .build();


        Profile profile = Profile.builder()
                .bio("bio")
                .phoneNumber("01018689588")
                .dateOfBirth(LocalDate.of(2006, 5, 13))
                .build();

        profile.setUser(user);
        userRepository.save(user);
        profileRepository.save(profile);
    }

    public void addAddress(User user) {
        Address address = Address.builder()
                .street("street")
                .city("city")
                .zip("zip")
                .state("state")
                .build();

        address.setUser(user);
        addressRepository.save(address);
    }

    @Transactional
    public void addProductAndCategory() {
        Category category1 = new Category();
        Category category2 = new Category();

        category1.setName("Books");
        category2.setName("Laptop");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Product product1 = Product.builder()
                .name("OOA&D")
                .description("Object Oriented Analysis and Design")
                .price(BigDecimal.valueOf(432))
                .build();
        product1.setCategory(category1);

        Product product2 = Product.builder()
                .name("The Definitive Guide of HTTP")
                .price(BigDecimal.valueOf(450))
                .build();
        product2.setCategory(category1);

        Product product3 = Product.builder()
                .name("HP")
                .description("HP Laptop")
                .price(BigDecimal.valueOf(46574.5))
                .build();
        product3.setCategory(category2);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }
}
