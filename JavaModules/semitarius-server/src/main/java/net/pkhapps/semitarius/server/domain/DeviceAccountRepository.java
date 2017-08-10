package net.pkhapps.semitarius.server.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository of {@link DeviceAccount}s.
 */
public interface DeviceAccountRepository extends Repository<DeviceAccount> {

    Optional<DeviceAccount> findByLoginId(String loginId);

    List<DeviceAccount> findByUserAccount(UserAccount userAccount);
}
