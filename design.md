# Diseño de proyecto de Local-Operations

## Requisitos

- [X] Controlador de terminal
- [X] Leer un yml y hacer de proxy de la terminal
- [X] soporte para varios targets : win|max|nix and all-os
- [X] wildcard para no reperit comandos : idem
- [X] El yml contendrá comandos asociados a un alias
- [ ] revisar empaquetado :
   - https://docs.oracle.com/javase/8/docs/technotes/guides/deploy/self-contained-packaging.html
   - https://github.com/fvarrui/JavaPackager 
- [X] #1 limpiar código para liberar versión
- [ ] ver como se libera una versión en github
- [ ] ver como se publican los paquetes en github (github actions)
- [ ] ver como usar colores en los mensajes
- [ ] uso de spi para cargar comandos comunes: help,version,check
- [ ] pensar como realizar updates
- [ ] ver de incluir lookp-up variables
	- variables de entorno shell $env{ .... }
	- variables de la jvm $jvm{ user.home }
	- ver org.apache.commons.text.StringSubstitutor
		- ${env:SNYK_TOKEN}
		- ${java:user.home}
		- ${date:yyy-MM-dd}
		- ${file:UTF-8:/home/user/secret.txt}
		- ${properties:/home/user/secrets.properties::snyk.user}
- [ ] Cuando se ejecute un comando imprimir msg con el comando ejecutado
- [ ] Incluir argumentos a las opciones y hacer lookup con ${arg:option.name}
- [ ] Ver de hacer más genericos los targets de despliegue (Incluir las opciones apache: Aix,etc..)
- [ ] ver de incluir un banner ascii-art en la ayuda
- [ ] Cambiar el comando de ayuda, para que muestre la sintaxis y ejemplos del localops.yml
- [ ] Incluir la versión en la ayuda, eliminar default-command version
- [ ] Tendra una sección de comprobación de entorno que valide si los comandos se pueden ejecutar
- [ ] Controlador de terminal interactiva
- [ ] incluir default option manage <init|add|delete|..>
	- init : crea un localops.yml default
	- add : añade un comando al fichero
	- delete : elimina un comando del fichero


## Incidencias

- [ ] ver como dar soporte para que el nombre de las operaciones se puedan usar "-"
- [X] cuando una opcion no es valida imprimir la ayuda


## Formato yml
``` yml
defaults:
 shell:
  win: cmd.exe \c
  nix: sh -c
  mac: sh -c
 path: $jvm{user.home} | "."
 
enviroment:
 checks:
  jvm-var:
    msg: "Existe JAVA_HOME variable"
    cmd:
      win:
      nix:
      mac:
  jvm-version:
    msg: "La version de la jvm es igual a 1.8"
    cmd:
      win:
      nix:
      mac:
  mvn-var:
operations:
 compile:
   desc: "compila el proyecto y deja el resultado en target"
   cmd:
    nix: mvn clean install
    win: idem
    mac: idem
   checker:
    cmd:
     nix: mvn --version 
     win: idem
     mac: idem           
 compile-without-tests:
   desc: "compila el proyecto y deja el resultado en target"
   cmd:
    nix: mvn clean install -DskipTests=true
    win: idem
    mac: idem
 update-child-version:
   desc: "actualiza el numero de versión de los proyectos hijos"
   cmd:
    nix: mvn --versions:update-child-modules
    win:
``` 
