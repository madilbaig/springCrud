package com.javatechie.crud.example.service;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products){
        return repository.saveAll(products);
    }
    public List<Product> retrieveProducts(){
        return repository.findAll();
    }

    public Product retrieveProductById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Product retrieveProductByName(String name){
        return repository.findByName(name);
    }

    public String removeProductById(Integer id){
        repository.deleteById(id);
        return "product deleted !!" + id;
    }
    public String removeProductByName(String name){
        repository.deleteByName(name);
        return "product deleted !!" + name;
    }

    public Product rebuildProduct(Product product){
        Product existingProduct = repository.findById(product.getId()).orElse(product);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        return repository.save(existingProduct);
    }
}
