package hu.braininghub.spring6r.dto;

import hu.braininghub.spring6r.entity.FooEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FooMapper {

    FooEntity dtoToEntity(Foo foo);
    Foo entityToDto(FooEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "version", ignore = true),
    })
    void updateFromDto(Foo foo, @MappingTarget FooEntity entity);

    default ZonedDateTime map(Instant value) {
        if (value == null) return null;
        return ZonedDateTime.ofInstant(value, ZoneId.of("Europe/Budapest"));
    }
}
