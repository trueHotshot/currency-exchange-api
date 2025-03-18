package pl.piechdesign.currencyexchange.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;

interface AccountRepository extends JpaRepository<Account, AccountId> {

    default Account findExactlyOneById(AccountId id) {
        return findById(id).orElseThrow(() -> new AccountNotFoundException(id.toString()));
    }
}