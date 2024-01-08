package com.mani.productservice.dto;


import lombok.Getter;
import lombok.Setter;

//The result that is finally get
@Getter
@Setter
public class GenericProductDto {

    private Long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;
}
