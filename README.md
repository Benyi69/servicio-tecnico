# ServiTech Pro 📱

## 🛠️ Descripción de la Aplicación

**ServiTech Pro** es una solución integral para la gestión de talleres de servicio técnico automotriz. La aplicación móvil, desarrollada en Kotlin nativo para Android, conecta a administradores, técnicos y clientes, optimizando el flujo de trabajo desde la creación de una orden de servicio hasta su seguimiento y finalización.

El sistema permite manejar tanto a clientes registrados como a clientes invitados, ofreciendo una experiencia fluida para todos los usuarios. Los administradores y técnicos pueden gestionar las órdenes a través de un panel Kanban intuitivo, mientras que los clientes pueden seguir el progreso de sus reparaciones en tiempo real.

## 👥 Integrantes

*   **Benjamín Caro**

## ✨ Funcionalidades Principales

### Para Administradores y Técnicos:
- **Gestión de Clientes:** Permite registrar nuevos clientes (tanto usuarios completos como "invitados") y seleccionarlos para crear nuevas órdenes.
- **Creación de Órdenes de Servicio:** Formulario completo para registrar los detalles del vehículo, la descripción del problema y asociar la orden a un cliente.
- **Panel Kanban de Órdenes:** Visualización de todas las órdenes de servicio organizadas por su estado actual (Nueva, En Diagnóstico, Esperando Aprobación, Aprobada, En Reparación, Completada, Rechazada).
- **Detalle de la Orden:** Permite ver toda la información de una orden, añadir "arreglos" o repuestos con su costo, y cambiar el estado de la orden.
- **Gestión de Presupuestos:** Al pasar una orden a "Esperando Aprobación", el cliente es notificado para que apruebe o rechace el presupuesto.
- **ID de Seguimiento:** Al crear una orden, se genera un ID único que el administrador puede compartir con el cliente para su seguimiento.

### Para Clientes (Registrados y No Registrados):
- **Autenticación Completa:** Los clientes pueden registrarse, iniciar sesión y recuperar su contraseña.
- **Dashboard de Cliente:** Los clientes registrados pueden ver un listado de sus órdenes activas y su historial de órdenes completadas.
- **Seguimiento de Órdenes:**
- **Clientes Registrados:** Ven el progreso de sus órdenes directamente en su dashboard.
- **Clientes Invitados:** Pueden usar la función "Rastrear mi Orden" en la pantalla de inicio para ver el estado de su servicio introduciendo el ID proporcionado por el taller.
- **Aprobación de Presupuestos:** Cuando una orden está "Esperando Aprobación", el cliente puede ver los detalles y aprobar o rechazar el presupuesto directamente desde la app.
- **Catálogo de Servicios:** Visualización de los servicios ofrecidos por el taller con descripciones y precios aproximados.

## 🌐 Endpoints y Servicios Externos

La aplicación se integra con los siguientes servicios para su funcionamiento:

- **Firebase Authentication:** Para la gestión de usuarios (registro, inicio de sesión, recuperación de contraseña).
- **Cloud Firestore:** Base de datos NoSQL en tiempo real utilizada como única fuente de verdad para:
    - `/users`: Colección para almacenar los datos de los usuarios registrados.
    - `/guest_clients`: Colección para almacenar los datos de los clientes invitados.
    - `/tickets`: Colección principal donde se guardan todas las órdenes de servicio.
    - `/service_offerings`: Colección para el catálogo de servicios del taller.
- **API Externa (Placeholder):** Se utiliza `https://jsonplaceholder.typicode.com/todos` como un ejemplo de integración con una API externa, aunque no es parte del flujo principal de la aplicación.

## 🚀 Instrucciones para Ejecutar el Proyecto

1.  **Clonar el Repositorio:**
    ```sh
    git clone https://github.com/Benyi69/servicio-tecnico.git
    ```
2.  **Configurar Firebase:**
    - Ve a la [Consola de Firebase](https://console.firebase.google.com/).
    - Crea un nuevo proyecto de Firebase.
    - Dentro del proyecto, crea una nueva aplicación de Android con el nombre de paquete `com.serviciotecnico`.
    - Sigue los pasos para descargar el archivo `google-services.json`.
    - **Copia el archivo `google-services.json`** en la carpeta `app/` de tu proyecto.
    - En la consola de Firebase, habilita los siguientes servicios:
        - **Authentication:** Activa el proveedor "Email/Password".
        - **Firestore Database:** Crea una base de datos en modo de prueba para permitir lecturas y escrituras.
3.  **Abrir en IntelliJ IDEA o Android Studio:**
    - Abre el IDE y selecciona "Open an Existing Project".
    - Navega hasta la carpeta clonada y ábrela.
    - El IDE descargará automáticamente las dependencias necesarias a través de Gradle.
4.  **Ejecutar la Aplicación:**
    - Conecta un dispositivo físico o inicia un emulador de Android.
    - Presiona el botón "Run" (▶) para compilar e instalar la aplicación en el dispositivo/emulador.

## 📦 APK Firmado y Keystore

- **APK Firmado:** El archivo `app-release.apk` se encuentra en la ruta `app/release/`. Este es el archivo que se puede instalar en un dispositivo Android.
- **Ubicación del Keystore (`.jks`):** El archivo `keystore.jks` **no se encuentra en este repositorio por razones de seguridad**. Ha sido añadido al archivo `.gitignore` para prevenir su subida accidental. La posesión de este archivo y sus contraseñas permite publicar futuras actualizaciones de la aplicación.

## 🤝 Evidencia de Trabajo Colaborativo

Todo el desarrollo ha sido realizado por **Benjamín Caro**. El historial de commits en este repositorio de GitHub sirve como evidencia del trabajo progresivo, las refactorizaciones y la implementación de las funcionalidades descritas.
