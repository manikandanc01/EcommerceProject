package com.mani.productservice.thirdpartyclients.fakestore;

import com.mani.productservice.Exceptions.NotFoundException;
import com.mani.productservice.thirdpartyclients.fakestore.dtos.FakeStoreProductDto;
import com.mani.productservice.dto.GenericProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreProductClient {


  //  @Value("${fakestore.api.baseurl}")
    private String baseUrl;

  //  @Value("${fakestore.api.productpath}")
    private String productPath;
    private RestTemplateBuilder restTemplateBuilder;

    private String productIdUrl;
    private String productUrl;

    //Shift + F6 => To change the variable in all places.
  /*  private String productIdUrl="https://fakestoreapi.com/products/{id}";
    private String productUrl ="https://fakestoreapi.com/products";*/


    @Autowired
    public FakeStoreProductClient(RestTemplateBuilder restTemplateBuilder,
                                  @Value("${fakestore.api.baseurl}") String baseUrl,
                                  @Value("${fakestore.api.productpath}") String productPath )
    {
        this.restTemplateBuilder=restTemplateBuilder;
        this.productIdUrl=baseUrl+productPath+"/{id}";
        this.productUrl=baseUrl+productPath;
    }

    public FakeStoreProductDto getProductsById(Long id) throws NotFoundException {

        System.out.println(productIdUrl);

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

        return fakeStoreProductDto;
    }


    public FakeStoreProductDto createProduct(GenericProductDto genericProductDto) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        //restTemplate.postForEntity(url,object posting,object returned by api)

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.postForEntity(
                productUrl,
                genericProductDto,
                FakeStoreProductDto.class
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();

        return fakeStoreProductDto;
    }


    public List<FakeStoreProductDto> getAllProducts() {

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


        return Arrays.asList(fakeStoreProductDtos);
    }


    public FakeStoreProductDto updateProductsById(Long id,GenericProductDto inputDto) {
        RestTemplate restTemplate=restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> responseEntity= restTemplate.exchange(
                productIdUrl,
                HttpMethod.PUT,
                new HttpEntity<>(inputDto),
                FakeStoreProductDto.class,
                id
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();

        return fakeStoreProductDto;
    }


    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.exchange(
                productIdUrl,
                HttpMethod.DELETE,
                null,
                FakeStoreProductDto.class,
                id
        );

        FakeStoreProductDto fakeStoreProductDto=responseEntity.getBody();


        return fakeStoreProductDto;

    }
}
