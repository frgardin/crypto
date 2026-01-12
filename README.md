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
- **POST** `/api/encrypt`
- Encrypts the provided text using AES and returns JSON with Base64 encoded result
- **Request Body**: `{"text": "your text here"}`
- **Response**: `{"text": "encrypted_base64_string"}`

### Decrypt
- **POST** `/api/decrypt`
- Decrypts the provided Base64 encoded encrypted text
- **Request Body**: `{"text": "encrypted text here"}`
- **Response**: `{"text": "decrypted_original_text"}`

### Hash
- **POST** `/api/hash`
- Computes SHA-256 hash of the provided text and returns JSON with hexadecimal string
- **Request Body**: `{"text": "your text here"}`
- **Response**: `{"text": "sha256_hex_hash"}`

### Encrypt File
- **POST** `/api/encrypt-file`
- Encrypts an uploaded file using AES
- **Request**: Multipart form data with `file` parameter
- **Response**: Encrypted file as binary attachment

### Decrypt File
- **POST** `/api/decrypt-file`
- Decrypts an uploaded encrypted file using AES
- **Request**: Multipart form data with `file` parameter
- **Response**: Decrypted file as binary attachment

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

The application will start on http://localhost:8080 (configurable via `SERVER_PORT` environment variable or `server.port` property)

### Testing
```bash
./gradlew test
```

## Example Usage

### Encrypt text
```bash
curl -X POST "http://localhost:8080/api/encrypt" \
     -H "Content-Type: application/json" \
     -d '{"text": "Hello World"}'
```

### Decrypt text
```bash
curl -X POST "http://localhost:8080/api/decrypt" \
     -H "Content-Type: application/json" \
     -d '{"text": "encrypted_text_here"}'
```

### Hash text
```bash
curl -X POST "http://localhost:8080/api/hash" \
     -H "Content-Type: application/json" \
     -d '{"text": "Hello World"}'
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
