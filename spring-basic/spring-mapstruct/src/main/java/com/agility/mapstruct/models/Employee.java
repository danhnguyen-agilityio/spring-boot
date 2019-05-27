package com.agility.mapstruct.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private Division division;
    private Date startDt;
}
