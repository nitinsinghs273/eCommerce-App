package com.example.ECommerceApp.Service;


import com.example.ECommerceApp.Model.Product;
import com.example.ECommerceApp.Repository.ProductRepositry;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepositry productRepositry;

    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return new ResponseEntity<>(productRepositry.findAll(), HttpStatus.OK) ;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<Product> getProductByID(Integer id) {
        try {
           Product product = productRepositry.findById(id).orElse(null);
           if(product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
           else{
               return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
           }
        }
        catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(new Product(),  HttpStatus.BAD_REQUEST);
        }
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepositry.save(product);
    }

    public Product updateProduct(Integer id, Product product, MultipartFile imageFile) throws IOException {
        // can also include id check
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepositry.save(product);
    }

    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            productRepositry.deleteById(id);
            return new ResponseEntity<>("Deleted sucessfully", HttpStatus.ACCEPTED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Deletion Failed", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Product>> searchByKeyword(String keyword) {
        try {
            return new ResponseEntity<>(productRepositry.searchByKeyword(keyword),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();;
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }
}
