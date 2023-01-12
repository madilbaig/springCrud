package com.javatechie.crud.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean private ProductService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateProduct() throws Exception {
        Product mockProduct = new Product(1,"abc", 2,234D);
        Product postProduct = new Product("abc", 2,234D);
        doReturn(mockProduct).when(service).saveProduct(any());

        mockMvc.perform(post("/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("abc"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(234.0));
    }
//
    @Test
    void addProducts() throws Exception {
        Product mockProduct = new Product(1,"abc", 2,234D);
        Product postProduct = new Product("abc", 2,234D);
        Product mockProduct1 = new Product(2,"abc2", 3,23534D);
        Product postProduct1 = new Product("abc2", 3,23534D);

        doReturn(Arrays.asList(mockProduct,mockProduct1)).when(service).saveProducts(anyList());
        mockMvc.perform(post("/addProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(Arrays.asList(postProduct,postProduct1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("abc"))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$.[0]price").value(234.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("abc2"))
                .andExpect(jsonPath("$[1].quantity").value(3))
                .andExpect(jsonPath("$[1].price").value(23534.0));

    }

    @Test
    void findAllProducts() throws Exception {
        Product mockProducts = new Product(1,"abc", 2,234D);
        Product mockProducts1 = new Product(3,"abc2", 3,23534D);
        doReturn(Arrays.asList(mockProducts,mockProducts1)).when(service).retrieveProducts();

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("abc"))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].price").value(234.0))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[1].name").value("abc2"))
                .andExpect(jsonPath("$[1].quantity").value(3))
                .andExpect(jsonPath("$[1].price").value(23534.0));
    }

    @Test
    void testFindProductById() throws Exception {
        Product mockProduct = new Product(1,"abc", 2,234D);
        doReturn(Optional.of(mockProduct)).when(service).retrieveProductById(1);

        mockMvc.perform(get("/productById/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("abc"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(234.0));
    }


    @Test
    void testUpdateProduct() throws Exception {
        Product mockProduct = new Product(1,"abc", 2,234D);
        Product putProduct = new Product("abc", 2,234D);

        doReturn(mockProduct).when(service).rebuildProduct(any());

        mockMvc.perform(put("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(putProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("abc"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(234.0));
    }

    @Test
    void deleteProductById() throws Exception {
        Product mockProduct = new Product(1,"abc", 2,234D);
        String string = "product deleted";
        doReturn(string).when(service).removeProductById(anyInt());

        mockMvc.perform(delete("/deleteById/{id}", 1))
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}