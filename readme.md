Переменные окружения
====

Нужно скопировать содержимое `.env-dist` в `.env`

```
POSTGRES_HOST=localhost
POSTGRES_PASSWORD=111
POSTGRES_USER=monitoring_user
POSTGRES_DB=monitoring_db
POSTGRES_PORT=5432
```

Если приложение работает с базой, которая тоже в докере, то `POSTGRES_HOST` должно быть названию контейнера. Если к базе
не подключться - вылетит приложение.

Сборка
===
Собрать контейнер:

```
./gradlew build -x test && docker build . -t leti/monitoring
```

или можно так:

```
./build_docker.sh
```

Затем просто `docker-compose up -d`

Схема базы данных находится в файле schema.sql

Приложение висит на порту 8080, из докера можно его биндить на любой порт, ``8080:8080`` и так далее.

Некоторые запросы
==

```

http://0.0.0.0:8080/api/steps?steps_count=123123&device_id=asdasd
http://0.0.0.0:8080/api/steps/all

http://0.0.0.0:8080/api/damping-angle/all
http://0.0.0.0:8080/api/damping-angle?angle=10&device_id=123123

http://0.0.0.0:8080/api/word-analyze/all
http://0.0.0.0:8080/api/
```
