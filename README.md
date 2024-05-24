# Aplicación Resultados master

Para ver lo documentación completa del proyecto: [Wiki](https://github.com/anavarroo/ResultadosMaster/wiki)

## Como ejecutar la aplicación con Docker

1. Escribe en la terminal el comando `git clone https://github.com/anavarroo/ResultadosMaster.git`
2. Escribe en la terminal el comando `mvn clean package -DskipTest` para limpiar la aplicación.
3. Escibe `docker-compose up` para iniciar la aplicación..

## Como ejecutar la aplicación sin Docker

1. Escribe en la terminal el comando `git clone https://github.com/anavarroo/ResultadosMaster.git`
2. Comprueba que tienes todas las versiones correctas de [Entorno](https://github.com/anavarroo/GestionGenericaP/wiki/Punto-de-vista-Tecnico#--requisitos-del-sistema)
3. Escribe en la terminal el comando `mvn clean verify -DskipTest` para entrar en cada carpeta y construit la aplicación.
4. Despues ejecuta `mvn spring-boot:run` para entrar en cada carpeta y iniciar la aplicación..

## Contacto

Para cualquier duda, no dudes en contactar conmigo:
  - navarrovegaalberto@gmail.com



    
