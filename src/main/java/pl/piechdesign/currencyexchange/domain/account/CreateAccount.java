package pl.piechdesign.currencyexchange.domain.account;

import pl.piechdesign.currencyexchange.domain.shared.AccountId;

public interface CreateAccount {

    AccountId create(CreateAccountRequest request);
}
