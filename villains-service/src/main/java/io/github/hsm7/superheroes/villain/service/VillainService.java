package io.github.hsm7.superheroes.villain.service;

import static jakarta.transaction.Transactional.TxType.*;

import java.util.List;
import java.util.Optional;

import io.github.hsm7.superheroes.villain.config.VillainConfig;
import io.github.hsm7.superheroes.villain.mapping.VillainMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

import io.quarkus.logging.Log;
import io.github.hsm7.superheroes.villain.Villain;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

/**
 * Service class containing business methods for the application.
 */
@ApplicationScoped
@Transactional(REQUIRED)
public class VillainService {
    @Inject
    Validator validator;

    @Inject
    VillainConfig villainConfig;

    @Inject
    VillainMapper villainMapper;

    @Transactional(SUPPORTS)
    @WithSpan("VillainService.findAllVillains")
    public List<Villain> findAllVillains() {
        Log.debug("Getting all villains");
        return Optional.ofNullable(Villain.<Villain>listAll())
                .orElseGet(List::of);
    }

    @Transactional(SUPPORTS)
    @WithSpan("VillainService.findAllVillainsHavingName")
    public List<Villain> findAllVillainsHavingName(@SpanAttribute("arg.name") String name) {
        Log.debugf("Finding all villains having name = %s", name);
        return Optional.ofNullable(Villain.listAllWhereNameLike(name))
                .orElseGet(List::of);
    }

    @Transactional(SUPPORTS)
    @WithSpan("VillainService.findVillainById")
    public Optional<Villain> findVillainById(@SpanAttribute("arg.id") Long id) {
        Log.debugf("Finding villain by id = %d", id);
        return Villain.findByIdOptional(id);
    }

    @Transactional(SUPPORTS)
    @WithSpan("VillainService.findRandomVillain")
    public Optional<Villain> findRandomVillain() {
        Log.debug("Finding a random villain");
        return Villain.findRandom();
    }

    @WithSpan("VillainService.persistVillain")
    public Villain persistVillain(@SpanAttribute("arg.villain") @NotNull @Valid Villain villain) {
        Log.debugf("Persisting villain: %s", villain);
        villain.level = (int) Math.round(villain.level * this.villainConfig.level().multiplier());
        Villain.persist(villain);

        return villain;
    }

    @WithSpan("VillainService.replaceVillain")
    public Optional<Villain> replaceVillain(@SpanAttribute("arg.villain") @NotNull @Valid Villain villain) {
        Log.debugf("Replacing villain: %s", villain);
        return Villain.findByIdOptional(villain.id)
                .map(Villain.class::cast) // Only here for type erasure within the IDE
                .map(v -> this.villainMapper.update(villain, v));
    }

    @WithSpan("VillainService.partialUpdateVillain")
    public Optional<Villain> partialUpdateVillain(@SpanAttribute("arg.villain") @NotNull Villain villain) {
        Log.debugf("Partially updating villain: %s", villain);
        return Villain.findByIdOptional(villain.id)
                .map(Villain.class::cast) // Only here for type erasure within the IDE
                .map(v -> this.villainMapper.patch(villain, v))
                .map(this::validatePartialUpdate);
    }

    @WithSpan("VillainService.replaceAllVillains")
    public void replaceAllVillains(@SpanAttribute("arg.villains") List<Villain> villains) {
        Log.debug("Replacing all villains");
        deleteAllVillains();
        Villain.persist(villains);
    }

    private Villain validatePartialUpdate(Villain villain) {
        var violations = this.validator.validate(villain);

        if ((violations != null) && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return villain;
    }

    @WithSpan("VillainService.deleteAllVillains")
    public void deleteAllVillains() {
        Log.debug("Deleting all villains");
        List<Villain> villains = Villain.listAll();
        villains.stream()
                .map(v -> v.id)
                .forEach(this::deleteVillain);
    }

    @WithSpan("VillainService.deleteVillain")
    public void deleteVillain(@SpanAttribute("arg.id") Long id) {
        Log.debugf("Deleting villain by id = %d", id);
        Villain.deleteById(id);
    }
}
