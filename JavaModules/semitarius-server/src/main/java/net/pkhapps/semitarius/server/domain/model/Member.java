package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Aggregate root representing a member of an organization/tenant. Most of the members are also users of the system,
 * but they have been kept distinct from the users since there may also be users who are not members. For fire
 * departments, a member is a firefighter.
 */
@Entity
@Table(name = Member.TABLE_NAME)
public class Member extends TenantOwnedAggregateRoot {

    static final String TABLE_NAME = "members";
    static final String COL_FIRST_NAME = "first_name";
    static final String COL_LAST_NAME = "last_name";
    static final String COL_EMAIL = "email";
    static final String COL_PHONE_NUMBER = "phoneNumber";

    @Column(name = COL_FIRST_NAME)
    private String firstName;

    @Column(name = COL_LAST_NAME)
    private String lastName;

    @Column(name = COL_EMAIL)
    private String email;

    @Column(name = COL_PHONE_NUMBER)
    private String phoneNumber;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    Member() {
    }

    public Member(@NotNull Tenant tenant, String firstName, String lastName) {
        super(tenant);
        this.firstName = Strings.requireNonEmpty(firstName, "firstName must not be empty");
        this.lastName = Strings.requireNonEmpty(lastName, "lastName must not be empty");
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = Strings.requireNonEmpty(firstName, "firstName must not be empty");
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = Strings.requireNonEmpty(lastName, "lastName must not be empty");
    }

    @NotNull
    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @NotNull
    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
