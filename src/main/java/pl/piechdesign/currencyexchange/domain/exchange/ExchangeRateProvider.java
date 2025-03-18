package pl.piechdesign.currencyexchange.domain.exchange;

import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;

import java.math.BigDecimal;

public interface ExchangeRateProvider {

    BigDecimal getExchangeRate(CurrencyType from, CurrencyType to);
}