package pl.piechdesign.currencyexchange.domain.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountId){
        super("Nie znaleziono konta o podanym id: " + accountId,
                null,
                false,
                false);
    }
}