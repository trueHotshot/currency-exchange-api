package pl.piechdesign.currencyexchange.domain.balance;

import lombok.Getter;
import pl.piechdesign.currencyexchange.domain.shared.DomainException;

import java.math.BigDecimal;

@Getter
public class InsufficientFundsExceptions extends DomainException {

    private final CurrencyType currency;
    private final BigDecimal requested;
    private final BigDecimal available;

    public InsufficientFundsExceptions(CurrencyType currency, BigDecimal requested, BigDecimal available) {
        super(String.format("Niewystarczajace srodki dla %s: zadane %s, dostepne %s",
                currency, requested.toPlainString(), available.toPlainString()));
        this.currency = currency;
        this.requested = requested;
        this.available = available;
    }
}