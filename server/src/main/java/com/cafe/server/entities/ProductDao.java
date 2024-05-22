package com.cafe.server.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDao {

    private String name;

    private String description;

    private int price;

    private int categoryId;

}
