# Applicazione JavaFX per la Gestione degli Animali

## Descrizione
Questa applicazione JavaFX permette di gestire le informazioni relative agli animali, prenotazione visite,  e regimi farmacologici. L'applicazione si interfaccia con un database MySQL locale per memorizzare e recuperare i dati.


## Avvio

### 1. Avviare il Server MySQL
- Avviare (per esempio) XAMPP e avviare il servizio MySQL.

### 2. Configurare il Database
- Aprire (per esempio)**MySQL Workbench**.
- Connettersi al server MySQL locale (di solito `localhost`).
- Caricare il file DDL per creare lo schema del database:
  - Andare su `File` > `Open SQL Script...` e selezionare il file `AnimalettiCoccolosi.ddl` dalla cartella app\src\main\resources\QUERIES AVVIO.
  - Eseguire lo script per creare le tabelle necessarie.

### 3. Popolare il Database
- Caricare il file di popolamento dei dati:
  - Andare su `File` > `Open SQL Script...` e selezionare il file `POPOLAMENTO.sql`dalla cartella app\src\main\resources\QUERIES AVVIO.
  - Eseguire lo script per inserire i dati di esempio nelle tabelle.

### 4. Configurare l'Applicazione
- Importare il progetto in un IDE Java (come IntelliJ IDEA, Eclipse, o NetBeans).
- Assicurarsi che il file `MySQLConnect.java` contenga le corrette credenziali di connessione al database:

  ```java
  // Esempio di MySQLConnect.java
  public class MySQLConnect {
      private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
      private static final String USER = "your_username";
      private static final String PASSWORD = "your_password";

      public static Connection getConnection() throws SQLException {
          return DriverManager.getConnection(URL, USER, PASSWORD);
      }
  }

### 5. Avviare applicazione da terminale
- Aprire terminale e digitare ./gradlew run

da VisualStudioCode si può anche decidere di avviarlo dal IDE con Run->Run Without Debugging
