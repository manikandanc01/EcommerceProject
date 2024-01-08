package com.mani.productservice.controllers;


import com.mani.productservice.Exceptions.NotFoundException;
import com.mani.productservice.dto.ExceptionDto;
import com.mani.productservice.dto.GenericProductDto;
import com.mani.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("{id}")
    public GenericProductDto getProductsById(@PathVariable("id") Long id) throws NotFoundException {
        return productService.getProductsById(id);
    }

    @PutMapping("{id}")
    public GenericProductDto updateProductById(@PathVariable("id") Long id,@RequestBody GenericProductDto genericProductDto) {
        return productService.updateProductsById(id,genericProductDto);
    }


    //In the Post http request, we will send some data to the server in the form of JSON
    //@RequestBody is used to bind the HTTP request body to a method parameter.
    //This allows you to consume the request body as a Java object,
    //which can then be processed by your controller method.

    @PostMapping()
    public GenericProductDto createProduct(@RequestBody GenericProductDto genericProductDto) {
        return productService.createProduct(genericProductDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") Long id)
    {
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);

        //return productService.deleteProductById(id);
    }

    //This Exception Handling only specific to this controller.
    //If somewhere else this class not found exception is thrown, that will not handled.
    //So instead of handling exeception in every single file, handle the exception globally
 /*   @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(NotFoundException notFoundException)
    {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }

  */


}
