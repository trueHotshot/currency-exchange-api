package pl.piechdesign.currencyexchange.domain.account;

import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;

import java.util.List;

public record AccountDto(String id, String firstName, String lastName, List<BalanceDto> balances) {

    public record BalanceDto(CurrencyType currency, String amount) {}
}
