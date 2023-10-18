package io.github.hsm7.superheroes.fight.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import io.github.hsm7.superheroes.fight.Fight;
import io.github.hsm7.superheroes.fight.schema.AvroFight;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FightMapperTests {
    private static final String DEFAULT_FIGHT_ID = new ObjectId().toString();
    private static final Instant DEFAULT_FIGHT_DATE = Instant.now().truncatedTo(ChronoUnit.MICROS);

    private static final String DEFAULT_HERO_NAME = "Super Baguette";
    private static final String DEFAULT_HERO_PICTURE = "super_baguette.png";
    private static final int DEFAULT_HERO_LEVEL = 42;
    private static final String HEROES_TEAM_NAME = "heroes";

    private static final String DEFAULT_VILLAIN_NAME = "Super Chocolatine";
    private static final String DEFAULT_VILLAIN_PICTURE = "super_chocolatine.png";
    private static final int DEFAULT_VILLAIN_LEVEL = 42;
    private static final String VILLAINS_TEAM_NAME = "villains";

    FightMapper mapper = Mappers.getMapper(FightMapper.class);

    @Test
    public void mappingWorks() {
        var fight = this.mapper.toSchema(createFight());

        assertThat(fight)
                .extracting(
                        AvroFight::getId,
                        AvroFight::getFightDate,
                        AvroFight::getWinnerName,
                        AvroFight::getWinnerLevel,
                        AvroFight::getWinnerPicture,
                        AvroFight::getLoserName,
                        AvroFight::getLoserLevel,
                        AvroFight::getLoserPicture,
                        AvroFight::getWinnerTeam,
                        AvroFight::getLoserTeam
                )
                .containsExactly(
                        DEFAULT_FIGHT_ID,
                        DEFAULT_FIGHT_DATE,
                        DEFAULT_HERO_NAME,
                        DEFAULT_HERO_LEVEL,
                        DEFAULT_HERO_PICTURE,
                        DEFAULT_VILLAIN_NAME,
                        DEFAULT_VILLAIN_LEVEL,
                        DEFAULT_VILLAIN_PICTURE,
                        HEROES_TEAM_NAME,
                        VILLAINS_TEAM_NAME
                );
    }

    private static Fight createFight() {
        var fight = new Fight();
        fight.id = new ObjectId(DEFAULT_FIGHT_ID);
        fight.fightDate = DEFAULT_FIGHT_DATE;
        fight.winnerName = DEFAULT_HERO_NAME;
        fight.winnerLevel = DEFAULT_HERO_LEVEL;
        fight.winnerPicture = DEFAULT_HERO_PICTURE;
        fight.loserName = DEFAULT_VILLAIN_NAME;
        fight.loserLevel = DEFAULT_VILLAIN_LEVEL;
        fight.loserPicture = DEFAULT_VILLAIN_PICTURE;
        fight.winnerTeam = HEROES_TEAM_NAME;
        fight.loserTeam = VILLAINS_TEAM_NAME;

        return fight;
    }
}
