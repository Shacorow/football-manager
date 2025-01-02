@echo off

set db_url=jdbc:postgresql://localhost:5432/football_manager_data
set db_user=admin
set db_password=

java -jar football-manager-1.0-SNAPSHOT.jar
