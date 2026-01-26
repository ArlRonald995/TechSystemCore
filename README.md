# TechSystemCore — Tienda de Artículos Tecnológicos

TechSystemCore es una aplicación de escritorio/web en Java que implementa conceptos de Programación Orientada a Objetos (POO) y funciona como un sistema gestor para una tienda de artículos tecnológicos.

La aplicación se conecta a una base de datos PostgreSQL y está pensada para ser ejecutada en un entorno preparado con Docker.

## Descripción

Este proyecto permite:

- Gestionar productos tecnológicos.
- Registrar y consultar clientes y ventas.
- Interactuar con una base de datos PostgreSQL.
  
Se ha estructurado siguiendo buenas prácticas de POO en Java.

*Nota: La interfaz principal puede tardar en cargar al inicio, sobre todo la primera vez que se levanten los servicios y contenedores de Docker.*

## Tecnologías utilizadas
1. Java — Lógica del sistema.

2. PostgreSQL — Base de datos relacional para almacenar datos de productos, clientes y transacciones.

3. Docker & Docker Compose — Para levantar contenedores (base de datos y servicios).

4. Maven — Para la gestión de dependencias y build (pom.xml).

## Estructura del proyecto

```
TechSystemCore/
├── .idea/
├── src/
│   └── main/
│       └── java/
│           └── Interfaz/
│           └── (otros paquetes del proyecto)
├── pom.xml
└── README.md
```

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalados:

- Docker y Docker Compose
- Java JDK 17+
- Maven (para compilar la app si no usas un IDE integrado)
- PostgreSQL (si no usas Docker) 

## Documentación Docker

Docker es una plataforma que permite crear, ejecutar y distribuir aplicaciones mediante contenedores, evitando la necesidad de instalar manualmente motores de base de datos u otros servicios en la computadora local. En este proyecto se utilizó Docker para encapsular la base de datos PostgreSQL y garantizar que pueda ejecutarse en cualquier equipo.

La principal ventaja es que cualquier equipo puede ejecutar la base de datos sin importar el sistema operativo, siempre y cuando tenga Docker instalado.

Esto garantiza:

- Compatibilidad en Windows, Linux y macOS.

- Facilidad para desplegar y compartir el proyecto.

- Entornos idénticos en cada máquina, evitando errores por configuraciones distintas.
  
  - misma versión de PostgreSQL
  - mismos usuarios y contraseñas
  - mismas tablas y datos iniciales

Para levantar la base de datos, solo necesitas ejecutar:
```
  docker-compose up -d
```
*Con este comando, cualquier persona puede levantar la base de datos inmediatamente.*

Esto crea:

- El contenedor de PostgreSQL
- El usuario y la base de datos
- La estructura inicial mediante scripts
- El entorno totalmente listo para usar

### ¿Qué incluye el contenedor de este proyecto?

El contenedor Docker usado para esta aplicación contiene:
- PostgreSQL configurado con:

  - Usuario: AdminTienda
  - Base de datos: InventarioTienda
  - Script de inicialización: *inicializar_bd.sql*
  - Puertos expuestos para conexión desde IntelliJ
    
- Persistencia de datos mediante volúmenes



## Configuración del entorno

1. Clonar el repositorio

```
git clone https://github.com/ArlRonald995/TechSystemCore.git
cd TechSystemCore
```

2. Crear el archivo .env

```
POSTGRES_USER= "Aquí se debe poner el nombre de usuario de la base de datos"
POSTGRES_PASSWORD= "Aquí se debe poner la contraseña del usuario de la base de datos"
POSTGRES_DB= "Aquí se debe poner el nombre de la base de datos creada dentro del contenedor "
DB_PORT= 5432 "Generalmente este puerto se suele usar para conectarse a la base de datos " 
```

## Levantar PostgreSQL con Docker
Crea un docker-compose.yml con el siguiente contenido si aún no existe:

```
version: "3.8"
services:
  db:
    image: postgres:latest
    restart: always
    environment:
      - POSTGRES_USER=${POSTGRES_USER}
      - POSTGRES_PASSWORD=${POSTGRES_PASSWORD}
      - POSTGRES_DB=${POSTGRES_DB}
    ports:
      - "${DB_PORT}:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

Luego ejecuta:

```
docker-compose up -d
```
Esto levantará un contenedor de PostgreSQL listo para usarse.



## Pasos para levantar la base de datos de la tienda

1. Instalar Docker desde la página oficial según el sistema operativo:

https://www.docker.com

2. Dentro de Docker Desktop, actualizar WSL si la aplicación lo solicita (solo en Windows).

3. Iniciar Docker Desktop.
   
   - En aplicaciones en segundo plano debe aparecer el ícono de Docker.

   - Al hacer clic derecho, en la parte superior debe mostrar: "Docker Desktop is running" junto con un punto verde.

4. Descomprimir el archivo BaseDeDatos_FINAL.zip.

5. Identificar la ruta de la carpeta llamada BaseDeDatos.

6. Verificar que dentro de la carpeta se encuentren los archivos:

```
docker-compose.yml
```

```
inicializar_bd.sql
```
7. Abrir el Símbolo del Sistema (CMD) y navegar hasta la carpeta BaseDeDatos.
   
Ejemplo:

```
cd C:\Ruta\A\BaseDeDatos
```

8. Ejecutar el siguiente comando para levantar la base de datos:

```
docker-compose up -d
```

9. Si el proceso fue exitoso, el contenedor de PostgreSQL estará corriendo.

10. Para comprobar si existen las tablas, ejecutar:
    
```
docker exec -it db_tienda_equipo bash
```

11. Luego dentro del contenedor ingresar a PostgreSQL:
    
```
psql -U AdminTienda --password InventarioTienda
```
12. Cuando pida la contraseña, escribir:
    
```
password
```
(No se verá en pantalla, pero se está escribiendo.)

13. Dentro de PostgreSQL ejecutar:
```
\dt
```

Si aparece el mensaje “no existe ninguna relación”, significa que las tablas no se cargaron.

14. Salir de la base de datos usando Ctrl + D varias veces hasta volver a la ruta del archivo BaseDeDatos.

15. Ejecutar nuevamente la carga inicial:
```
docker exec -i db_tienda_equipo psql -U AdminTienda -d InventarioTienda < inicializar_bd.sql
```

16.Repetir los pasos 10 al 13 para verificar si ahora sí existen las tablas.

17. Finalmente, verificar la conexión desde IntelliJ ejecutando la clase Conexion dentro del proyecto.
Si no hay errores, la base de datos fue levantada correctamente.

## Compilar y ejecutar la aplicación

#### Usando IDE 

1. Importa el proyecto en IntelliJ.

2. Asegúrate de que la configuración de la base de datos en el código apunte a tu PostgreSQL.

3. Ejecuta la clase principal desde tu IDE.


### Importante sobre el rendimiento
La interfaz principal puede tardar unos segundos o más en cargar, especialmente la primera vez que se conecta y sincroniza con la base de datos dentro de Docker. Esto es normal en aplicaciones que inicializan muchas dependencias de backend.


# Manual de Usuario — Cómo usar la aplicación

Esta sección explica paso a paso cómo interactuar con la aplicación de la tienda, según el tipo de usuario. Está diseñada para que cualquier persona pueda entender rápidamente cómo navegar las interfaces.

## 1. Inicio — Pantalla de Login

Al abrir la aplicación *(al ejecutar Interfaz.InicioDeSesion)*, aparece la pantalla de inicio de sesión.

  Objetivo: Que el usuario ingrese sus credenciales para acceder al sistema.

Elementos visibles:

 - Campos para Usuario y Contraseña
 - Botón Acceder
 - Botón Registrarse

## 2. Usuario Comprador

Si el usuario ingresa correctamente con una cuenta de comprador:
 
### Ventana de Catálogo

Después de iniciar sesión, se muestra la VentanaCatalogo, donde el comprador puede:

- Ver la lista de productos disponibles
- Navegar entre categorías
- Examinar artículos tecnológicos con imagen, precio y descripción

Acciones disponibles:

- Seleccionar producto → muestra detalle parcial
- Agregar al carrito → añade el producto a la lista de compra

### Carrito de Compras

Desde el catálogo, el usuario puede abrir su Carrito de Compras.

En el carrito puede ver:

- Productos añadidos
- Cantidad, precio individual y total
- Botón para eliminar producto
- Botón para realizar el pago

### Proceso de Pago

Al presionar Finalizar compra en el carrito, se abre el panel de ProcesoDePago, donde el comprador:

- Ingresa datos de pago
- Confirma la compra
- Puede ver un resumen final de la transacción

## 3. Usuario Administrador

Si el usuario inicia sesión con credenciales de administrador:

### Credenciales Admin:

Usuario: admin@tienda.com

Contraseña: admin123

### Ventana Admin

Se abre la VentanaAdmin con opciones para gestionar el sistema.

En esta interfaz el administrador puede:

- Agregar productos nuevos

   - Se abre la pantalla de AgregarProducto
   - Rellenar nombre, descripción, precio, stock y categoría
   - Guardar producto en la base de datos

- Ver detalle de un producto

    - Al seleccionar un producto de la lista
    - Se abre la pantalla de DetalleProducto mostrando información completa

- Ver Pedidos

   - Revisar transacciones finalizadas
   - Filtrar por fecha o cliente

 ## 4. Detalle de Producto

Desde VentanaCatalogo o VentanaAdmin, al seleccionar un producto:

### DetalleProducto

Se muestra una ventana con:

- Imagen grande del producto
- Nombre
- Descripción completa
- Precio
- Stock disponible

Dependiendo del tipo de usuario, puede aparecer directamente la opción de agregar al carrito (para compradores) o solo vista informativa (para admins).

## 5. Registro de nuevo usuario

Desde la pantalla de InicioDeSesion, si se presiona Registrarse, aparece la pantalla de Registro.

En esta pantalla el usuario puede:

- Ingresar nombre completo
- Ingresar email
- Establecer contraseña
- Ingresar dirección
- Confirmar registro (Registrarse)
- O (Cancelar)

Una vez creado, regresará a login para iniciar sesión.

## 6. Mensajes y Errores del Sistema

Durante el uso de la aplicación, pueden aparecer mensajes:

- **Error de credenciales**: Si el usuario o contraseña son incorrectos

- **Campos incompletos**: Si no se completaron datos obligatorios

- **Acción completada**: Al añadir un producto o completar un pedido
  

## Recomendaciones

- Asegúrate de tener datos válidos para iniciar sesión.
- Si no tienes cuenta, regístrate antes.
- Se recomienda ejecutar primero la base de datos antes de iniciar la aplicación.
  
- Si deseas ver cómo impacta el funcionamiento del programa directamente en la base de datos, te recomiendo instalar pgAdmin, que es un cliente gráfico oficial      para PostgreSQL.

    Con pgAdmin podrás:

     - Ver las tablas creadas por el programa
     - Explorar registros insertados por los usuarios
     - Ejecutar consultas SQL
     - Ver relaciones y estructura de la BD
  
    Puedes descargarlo aquí:
🔗  https://www.pgadmin.org/download/



- Si la configuración del contenedor usa el puerto 5432, que es el puerto por defecto de PostgreSQL:

```
ports:
  - "5432:5432"
```

En algunos equipos este puerto puede estar ocupado por:

   - Una instalación local de PostgreSQL
   - Otro contenedor
   - Alguna aplicación que lo utiliza internamente

Si al ejecutar docker-compose up aparece un error indicando que el puerto ya está ocupado, simplemente cambia el puerto de tu máquina local (izquierda) por otro disponible. Por ejemplo:

```
ports:
  - "5433:5432"
```

Esto hará que:

  - PostgreSQL dentro del contenedor siga usando su puerto normal (5432).
  - Pero se expondrá hacia tu computadora en el puerto 5433.

El puerto en pgAdmin, al momento de registrar el servidor → en Port coloca 5433.
```
pg admin Servers" > "Register" > "Server
```
   
Luego recuerda que en la clase Conexion se dede cambiar la configuración JDBC:

```
private static final String URL = "jdbc:postgresql://localhost:5433/InventarioTienda";
```




