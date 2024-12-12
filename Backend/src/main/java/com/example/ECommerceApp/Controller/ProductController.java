package com.example.ECommerceApp.Controller;

import com.example.ECommerceApp.Model.Product;
import com.example.ECommerceApp.Service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin //it is used for CORS error
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;



    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return productService.getAllProducts();
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id){
        return productService.getProductByID(id);
    }

    @PostMapping("/product") // we are sending all other details seprate and image seprate
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);

        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId){
        Product product = productService.getProductByID(productId).getBody();

        byte[] imageFile = product.getImageData();
        if(imageFile == null){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(imageFile);

    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try{
            product1 = productService.updateProduct(id, product, imageFile);
            if(product1 != null){
                return new ResponseEntity<>("Updation Sucess", HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>("Upadation Failed", HttpStatus.BAD_REQUEST);
            }


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Upadation Failed", HttpStatus.BAD_REQUEST);
        }



    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        Product product1 = productService.getProductByID(id).getBody();
        if(product1 != null){
            return productService.deleteProduct(id);
        }
        else{
            return new ResponseEntity<>("Deletion Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> SearchByKeyword(@RequestParam String keyword){

        return productService.searchByKeyword(keyword);

    }


}
