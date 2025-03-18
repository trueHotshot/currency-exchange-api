package pl.piechdesign.currencyexchange.domain.exchange;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.piechdesign.currencyexchange.domain.balance.Balance;
import pl.piechdesign.currencyexchange.domain.balance.BalanceRepository;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;
import pl.piechdesign.currencyexchange.domain.shared.DomainException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Wallet {

    private AccountId accountId;
    private ExchangeRateProvider exchangeRateProvider;
    private Map<CurrencyType, Balance> balances;

    public Wallet(
            AccountId accountId,
            BalanceRepository balanceRepository,
            ExchangeRateProvider exchangeRateProvider
    ) {
        AccountId.check(accountId);

        this.accountId = accountId;
        this.exchangeRateProvider = exchangeRateProvider;
        this.balances = balanceRepository.findByAccountId(accountId).stream()
                .collect(Collectors.toMap(Balance::getCurrency, item -> item));
    }

    public Balance getBalance(CurrencyType currency) {
        return balances.get(currency);
    }

    public void exchangeCurrency(CurrencyType from, CurrencyType to, BigDecimal amount) {
        Balance sourceBalance = getBalance(from);
        if (sourceBalance == null) {
            throw new DomainException("Bład salda dla waluty: " + from);
        }

        Balance targetBalance = getBalance(to);
        if (targetBalance == null) {
            throw new DomainException("Bład salda dla waluty: " + to);
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Kwota wymiany musi być dodatnia");
        }

        var exchangeRate = exchangeRateProvider.getExchangeRate(from, to);

        if (exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Kurs wymiany musi być dodatni");
        }

        sourceBalance.subtractAmount(amount);

        BigDecimal convertedAmount = amount.multiply(exchangeRate);
        targetBalance.addAmount(convertedAmount);
    }
}