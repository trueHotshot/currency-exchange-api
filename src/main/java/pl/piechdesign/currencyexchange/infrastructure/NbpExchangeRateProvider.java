package pl.piechdesign.currencyexchange.infrastructure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;
import pl.piechdesign.currencyexchange.domain.exchange.ExchangeRateProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class NbpExchangeRateProvider implements ExchangeRateProvider {

    private static final Logger logger = LogManager.getLogger(NbpExchangeRateProvider.class);
    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/C/USD/?format=json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BigDecimal getExchangeRate(CurrencyType from, CurrencyType to) {
        if (from == to) {
            return BigDecimal.ONE;
        }

        try {
            var response = restTemplate.getForObject(NBP_API_URL, NbpApiResponse.class);
            var rates = response.rates;

            if (rates != null && !rates.isEmpty()) {
                BigDecimal rate = getRate(rates.get(0), from, to);
                logger.info("Pobrano kurs {} -> {}: {}", from, to, rate);
                return rate;
            }
        } catch (Exception e) {
            logger.error("Blad podczas pobierania kursu walut z NBP: {}", e.getMessage());
        }

        throw new ExchangeRateException("Kurs wymiany nie dostepny dla " + from + " -> " + to);

    }

    private BigDecimal getRate(Rate rate, CurrencyType from, CurrencyType to) {
        if (from == CurrencyType.USD && to == CurrencyType.PLN) {
            return new BigDecimal(rate.bid);
        }
        if (from == CurrencyType.PLN && to == CurrencyType.USD) {
            return BigDecimal.ONE.divide(new BigDecimal(rate.ask), 6, RoundingMode.HALF_UP);
        }

        throw new ExchangeRateException("Wymiana " + from + " -> " + to + " nie jest obslugiwana");
    }

    public record NbpApiResponse(List<Rate> rates) {
    }

    public record Rate(String bid, String ask) {
    }
}
