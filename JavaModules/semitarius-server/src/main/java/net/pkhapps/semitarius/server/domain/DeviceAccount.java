package net.pkhapps.semitarius.server.domain;

import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO Document me!
 */
@Entity
@Table(name = DeviceAccount.TABLE_NAME)
@DiscriminatorValue("device")
public class DeviceAccount extends AbstractUser {

    static final String TABLE_NAME = "device_accounts";
    static final String COL_USER_ACCOUNT = "user_account_id";
    static final String COL_LOGIN_ID = "login_id";
    static final String COL_LOGIN_KEY = "login_key";
    static final String COL_LOCKED = "locked";
    static final String COL_DESCRIPTION = "description";

    @ManyToOne(optional = false)
    @JoinColumn(name = COL_USER_ACCOUNT, nullable = false)
    private UserAccount userAccount;

    @Column(name = COL_LOGIN_ID, unique = true, nullable = false)
    private String loginId;

    @Column(name = COL_LOGIN_KEY, nullable = false)
    private String loginKey;

    @Column(name = COL_LOCKED, nullable = false)
    private boolean locked = false;

    @Column(name = COL_DESCRIPTION)
    private String description;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    DeviceAccount() {
    }

    public DeviceAccount(@NotNull UserAccount userAccount,
                         @NotNull String loginId,
                         @NotNull String loginKey) {
        super(Objects.requireNonNull(userAccount, "userAccount must not be null").getTenant().orElse(null));
        this.userAccount = userAccount;
        this.loginKey = Strings.requireNonEmpty(loginKey, "loginKey must not be empty");
        this.loginId = Strings.requireNonEmpty(loginId, "loginId must not be null");
    }

    @NotNull
    public UserAccount getUserAccount() {
        return userAccount;
    }

    @NotNull
    public String getLoginKey() {
        return loginKey;
    }

    @NotNull
    public String getLoginId() {
        return loginId;
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

    @NotNull
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    @NotNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getAuthorities();
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        this.locked = true;
        // TODO Fire domain event?
    }

    public void unlock() {
        this.locked = false;
        // TODO Fire domain event?
    }
}
