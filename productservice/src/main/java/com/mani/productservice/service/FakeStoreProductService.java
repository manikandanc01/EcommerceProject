package com.mani.productservice.service;


import com.mani.productservice.Exceptions.NotFoundException;
import com.mani.productservice.thirdpartyclients.fakestore.dtos.FakeStoreProductDto;
import com.mani.productservice.dto.GenericProductDto;
import com.mani.productservice.thirdpartyclients.fakestore.FakeStoreProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

   FakeStoreProductClient fakeStoreProductClient;

    @Autowired
    public FakeStoreProductService(FakeStoreProductClient fakeStoreProductClient)
    {
        this.fakeStoreProductClient=fakeStoreProductClient;
    }


    public GenericProductDto convertFakeStoreProductDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto)
    {
        GenericProductDto genericProductDto=new GenericProductDto();

        genericProductDto.setId(fakeStoreProductDto.getId());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setPrice(fakeStoreProductDto.getPrice());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());

        return genericProductDto;
    }
    @Override
    public GenericProductDto getProductsById(Long id) throws NotFoundException {

      return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductClient.getProductsById(id));
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {

        return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductClient.createProduct(genericProductDto));

    }

    @Override
    public List<GenericProductDto> getAllProducts() {

        List<GenericProductDto> genericProductDtos=new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductClient.getAllProducts())
        {
           GenericProductDto genericProductDto=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);

           genericProductDtos.add(genericProductDto);
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto updateProductsById(Long id,GenericProductDto inputDto) {

       return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductClient.updateProductsById(id,inputDto));

    }

    @Override
    public GenericProductDto deleteProductById(Long id) {
       return convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductClient.deleteProductById(id));

    }
}
