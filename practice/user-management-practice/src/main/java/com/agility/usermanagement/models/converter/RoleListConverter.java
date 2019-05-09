package com.agility.usermanagement.models.converter;

import com.agility.usermanagement.constants.RoleName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Persist a property of type List<RoleName>
 */
@Converter
public class RoleListConverter implements AttributeConverter<List<RoleName>, String> {
    private static final String SPLIT_CHAR = ",";


    @Override
    public String convertToDatabaseColumn(List<RoleName> roleNameList) {
        return roleNameList.stream().map(roleName -> roleName.name()).collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public  List<RoleName> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(SPLIT_CHAR)).stream()
            .map(RoleName::getRole)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
