package pl.piechdesign.currencyexchange.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pl.piechdesign.currencyexchange.domain.balance.CurrencyType;
import pl.piechdesign.currencyexchange.domain.exchange.ExchangeCurrency;
import pl.piechdesign.currencyexchange.domain.exchange.ExchangeRateProvider;
import pl.piechdesign.currencyexchange.domain.exchange.ExchangeRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchanges")
@Tag(name = "Konta walutowe", description = "API do zarządzania kontami walutowymi")
public class ExchangeRestController {

    private static final Logger logger = LogManager.getLogger(ExchangeRestController.class);

    private final ExchangeCurrency exchangeCurrency;
    private final ExchangeRateProvider exchangeRateProvider;

    @PostMapping
    @Operation(
            summary = "Wymiana walut",
            description = "Wymienia walutę na koncie według aktualnego kursu NBP."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Wymiana zakończona sukcesem"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Nieprawidłowe dane wejściowe",
            content = @Content
    )
    public void exchangeCurrency(@RequestBody ExchangeRequest request) {
        logger.info("Wymiana waluty {} -> {} na kwotę {} dla konta: {}",
                request.getFrom(), request.getTo(), request.getAmount(), request.getAccountId());
        exchangeCurrency.exchange(request);
    }

    @GetMapping("/rates/usd")
    @Operation(
            summary = "Kursy wymiany walut",
            description = "Pobierz akutalne kursy nbp dla kupna/sprzedaży waluty."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pobrano aktualne kursy nbp"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Nieprawidłowe dane wejściowe",
            content = @Content
    )
    public String getCurrentRate(@RequestParam CurrencyType from, CurrencyType to) {
        logger.info("Pobieranie kursu {} -> {}", from, to);
        return exchangeRateProvider.getExchangeRate(from, to).toString();
    }
}
