# ♟️ Spring Chess App

Aplikacja webowa do gry w szachy stworzona z użyciem **Spring Boot** jako backendu oraz **Thymeleaf** jako silnika szablonów do renderowania HTML. Gra obsługiwana jest w pełni po stronie serwera – zarówno mechanika, jak i walidacja ruchów. Dane przechowywane są w **MySQL**, a komunikacja z klientem (np. przesyłanie ruchów) realizowana jest za pomocą **WebSocketów**. Projekt jest w aktywnej fazie rozwoju.

## 🧪 Co już działa?

- ✅ Rejestracja i logowanie graczy
- ✅ Profil użytkownika z możliwością edycji danych
- ✅ System rankingowy
- ✅ Historia rozegranych partii (z możliwością ich przeglądania)
- ✅ Możliwość dołączenia do wcześniej rozegranych gier
- ✅ WebSocket do przesyłania ruchów w czasie rzeczywistym
- ✅ Backendowa obsługa logiki gry (walidacja, ruchy, zapis stanu)
- ✅ Templaty HTML gotowe dla większości widoków

---

## 🚧 Aktualnie w trakcie prac

Obecnie pracuję nad:

- 🔄 Systemem **lobby**, czyli mechanizmem szukania i dopasowywania przeciwników online
- 🔍 Dodaniem **filtrowania** i **sortowania wyników** w:
  - tabeli rankingowej
  - historii rozegranych partii (np. po dacie, przeciwniku, wyniku)

---

## 🛠️ Technologie

- **Java 17+**
- **Spring Boot (MVC, Security, WebSocket, Data JPA)**
- **Thymeleaf (frontend)**
- **MySQL (baza danych)**
- **Lombok, Bootstrap**

---

## 🚀 Jak uruchomić projekt lokalnie?

### Skonfiguruj MySQL:
1. **Klonuj repozytorium:**
   ```bash
   git clone https://github.com/twoj-user/chess-springboot.git
   cd chess-springboot
   
2. **Utwórz bazę danych `chess_db` i zaktualizuj dane dostępu w `application.properties`:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chess_db
spring.datasource.username=root
spring.datasource.password=twojehaslo
spring.jpa.hibernate.ddl-auto=update
```
3. **Uruchom aplikacje**
   
4.  **Wejdź na http://localhost:8080**

