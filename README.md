# 📌 Currency Converter API

*A simple and efficient API for real-time currency conversion, built with Spring Boot.*

---

## 📖 Overview  
The **Currency Converter API** allows users to:  
✅ Fetch real-time **exchange rates** between two currencies.  
✅ Convert an **amount** from one currency to another based on exchange rates.  

This API integrates with a **public exchange rate provider** to ensure up-to-date conversions.

---

## 🚀 API Endpoints  

### 1️⃣ Get Exchange Rate  
**📌 Endpoint:** `GET /api/rates`  
**🔹 Description:** Fetches exchange rates for a given base currency (default: `USD`).  
**🔹 Query Parameters:**  
| Parameter | Type | Description |
|-----------|------|-------------|
| `base` | `string` | (Optional) Base currency code (e.g., `USD`). Default: `USD`. |

**✅ Example Request:**  
```sh
GET http://localhost:8080/api/rates?base=USD
```
**📥 Sample Response (`200 OK`)**  
```json
{
    "base": "USD",
    "rates": {
        "INR": 85.83,
        "EUR": 0.94,
        "GBP": 0.82
    }
}
```
**❌ Error Responses:**  
- `400 Bad Request` → Invalid input parameters.  

---

### 2️⃣ Convert Currency  
**📌 Endpoint:** `POST /api/convert`  
**🔹 Description:** Converts an amount from one currency to another using real-time exchange rates.  
**🔹 Request Body:**  
```json
{
    "from": "USD",
    "to": "INR",
    "amount": 100
}
```
**✅ Example Request:**  
```sh
POST http://localhost:8080/api/convert
```
**📥 Sample Response (`200 OK`)**  
```json
{
    "from": "USD",
    "to": "INR",
    "amount": 100,
    "convertedAmount": 8579.679
}
```
**❌ Error Responses:**  
- `400 Bad Request` → Invalid currency codes or missing parameters.  
- `500 Internal Server Error` → External API failure.  

---

## 🔧 Running the Project Locally  

### 🛠 Prerequisites  
- **Java Development Kit (JDK 17+)**  
- **Maven**  
- **Open Exchange Rates API Key** (if using a real API)  

### 📌 Steps to Run Locally  
1️⃣ **Clone the repository**  
```sh
git 
cd currency-converter-api
```
2️⃣ **Set up environment variables**  
- Configure `EXCHANGE_API_URL` in your system or **hardcode it** in:  
  ```
  src/main/java/com/example/currencyconverter/utils/ExternalApiClient.java
  ```

3️⃣ **Build the project**  
```sh
mvn clean install
```
4️⃣ **Run the application**  
```sh
mvn spring-boot:run
```
5️⃣ **Access the API**  
- The application will start at **`http://localhost:8080`**  

---

## 🧪 Running Tests  
To ensure everything works perfectly, run:  
```sh
mvn test
```

---

## 📌 Tech Stack  
🔹 **Java 17**  
🔹 **Spring Boot** (REST API)  
🔹 **Maven** (Dependency Management)  
🔹 **JUnit** (Unit Testing)  
🔹 **External API Integration**  

---

## 🎯 Future Enhancements  
🚀 Add Swagger API Documentation 📜  
🚀 Implement Caching for Faster Responses ⚡  
🚀 Deploy on Cloud (AWS, Railway, Render) ☁️  

---

### 💡 Author  
Developed with ❤️ by **sadhak K**.  

---

