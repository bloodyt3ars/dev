# Тестовое задание
Это тестовое задание предназначено для стажеров-разработчиков и заключается в создании веб-приложения на Java с использованием Spring в качестве серверного фреймворка и Angular в качестве интерфейсного фреймворка.

## Задачи приложения
Приложение должно иметь следующие возможности:

* Считывание из текстового файла строк в формате "имя_возраст".
* Предоставление веб-интерфейса с полем ввода для произвольного имени. После отправки имени на сервер в ответ должен прийти возраст, соответствующий этому имени. Если имени нет в перечне, то должно возвращаться произвольное положительное целое число.
* Предоставление возможности ведения просмотра статистики запросов по каждому имени: вывод частотности запросов в разрезе имён и вывод имени с наибольшим возрастом.

Дополнительные требования:
* Дополнительно требуется обеспечить работу приложения с внешним сервисом, который будет возвращать возраст произвольных имен, если их нет в текстовом файле.

## Требования к реализации
* Серверный фреймворк: Spring.
* Интерфейсный фреймворк: Angular.
### Установка и запуск
Для запуска приложения необходимо выполнить следующие действия:

* Склонировать репозиторий приложения на свой локальный компьютер.
* Открыть проект серверной части в выбранной IDE *(папка dev)*.
* Открыть проект веб интерфейса в выбранной IDE *(папка front)*.
* Собрать и запустить проект серверной части, а также веб интерфейса.
* Открыть веб-браузер и перейти на http://localhost:4200/.

### Использованные технологии
* Java 17
* Spring Boot
* Angular
* HTML/CSS
