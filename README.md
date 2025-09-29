-------Instrucciones Simples---------
1) Utilizar Docker 
2) Crear .env en la carpeta raiz del proyecto con los siguientes antributos: 
APP_PORT=9090
# Configuraciones de la Base de Datos (Servicio 'obs_sec')
MYSQL_ROOT_PASSWORD=XXXXXXX
MYSQL_DATABASE=obstetricia_segurity
MYSQL_USER=xxxxx # Puedes usar esta variable para el usuario de la app si no quieres usar root
MYSQL_PASSWORD_APP=xxxxxx # Contraseña que la aplicación usará para conectarse
3) Verificar que los datos establecidos en .env coordinen con docker-compose.yml
4) Ejecutar docker-compose build
5) "      " docker-compose up
Y listo!, tendras el proyecto para su uso.

Todos los derechos reservados para:
Benjamín Díaz
Cristian Chala
