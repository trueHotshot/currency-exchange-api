# CurrencyExchange

Aplikacja posiada REST API pozwalające na założenie konta walutowego

The technology behind it: 
* Java 17
* Postgres 14
* Spring Boot 3.4.3

## Instalacja

#### Przy użyciu `docker-compose`

In the terminal run the following command:
```console
$ docker-compose up
``` 

#### Przy użyciu Mavena (z bazą w pamięci (H2) lub lokalną instancją Postgresa)

Pierwsza kompilacja:

```console
$ mvn clean package
```

Następnie można wybrać jeden z dwóch profili uruchomieniowych. Domyślnie aplikacja działa w oparciu o bazę H2 i jest uruchomiana komendą:

```console
$ mvn spring-boot:run 
```

Uruchomienie profilu z bazą Postgres (sprawdź i skonfiguruj konfigurację połączenia w pliku - `src/main/resources/application.yml` dla profilu *local-postgres*). Aplikację w tym profilu uruchomia się komendą:
```console
$ mvn spring-boot:run -P local-postgres
```

#### Przy użyciu IntelliJ (z H2 lub Postgres)

Ustaw konfigurację uruchomienia klasy `CurrencyExchangeApplication.java` poprzez dodanie `--spring.profiles.active=h2` lub `--spring.profiles.active=postgres` w sekcji **Program arguments**.

Następnie uruchom klasę `CurrencyExchangeApplication.java`

