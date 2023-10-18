package io.github.hsm7.superheroes.fight.mapping;

import io.github.hsm7.superheroes.fight.Fight;
import io.github.hsm7.superheroes.fight.schema.AvroFight;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA_CDI)
public interface FightMapper {
    /**
     * Maps all fields from {@code fight} to a {@link AvroFight}
     *
     * @param fight
     * @return
     */
    AvroFight toSchema(Fight fight);

    default String toString(ObjectId objectId) {
        return objectId.toString();
    }
}
