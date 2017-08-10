package net.pkhapps.semitarius.server.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * TODO Document me
 */
public interface UserOwnedAggregateRoot {

    @NotNull
    Optional<UserAccount> getOwningUser();
}
