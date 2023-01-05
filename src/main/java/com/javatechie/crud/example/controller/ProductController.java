package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products){
        return service.saveProducts(products);
    }

    @GetMapping("/products")
    public List<Product> findAllProducts(){
        return service.retrieveProducts();
    }

    @GetMapping("/productById/{id}")
    public Product findProductById(@PathVariable Integer id){
        return service.retrieveProductById(id);
    }

    @GetMapping("/productByName/{name}")
    public Product findProductByName(@PathVariable String name){
        return service.retrieveProductByName(name);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product){
        return service.rebuildProduct(product);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteProductById(@PathVariable Integer id){
        return service.removeProductById(id);
    }

    @DeleteMapping("/deleteByName/{name}")
    public String deleteProductByName(@PathVariable String name){
        return service.removeProductByName(name);
    }
}
