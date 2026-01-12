# Crypto Application

A Spring Boot application that provides AES encryption and decryption services via REST API endpoints.

## Features

- AES-128 encryption/decryption
- RESTful API endpoints
- Automatic secret key generation and persistence
- Base64 encoded encrypted output

## Technologies

- Java 25
- Spring Boot 4.0.1
- Gradle
- AES encryption

## API Endpoints

### Encrypt
- **GET** `/api/encrypt?text={text}`
- Encrypts the provided text using AES and returns Base64 encoded result

### Decrypt
- **GET** `/api/decrypt?text={encryptedText}`
- Decrypts the provided Base64 encoded encrypted text

### Hash
- **GET** `/api/hash?text={text}`
- Computes SHA-256 hash of the provided text and returns hexadecimal string

## Security Notes

⚠️ **Important**: The secret key is stored in `src/main/resources/static/secret.key` as a Base64 encoded string. This is for demonstration purposes only. In production, keys should never be stored in publicly accessible locations. Consider using environment variables, secure vaults, or configuration services instead.

## Running the Application

### Prerequisites
- Java 25
- Gradle

### Build and Run
```bash
./gradlew bootRun
```

The application will start on http://localhost:8080

### Testing
```bash
./gradlew test
```

## Example Usage

### Encrypt text
```bash
curl "http://localhost:8080/api/encrypt?text=Hello%20World"
```

### Decrypt text
```bash
curl "http://localhost:8080/api/decrypt?text=<encrypted_text>"
```

### Hash text
```bash
curl "http://localhost:8080/api/hash?text=Hello%20World"
```

## Project Structure

- `src/main/java/org/gardin/felipe/crypto/` - Main application code
  - `CryptoApplication.java` - Main Spring Boot application class
  - `CryptoController.java` - REST controller with encrypt/decrypt endpoints
  - `CryptoService.java` - Service class handling encryption logic
  - `SecretKeyConfig.java` - Configuration for secret key bean
  - `ResourcePath.java` - Constants for API paths
- `src/test/java/org/gardin/felipe/crypto/` - Test classes
- `src/main/resources/` - Application resources
- `src/main/resources/static/` - Static resources (contains secret key)
