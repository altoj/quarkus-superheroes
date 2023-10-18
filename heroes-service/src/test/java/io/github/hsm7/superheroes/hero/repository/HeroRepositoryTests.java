package io.github.hsm7.superheroes.hero.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import io.github.hsm7.superheroes.hero.Hero;
import jakarta.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.logging.Log;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;

@QuarkusTest
class HeroRepositoryTests {
    private static final String DEFAULT_NAME = "Super Chocolatine";
    private static final String DEFAULT_OTHER_NAME = "Super Chocolatine chocolate in";
    private static final String DEFAULT_PICTURE = "super_chocolatine.png";
    private static final String DEFAULT_POWERS = "does not eat pain au chocolat";
    private static final int DEFAULT_LEVEL = 42;

    @Inject
    HeroRepository heroRepository;

    @Test
    @TestReactiveTransaction
    public void findRandomNotFound(UniAsserter asserter) {
        asserter.execute(this.heroRepository::deleteAll)
                .assertEquals(this.heroRepository::count, 0L)
                .assertThat(
                        this.heroRepository::findRandom,
                        hero -> assertThat(hero).isNull()
                );
    }

    @Test
    @TestReactiveTransaction
    public void findRandomFound(UniAsserter asserter) {
        Hero hero = new Hero();
        hero.setName(DEFAULT_NAME);
        hero.setOtherName(DEFAULT_OTHER_NAME);
        hero.setPicture(DEFAULT_PICTURE);
        hero.setPowers(DEFAULT_POWERS);
        hero.setLevel(DEFAULT_LEVEL);

        asserter.execute(this.heroRepository::deleteAll)
                .assertEquals(this.heroRepository::count, 0L)
                .execute(() -> this.heroRepository.persist(hero))
                .assertEquals(this.heroRepository::count, 1L)
                .assertThat(
                        this.heroRepository::findRandom,
                        h -> {
                            assertThat(h)
                                    .isNotNull()
                                    .usingRecursiveComparison()
                                    .isEqualTo(hero);

                            Assertions.assertThat(h.getId())
                                    .isNotNull()
                                    .isPositive();
                        }
                );
    }

    @Test
    @TestReactiveTransaction
    public void findAllWhereNameLikeFound(UniAsserter asserter) {
        // Doing it this way because UniAsserter doesn't work well with ParameterizedTest
        var names = Stream.of(DEFAULT_NAME, "choco", "Choco", "CHOCO", "Chocolatine", "super", "l", "");

        Hero hero = new Hero();
        hero.setName(DEFAULT_NAME);
        hero.setOtherName(DEFAULT_OTHER_NAME);
        hero.setPicture(DEFAULT_PICTURE);
        hero.setPowers(DEFAULT_POWERS);
        hero.setLevel(DEFAULT_LEVEL);

        asserter.execute(this.heroRepository::deleteAll)
                .assertEquals(this.heroRepository::count, 0L)
                .execute(() -> this.heroRepository.persist(hero))
                .assertEquals(this.heroRepository::count, 1L);

        names.forEach(name ->
                asserter.execute(() -> Log.infof("Inside listAllWhereNameLike(%s)", name))
                        .assertThat(
                                () -> this.heroRepository.listAllWhereNameLike(name),
                                heroes ->
                                        Assertions.assertThat(heroes)
                                                .isNotNull()
                                                .hasSize(1)
                                                .first()
                                                .usingRecursiveComparison()
                                                .isEqualTo(hero)
                        )
        );
    }

    @Test
    @TestReactiveTransaction
    public void findAllWhereNameLikeNotFound(UniAsserter asserter) {
        // Doing it this way because UniAsserter doesn't work well with ParameterizedTest
        var names = Stream.of("v", "support", "chocolate", null);

        Hero hero = new Hero();
        hero.setName(DEFAULT_NAME);
        hero.setOtherName(DEFAULT_OTHER_NAME);
        hero.setPicture(DEFAULT_PICTURE);
        hero.setPowers(DEFAULT_POWERS);
        hero.setLevel(DEFAULT_LEVEL);

        asserter.execute(this.heroRepository::deleteAll)
                .assertEquals(this.heroRepository::count, 0L)
                .execute(() -> this.heroRepository.persist(hero))
                .assertEquals(this.heroRepository::count, 1L);

        names.forEach(name ->
                asserter.execute(() -> Log.infof("Inside findAllWhereNameLikeNotFound(%s)", name))
                        .assertThat(
                                () -> this.heroRepository.listAllWhereNameLike(name),
                                heroes ->
                                        Assertions.assertThat(heroes)
                                                .isNotNull()
                                                .isEmpty()
                        )
        );
    }
}
