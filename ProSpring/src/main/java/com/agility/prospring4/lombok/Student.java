package com.agility.prospring4.lombok;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
//@Data // include Getter, Setter, toString(), equals(), hashCode()
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Student {
    private String name;
    private transient String code;
    private Date dateOfBirth;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
