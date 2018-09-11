package com.agility.prospring4.mapstruct;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * @author javareferencegv
 */
@Mapper
@DecoratedWith(ContactMapperDecorator.class)
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    /**
     * We define only those mappings which doesn't have same signature in source and target
     */
    @Mappings({
        @Mapping(source = "phone", target = "businessPhone"),
        @Mapping(source = "email", target = "businessEmail"),
        @Mapping(target = "businessCountry", constant="USA")
    })
    BusinessContact primaryToBusinessContact(PrimaryContact primary);
    @InheritInverseConfiguration
    PrimaryContact businessToPrimaryContact(BusinessContact business);

}