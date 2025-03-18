package pl.piechdesign.currencyexchange.domain.exchange;

import lombok.Value;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;

@Value
public class ExchangeRequest {

    String amount;
    CurrencyType from;
    CurrencyType to;
    String accountId;

    public ExchangeRequest(String amount, CurrencyType from, CurrencyType to, String accountId) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.accountId = accountId;
    }
}
