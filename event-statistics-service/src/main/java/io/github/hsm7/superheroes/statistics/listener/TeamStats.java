package io.github.hsm7.superheroes.statistics.listener;

import io.github.hsm7.superheroes.fight.schema.AvroFight;
import io.github.hsm7.superheroes.statistics.domain.TeamScore;

/**
 * Object keeping track of the number of battles won by heroes and villains
 */
class TeamStats {
    private int villains = 0;
    private int heroes = 0;

    /**
     * Adds a {@link AvroFight}
     *
     * @param result The {@link AvroFight} received
     * @return A {@link TeamScore} containing running battle stats by team
     */
    TeamScore add(AvroFight result) {
        if (result.getWinnerTeam().equalsIgnoreCase("heroes")) {
            this.heroes = this.heroes + 1;
        } else {
            this.villains = this.villains + 1;
        }

        return new TeamScore(this.heroes, this.villains);
    }

    int getVillainsCount() {
        return this.villains;
    }

    int getHeroesCount() {
        return this.heroes;
    }
}
