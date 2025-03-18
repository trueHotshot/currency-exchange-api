package pl.piechdesign.currencyexchange.infrastructure;

import pl.piechdesign.currencyexchange.domain.shared.DomainException;

public class ExchangeRateException extends DomainException {

    public ExchangeRateException(String message) {
        super(message);
    }

    public ExchangeRateException(String message, Throwable cause) {
        super(message, cause);
    }
}
