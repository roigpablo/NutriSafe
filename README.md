# NutriSafe

Aplicación Android nativa (Java) que indica con un semáforo si un producto alimentario encaja con tu perfil: celiaquía, alergias o diabetes tipo 2.

Proyecto final del CFGS Desarrollo de Aplicaciones Multiplataforma (DAM).

> **Estado: entrega parcial (20 de noviembre de 2026).** El flujo principal funciona; lo pendiente está en el apartado [Pendiente](#pendiente).

## Qué hace

- **Perfil:** sexo, edad, peso, altura, actividad, condición y alergias. Calcula las calorías de mantenimiento con la fórmula de Mifflin-St Jeor.
- **Consulta por código de barras:** descarga el producto de Open Food Facts y lo guarda en una caché local, así que lo ya consultado funciona sin conexión.
- **Búsqueda por nombre**, con espera de 500 ms entre pulsaciones y límite de 10 búsquedas por minuto.
- **Semáforo de aptitud:** ROJO, ÁMBAR, VERDE o GRIS (sin datos). Gana siempre el peor nivel.
- **Aviso de uso** al empezar y pantalla «Acerca de» con la atribución a Open Food Facts.

## Reglas del semáforo

- **Alérgenos:** ROJO si el alérgeno del usuario aparece en los alérgenos del producto; ÁMBAR si solo aparece en las trazas; VERDE si hay datos y no coincide; GRIS si no hay datos. La celiaquía se trata como alergia al gluten.
- **Diabetes:** azúcares por 100 g. Hasta 5 g es VERDE, hasta 22,5 g ÁMBAR y por encima ROJO. Por ahora los umbrales están fijos en el código.

## Requisitos

- Android Studio (versión reciente) y JDK 17 o superior.
- Emulador o móvil con Android 8.0 (API 26) o superior.
- Conexión a internet para consultar productos nuevos.

## Cómo compilar y ejecutar

1. Clona el repositorio: `git clone https://github.com/roigpablo/NutriSafe.git`
2. Ábrelo con Android Studio y espera a que termine la sincronización de Gradle.
3. Elige un emulador o dispositivo y pulsa **Run**.
4. Para las pruebas unitarias: clic derecho en el paquete `com.nutrisafe.domain` de la carpeta de tests y **Run Tests**. Son 19 pruebas JUnit.

## Estructura

```
com.nutrisafe
├── ui          Activities y adaptadores
├── viewmodel   ViewModels (LiveData)
├── domain      Lógica: calorías, evaluadores y semáforo
└── data
    ├── local   Room (Usuario, Alimento)
    └── remote  Retrofit (Open Food Facts)
```

Tecnologías: Java, Room, Retrofit con Gson y OkHttp, Glide, Material Components, JUnit.

## Pendiente

Se completará antes de la entrega final:

- Diario de comidas, plan semanal y lista de la compra (R04 a R06).
- Escáner de códigos de barras con la cámara.
- Pantalla de umbrales editables.
- Ajuste de las calorías según el objetivo.

## Aviso legal

NutriSafe es una herramienta orientativa. No sustituye el consejo de un profesional sanitario ni la lectura de la etiqueta del producto. Los datos pueden ser incompletos o contener errores.

## Atribución

Datos de productos de [Open Food Facts](https://world.openfoodfacts.org), publicados bajo la licencia [ODbL](https://opendatacommons.org/licenses/odbl/1-0/). Las imágenes se rigen por CC BY-SA.

## Autor

Pablo Roig Pérez. Tutor: Jorge Chaves Cristobal.