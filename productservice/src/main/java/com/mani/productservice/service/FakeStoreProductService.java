package com.mani.productservice.service;


import com.mani.productservice.Exceptions.NotFoundException;
import com.mani.productservice.dto.FakeStoreProductDto;
import com.mani.productservice.dto.GenericProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;

    //Shift + F6 => To change the variable in all places.
    private String productIdUrl="https://fakestoreapi.com/products/{id}";
    private String productUrl ="https://fakestoreapi.com/products";



    @Autowired
    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplateBuilder=restTemplateBuilder;
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

        RestTemplate restTemplate=restTemplateBuilder.build();

        //Our Api Doesn't return Exact format we want
        //Sometimes we get status code like 404, like that
        //So we catch that in the ResponseEntity. responseEntity.getBody gives the object

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.getForEntity(
                productIdUrl,
                FakeStoreProductDto.class,
                id);

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();
        if(fakeStoreProductDto==null)
            throw new NotFoundException("Id "+id+" is not found");

        GenericProductDto genericProductDto=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);

        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        //restTemplate.postForEntity(url,object posting,object returned by api)

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.postForEntity(
                productUrl,
                genericProductDto,
                FakeStoreProductDto.class
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();
        GenericProductDto result=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
        return result;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {

        RestTemplate restTemplate=restTemplateBuilder.build();
        List<GenericProductDto> genericProductDtos=new ArrayList<>();

        //List<FakeStoreProductDto>.class is not working because of Java Type Erasure OCncepts.
        //Internally List<T> is considerd as List only.
        //So Java didn't know the type of the Response at runtime. So it shows us an error
        ResponseEntity<FakeStoreProductDto[]> responseEntities= restTemplate.getForEntity(
                productUrl,
                FakeStoreProductDto[].class
        );

        FakeStoreProductDto[] fakeStoreProductDtos=responseEntities.getBody();

        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos)
        {
           GenericProductDto genericProductDto=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);

           genericProductDtos.add(genericProductDto);
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto updateProductsById(Long id,GenericProductDto inputDto) {
        RestTemplate restTemplate=restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> responseEntity= restTemplate.exchange(
                productIdUrl,
                HttpMethod.PUT,
                new HttpEntity<>(inputDto),
                FakeStoreProductDto.class,
                id
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();
        GenericProductDto genericProductDto=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
        return genericProductDto;
    }

    @Override
    public GenericProductDto deleteProductById(Long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.exchange(
                productIdUrl,
                HttpMethod.DELETE,
                null,
                FakeStoreProductDto.class,
                id
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();

        GenericProductDto genericProductDto=convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);

        return genericProductDto;

    }
}
