# Autorstwo i przeznaczenie:
Program został wykonany na potrzeby rekrutacji do firmy NGN IT Solutions GmbH w 2022r.
Autor: Michał Kurzyk

# Wymagane narzędzia:
Wydawca nie gwarantuje poprawnego działania programu z narzędziami w wersji starszymi niż wypisano poniżej:
- Maven 3.6.3
- Java JDK 15.0.2

# Założenia projektu
- Program z założenia nie wspiera nawiasów [założenie od klienta].
- Separatorem dziesiętnym jest zarówno kropka '.', jak i przecinek ','. Nie używa się separatora tysięcznego.
- Operatorem do wykonywania pierwiastków jest 'r'. (przykład: 125r3 = 5)
- Użytkownik w celu otrzymania wyniku musi wprowadzić do programu poprawnie zapisane działanie, w przeciwnym przypadku program zwróci komunikat o niepoprawnym formacie.
- Dozwolone jest wprowadzanie więcej niż jedno działanie na raz.
- Dzielenie przez zero daje wynik nieskończoność (eng. infinity)
- Białe znaki są usuwane przez program. Nie zaleca się wprowadzać białych znaków, zwłaszcza wewnątrz działania. Białe znaki mogą przyczynić się do ludzkich pomyłek podczas odczytywania wyniku. (objaśnienie: przykład: 3 5 = 35).
- Program stworzony jest w angielskiej wersji językowej i na etapie planowania nie zakładano innej.