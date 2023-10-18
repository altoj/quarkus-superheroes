package io.github.hsm7.superheroes.hero.mapping;

import io.github.hsm7.superheroes.hero.Hero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Mapper to map all fields on an input {@link Hero} onto a target {@link Hero}.
 */
@Mapper(componentModel = ComponentModel.JAKARTA_CDI)
public interface HeroMapper {
    /**
     * Maps all fields except <code>id</code> from {@code input} onto {@code target}.
     *
     * @param input  The input {@link Hero}
     * @param target The target {@link Hero}
     */
    @Mapping(target = "id", ignore = true)
    Hero update(Hero input, @MappingTarget Hero target);

    /**
     * Maps all <code><strong>non-null</strong></code> fields from {@code input} onto {@code target}.
     *
     * @param input  The input {@link Hero}
     * @param target The target {@link Hero}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "otherName", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "level", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "picture", nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "powers", nullValuePropertyMappingStrategy = IGNORE)
    Hero patch(Hero input, @MappingTarget Hero target);
}
