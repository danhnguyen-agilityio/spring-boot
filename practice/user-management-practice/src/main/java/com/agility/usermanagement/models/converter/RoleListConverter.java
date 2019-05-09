package com.agility.usermanagement.models.converter;

import com.agility.usermanagement.models.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Persist a property of type List<Role>
 */
@Converter
public class RoleListConverter implements AttributeConverter<List<Role>, String> {
    private static final String SPLIT_CHAR = ",";


    @Override
    public String convertToDatabaseColumn(List<Role> roleList) {
        return roleList.stream().map(roleName -> roleName.name()).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public  List<Role> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(SPLIT_CHAR)).stream()
            .map(Role::getRole)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
