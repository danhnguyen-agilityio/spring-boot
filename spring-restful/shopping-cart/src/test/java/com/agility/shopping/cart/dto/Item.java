package com.agility.shopping.cart.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    public Item(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    @JsonView(Views.Member.class)
    public int id;

    @JsonView(Views.Member.class)
    public String name;

    @JsonView(Views.Admin.class)
    public String owner;

    public String description;
}
