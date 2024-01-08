package com.mani.productservice.dto;

import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Generally our api's return JSON -- Which is key value pairs
//In Java, class is equivalent to key,value pairs
//So we store that response from the external api's as a Java object

public class FakeStoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

}