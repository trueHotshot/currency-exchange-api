package pl.piechdesign.currencyexchange.domain.balance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;
import pl.piechdesign.currencyexchange.domain.shared.BaseEntity;
import pl.piechdesign.currencyexchange.domain.shared.DomainException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SequenceGenerator(
        name = "balances_id_seq_generator",
        sequenceName = "seq_balances",
        allocationSize = 1
)
@Entity
@Table(name = "balances")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Balance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balances_id_seq_generator")
    private Long id;

    @Column(nullable = false)
    @Embedded
    private AccountId accountId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Column(nullable = false)
    private LocalDateTime lastUpdatedAt;

    public Balance(AccountId accountId, BigDecimal amount, CurrencyType currency) {
        AccountId.check(accountId);
        validateAmount(amount);

        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new DomainException("Kwota nie moze byc null");
        }

        if (amount.scale() > 4) {
            throw new DomainException("Kwota nie moze miec wiecej niz 4 miejsca po przecinku");
        }
    }

    public void addAmount(BigDecimal amountToAdd) {
        if (amountToAdd.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Dodawana kwota musi byc dodatnia");
        }

        this.amount = this.amount.add(amountToAdd);
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public void subtractAmount(BigDecimal amountToSubtract) {
        validateAmount(amountToSubtract);

        if (amountToSubtract.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Odejmowana kwota musi byc dodatnia");
        }

        if (this.amount.compareTo(amountToSubtract) < 0) {
            throw new InsufficientFundsExceptions(currency, amountToSubtract, this.amount);
        }

        this.amount = this.amount.subtract(amountToSubtract);
        this.lastUpdatedAt = LocalDateTime.now();
    }
}
