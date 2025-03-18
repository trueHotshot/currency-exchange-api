package pl.piechdesign.currencyexchange.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
@AllArgsConstructor
public class CreateAccountRequest implements Serializable {

    String firstName;

    String lastName;

    String initialBalance;

    @JsonIgnore
    public BigDecimal getInitialBalanceValue() {
        return new BigDecimal(initialBalance);
    }
}