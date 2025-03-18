package pl.piechdesign.currencyexchange.domain.shared;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class AccountId implements Serializable {

    public static AccountId newOne() {
        return new AccountId(UUID.randomUUID().toString());
    }

    public static AccountId of(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new DomainException("Nieprawidłowy identyfikator konta");
        }
        return new AccountId(id);
    }

    public static void check(AccountId accountId) {
        if (accountId == null) {
            throw new DomainException("Id konta nie moze byc null");
        }
    }

    @Column(name = "account_id")
    private String value;

    private AccountId(String value) {
        this.value = value;
    }

    private AccountId() {
    }

    public String id() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId1 = (AccountId) o;
        return Objects.equals(value, accountId1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
