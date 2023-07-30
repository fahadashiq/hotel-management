package com.trivago.hotelmanagement.model.mapper;

import com.trivago.hotelmanagement.model.Location;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {Location.class})
public interface LocationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLocationFromLocation(Location location, @MappingTarget Location targetLocation);
}
