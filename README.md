# sharilanekgm — UI-автотесты регистрации Sharelane

Maven-проект UI-автотестов на Java для учебного сайта
[Sharelane (sharelane.com)](https://www.sharelane.com/cgi-bin/register.py):
Selenium WebDriver 4, TestNG, паттерн Page Object, явные ожидания,
повторные попытки для «медленных» ответов стенда и отчёты Maven Surefire.

Репозиторий: <https://github.com/georgiikluchnikov-a11y/sharilanekgm>

## Стек

| Технология | Версия |
|---|---|
| Java | 17 |
| Maven | 3.9+ |
| Selenium WebDriver | 4.49.0 |
| TestNG | 7.12.0 |
| WebDriverManager | 5.9.2 |

## Структура проекта

```text
sharilanekgm/
├── pom.xml
├── src/main/java/ru/klyuchnikov/sharilane/Main.java   # точка входа (заглушка)
├── src/test/java/
│   ├── pages/BasePage.java          # явные ожидания, клик с повтором, headless
│   ├── pages/ZipCodePage.java       # шаг 1: ввод Zip Code
│   ├── pages/SignUpPage.java        # шаг 2: форма регистрации
│   ├── tests/TestBase.java          # драйвер Chrome, таймауты, driver.quit()
│   └── tests/RegistrationTest.java  # 4 теста
└── target/surefire-reports/         # отчёты после прогона (не в git)
```

## Требования

- JDK 17+ (`java -version`)
- Maven 3.9+ (`mvn -v`)
- Google Chrome (WebDriverManager сам скачает chromedriver)

## Запуск

```bash
# все тесты (видимый браузер)
mvn clean test

# без графического интерфейса, как в CI
mvn clean test -Dheadless=true

# один класс или один метод
mvn test -Dtest=RegistrationTest
mvn test -Dtest=RegistrationTest#testSuccessfulRegistration
```

Отчёты: `target/surefire-reports/`.

## Реализованные сценарии

| Тест | Что проверяет |
|---|---|
| `testValidZipCode` | корректный Zip Code из 5 цифр открывает форму регистрации |
| `testShortZipCode` | 4 цифры → сообщение `Oops, error on page. ZIP code should have 5 digits` |
| `testLettersInZipCode` | буквы вместо цифр → то же сообщение об ошибке |
| `testSuccessfulRegistration` | заполнение формы и создание аккаунта → `Account is created!` |

Сценарий повторяет двухшаговую регистрацию Sharelane: на первом шаге вводится
Zip Code (`https://www.sharelane.com/cgi-bin/register.py`), на втором — имя, фамилия,
e-mail и пароль. E-mail генерируется уникальным, поэтому тест можно запускать повторно.

## Особенности реализации

- Явные ожидания (`WebDriverWait`) вместо `Thread.sleep` — в `BasePage`.
- `clickWithRetry` и повторное открытие страницы: если стенд отвечает медленно,
  выполняется обычный клик, затем клик через JavaScript (до 3 попыток).
- `-Dheadless=true` или переменная окружения `HEADLESS=true` — прогон без графического интерфейса.
- Драйверы WebDriverManager кэшируются в `target/wdm-cache`.

## Git-процесс

```bash
git checkout -b feature/<Группа>_<Фамилия>_sharilane
git add .
git commit -m "test: add sharelane registration tests"
git push -u origin feature/<Группа>_<Фамилия>_sharilane
```

Далее открыть Pull Request в `main` и добавить ментора в Reviewers.

> Демо-стенд sharelane.com периодически отвечает медленно или недоступен, поэтому
> единичный сетевой таймаут не приводит к падению всего набора.
