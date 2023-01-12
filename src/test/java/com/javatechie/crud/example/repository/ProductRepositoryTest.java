package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository UsageTest;
    private String monitor = "Monitor";
    private Product product = new Product(monitor, 1, 12000D);


    @AfterEach
    void tearDown() {
        UsageTest.deleteAll();
    }

    @Test
    void canFindByName() {
        // given
        UsageTest.save(product);
        // when
        Product product1 = UsageTest.findByName(monitor);
        // then
        assertThat(product1).isEqualTo(product);
    }

    @Test
    void canDeleteByName() {
        // given

        // when
        UsageTest.deleteByName(monitor);
        Product product = UsageTest.findByName(monitor);
        // then
        assertThat(product).isNull();
    }
}