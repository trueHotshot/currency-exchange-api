package pl.piechdesign.currencyexchange.domain.account;

import pl.piechdesign.currencyexchange.domain.shared.AccountId;

public interface GetAccount {

    AccountDto getAccount(AccountId id);
}
