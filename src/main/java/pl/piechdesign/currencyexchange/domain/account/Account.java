package pl.piechdesign.currencyexchange.domain.account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;
import pl.piechdesign.currencyexchange.domain.shared.BaseEntity;
import pl.piechdesign.currencyexchange.domain.shared.DomainException;

import java.util.Objects;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Account extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private final AccountId id = AccountId.newOne();

    private String firstName;
    private String lastName;

    public Account(String firstName, String lastName) {
        validateName(firstName, "Imie");
        validateName(lastName, "Nazwisko");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new DomainException(fieldName + " nie moze byc puste");
        }

        if (name.length() > 100) {
            throw new DomainException(fieldName + " moze miec maksimum 100 znakow");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
