# Local Operations
Herramienta de terminal multiplatafroma (jvm) para ejecutar comandos.

## Introduccion

La herramienta permite ejecutar comandos de terminal. Los comandos que permite ejecutar se listan dentro del fichero `localops.yml` que ha de estar en el mismo directorio que el de ejecución de la herramienta. Su principal uso, es para los proyectos de desarrollo de software y dejar en la raiz del proyecto el fichero `localops.yml` con el inventario de comandos utilizados para el desarrollo del proyecto en el entorno local del desarrollador.

## Uso

Crear un fichero `localops.yml` con la siguiente estructura:

```
operations: 
 hi:
  desc: "realiza un hola mundo"
  cmd:
   allos: echo "Hola Mundo"
defaults:
 shell:
  win: cmd.exe \c
  nix: sh -c
  mac: sh -c
 path: .
```

Instalar la herramienta mediante uno de los paquetes disponible en el repositorio de github. Ejecutar el siguiente comando en el mismo directorio que el fichero `localops.yml`

```
$ lo
usage: lo [-check | -env | -help | -hi | -version]
 -check            evaluate enviroment checks.
 -env              show enviroment variables in shell.
 -help             show avaliable options.
 -hi               realiza un hola mundo
 -version          show version of project.
```
Para ejecutar la operacion `hi`

```
$ lo -hi
Hola Mundo
```
