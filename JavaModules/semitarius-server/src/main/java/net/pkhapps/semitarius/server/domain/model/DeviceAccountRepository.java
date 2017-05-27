package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link DeviceAccount}s.
 */
public interface DeviceAccountRepository extends Repository<DeviceAccount> {

    Optional<DeviceAccount> findByLoginKey(String loginKey);

    List<DeviceAccount> findByUserAccount(UserAccount userAccount);
}
