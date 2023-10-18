package io.github.hsm7.superheroes.villain.mapping;

import org.mapstruct.*;

import io.github.hsm7.superheroes.villain.Villain;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)

public interface VillainMapper {

    @Mapping(target = "id", ignore = true)
    Villain update(Villain input, @MappingTarget Villain target);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "otherName", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "level", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "picture", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "powers", nullValuePropertyMappingStrategy = IGNORE)
    Villain patch(Villain input, @MappingTarget Villain target);
}