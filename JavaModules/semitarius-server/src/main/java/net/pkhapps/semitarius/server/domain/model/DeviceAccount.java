package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * TODO Document me!
 */
@Entity
@Table(name = DeviceAccount.TABLE_NAME)
@DiscriminatorValue("device")
public class DeviceAccount extends AbstractUser {

    static final String TABLE_NAME = "device_accounts";
    static final String COL_USER_ACCOUNT = "user_account_id";
    static final String COL_LOGIN_KEY = "login_key";

    @ManyToOne(optional = false)
    @JoinColumn(name = COL_USER_ACCOUNT, nullable = false)
    private UserAccount userAccount;

    @Column(name = COL_LOGIN_KEY, unique = true, nullable = false)
    private String loginKey;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    DeviceAccount() {
    }

    public DeviceAccount(@NotNull UserAccount userAccount,
                         @NotNull String loginKey) {
        super(Objects.requireNonNull(userAccount, "userAccount must not be null").getTenant().orElse(null));
        this.userAccount = userAccount;
        this.loginKey = Strings.requireNonEmpty(loginKey, "loginKey must not be empty");
    }

    @NotNull
    public UserAccount getUserAccount() {
        return userAccount;
    }

    @NotNull
    public String getLoginKey() {
        return loginKey;
    }

    @Override
    @NotNull
    public String getUsername() {
        return userAccount.getUsername();
    }

    @Override
    @NotNull
    public String getFullName() {
        return userAccount.getFullName();
    }

    @Override
    @NotNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getAuthorities();
    }
}
