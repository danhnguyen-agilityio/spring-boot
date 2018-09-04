package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.SimpleDestination;
import com.agility.mapstruct.models.SimpleSource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {

    SimpleDestination sourceToDestination(SimpleSource source);
    SimpleSource destinationToSource(SimpleDestination destination);
}
