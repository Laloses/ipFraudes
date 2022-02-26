# ipFraudes
API Encargada de obtener información geográfica y economica sobre una ip

El proyecto esta construido con **Spring Cloud**, utilizando el servidor de **Eureka** y la libreria de **Gateway** para controlar el trafico del API principal.

Dentro del API principal se implementa **Resilience4j** para controlar los errores y tiempos de espera de peteciones que se realizan hacia otras API's públcias dentro de este micro servicio.

En el Micro servicio IpFraudeAPI permite bloquear la consulta de informacion dada una API, de la cuál guarda la información geografica sobre esta IP. Tambien permite consultar la informacion geográfica de una IP siempre y cuando no haya sido bloqueada.

Para guardar la información sobre las IP se usa **H2 Database** para almacenar los datos en una tabla de lista negra, la cual contiene la IP bloqueada, la informacion sobre esta y la fecha en la que se añadió a la lista.
