docker run --name carros-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:8

docker exec -it 8ea mysql -uroot -pmy-secret-pw

CREATE DATABASE carros