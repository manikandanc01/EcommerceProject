package com.mani.productservice.service;


import com.mani.productservice.Exceptions.NotFoundException;
import com.mani.productservice.dto.GenericProductDto;

import java.util.List;

public interface ProductService {

    public GenericProductDto getProductsById(Long id) throws NotFoundException;
    public GenericProductDto createProduct(GenericProductDto genericProductDto);
    public List<GenericProductDto> getAllProducts();
    public GenericProductDto updateProductsById(Long id,GenericProductDto genericProductDto);
    public GenericProductDto deleteProductById(Long id);
}
