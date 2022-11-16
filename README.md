# retoeurekalabs
* Aplicación para obtener información sobre el mercado de valores.
* La aplicación está compilada con Java 17 y el administrador de dependencias es gradle.
* La aplicación está diseñada con arquitectura hexagonal para favorecer el diseño basado en dominios.
* La aplicación se implementa en Heroku: https://stock-market-api-service.herokuapp.com

### Inscribirse ###
El endpoint `POST` `/api/auth/signup` se usa para crear el registro de usuario y poder invocar el endpoint para obtener el token de autorización
```json
{
"email":"your.email@email.com",
"password":"admin123456",
"name":"name",
"lastName":"lastName"
}
```
Si el registro es exitoso, debe esperar la siguiente respuesta:
`status 200`
```json
{
  "message": "User registered successfully!"
}
```

### SignIn ###

El endpoint `POST` `/api/auth/signin` se utiliza para autenticarse como usuario registrado a fin de obtener un token de autorización.
```json
{
  "email": "your.email@email.com",
  "password": "admin123456"
}
```

Si la autenticación es exitosa, debe esperar la siguiente respuesta:
`status 200`
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VyLmVtYWlsQGVtYWlsLmNvbSIsImlzcyI6IlN0b3JtcGF0aCIsImV4cCI6MTY2NzMzNTIxMywiaWF0IjoxNjY3MjQ4ODEzfQ.SgIes24Z2_pwaPsB_mhkkgT38BbPFJJmt6NhvUDc0oll6zBxRyl4wtp7WRUB5ccvdDXFuoh0vJdlPb9c3wv9uw",
  "email": "your.email@email.com",
  "roles": [
    "ROLE_ADMIN"
  ]
}
```

### StockMarketInfo ###

Con el endpoint `GET` `/api/stock-market/stock-info?stock-symbol={SYMBOL}&time-series={TIME_SERIES}` puede obtener la información bursátil de un valor de mercado _SYMBOL_.
Puede probar algunas de las siguientes acciones para _SYMBOL_
* IBM
*AAPL
*MSFT
*GOOGL
*AMZN

Debe especificar un _TIME_SERIES_. Le permitirá calcular la información del mercado de forma diaria, semanal o mensual.
Aquí las TIME_SERIES disponibles
* TIME_SERIES_DAILY
* TIME_SERIES_WEEKLY
* TIME_SERIES_MENSUAL

También debe incluir una autorización de tipo bearer token con el token resultante del servicio `/api/auth/signin`.

La respuesta esperada es la siguiente:
`status 200`
```json
{
  "openPrice": 138.06,
  "higherPrice": 122.15,
  "lowerPrice": 115.545,
  "variationPercentageTwoLastClosingPrices": -0.15883419
}
```

# Requisitos previos
* Debe tener JDK 17 instalado y configurado.
* Debes tener docker instalado (opcional tambien se puede correr sin docker y sin imagen).

# Cómo implementar localmente
* Ejecute el comando $`.\gradlew build` para compilar la aplicación con el envoltorio de gradle
* Ejecute el comando $`docker build --tag=retoeurekalabs:latest .` para construir la imagen opcional)
* Ejecute el comando $`docker run retoeurekalabs` para ejecutar el contenedor docker. Esto está configurado para recibir solicitudes a través del puerto 8080. (opcional)

# Consideraciones finales
* La aplicación no cuenta con pruebas unitarias
* Se incluye 2 colecciones local y publicada en heroku
* Se está utilizando una implementación en una base de datos en memoria H2 con fines prácticos
