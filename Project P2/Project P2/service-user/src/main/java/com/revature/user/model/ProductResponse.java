package com.revature.user.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String skuCode;
    private Double price;
    private CategoryResponse category;
    private String imageurl;

    // Getters and Setters
}
 // Category name to display on the product page

