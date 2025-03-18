package pl.piechdesign.currencyexchange.domain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piechdesign.currencyexchange.domain.balance.Balance;
import pl.piechdesign.currencyexchange.domain.balance.BalanceRepository;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
class AccountFacade implements CreateAccount, GetAccount {

    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Transactional
    @Override
    public AccountId create(CreateAccountRequest request) {
        Account account = new Account(request.getFirstName(), request.getLastName());
        accountRepository.save(account);

        Balance plnBalance = new Balance(account.getId(), request.getInitialBalanceValue(), CurrencyType.PLN);
        balanceRepository.save(plnBalance);

        Balance usdBalance = new Balance(account.getId(), BigDecimal.ZERO, CurrencyType.USD);
        balanceRepository.save(usdBalance);

        return account.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public AccountDto getAccount(AccountId id) {
        var account = accountRepository.findExactlyOneById(id);

        var balances = balanceRepository.findByAccountId(id);

        return mapToDto(account, balances);
    }

    private AccountDto mapToDto(Account account, List<Balance> balances) {
        var balanceDtos = balances.stream()
                .map(it -> new AccountDto.BalanceDto(
                                it.getCurrency(),
                                new DecimalFormat("0.00").format(it.getAmount())
                        ))
                .toList();

        return new AccountDto(
                account.getId().toString(),
                account.getFirstName(),
                account.getLastName(),
                balanceDtos
        );
    }
}
