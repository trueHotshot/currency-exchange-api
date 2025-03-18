package pl.piechdesign.currencyexchange.domain.exchange;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piechdesign.currencyexchange.domain.balance.BalanceRepository;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
class ExchangeFacade implements ExchangeCurrency {

    private final ExchangeRateProvider exchangeRateProvider;
    private final BalanceRepository balanceRepository;

    @Transactional
    @Override
    public void exchange(ExchangeRequest request) {
        var wallet = new Wallet(AccountId.of(request.getAccountId()), balanceRepository, exchangeRateProvider);

        wallet.exchangeCurrency(request.getFrom(), request.getTo(), new BigDecimal(request.getAmount()));

        var plnBalance = wallet.getBalance(CurrencyType.PLN);
        var usdBalance = wallet.getBalance(CurrencyType.USD);

        balanceRepository.save(plnBalance);
        balanceRepository.save(usdBalance);
    }
}
