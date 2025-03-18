package pl.piechdesign.currencyexchange.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piechdesign.currencyexchange.domain.account.AccountDto;
import pl.piechdesign.currencyexchange.domain.account.CreateAccount;
import pl.piechdesign.currencyexchange.domain.account.CreateAccountRequest;
import pl.piechdesign.currencyexchange.domain.account.GetAccount;
import pl.piechdesign.currencyexchange.domain.shared.AccountId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
@Tag(name = "Konta walutowe", description = "API do zarządzania kontami walutowymi")
class AccountRestController {

    private static final Logger logger = LogManager.getLogger(AccountRestController.class);

    private final CreateAccount createAccount;
    private final GetAccount getAccount;

    @PostMapping
    @Operation(
            summary = "Zakładanie konta",
            description = "Tworzy nowe konto walutowe z podanym saldem początkowym."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Konto zostało utworzone"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Nieprawidłowe dane wejściowe",
            content = @Content
    )
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request) {
        logger.info("Tworzenie nowego konta dla użytkownika: {} {}", request.getFirstName(), request.getLastName());

        return new ResponseEntity<>(createAccount.create(request).toString(), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    @Operation(
            summary = "Pobranie danych konta",
            description = "Zwraca informacje o koncie i jego stanie."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Dane konta zostały zwrócone"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Konto nie istnieje",
            content = @Content
    )
    public AccountDto getAccount(@PathVariable String accountId) {
        logger.info("Pobieranie danych konta o ID: {}", accountId);
        return getAccount.getAccount(AccountId.of(accountId));
    }


}