package pl.piechdesign.currencyexchange.domain.balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    List<Balance> findByAccountId(AccountId accountId);

    Optional<Balance> findByAccountIdAndCurrency(AccountId accountId, CurrencyType currency);
}
