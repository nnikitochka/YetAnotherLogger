    ███╗   ██╗ ███╗   ██╗   ███████╗ ██████╗  ██╗ ████████╗ ██╗  ██████╗  ███╗   ██╗
    ████╗  ██║ ████╗  ██║   ██╔════╝ ██╔══██╗ ██║ ╚══██╔══╝ ██║ ██╔═══██╗ ████╗  ██║
    ██╔██╗ ██║ ██╔██╗ ██║   █████╗   ██║  ██║ ██║    ██║    ██║ ██║   ██║ ██╔██╗ ██║
    ██║╚██╗██║ ██║╚██╗██║   ██╔══╝   ██║  ██║ ██║    ██║    ██║ ██║   ██║ ██║╚██╗██║
    ██║ ╚████║ ██║ ╚████║   ███████╗ ██████╔╝ ██║    ██║    ██║ ╚██████╔╝ ██║ ╚████║
    ╚═╝  ╚═══╝ ╚═╝  ╚═══╝   ╚══════╝ ╚═════╝  ╚═╝    ╚═╝    ╚═╝  ╚═════╝  ╚═╝  ╚═══╝
    Разработчик в вк/тг/дс/гитхаб - @nnikitochka
    Дискорд сообщество https://dsc.gg/nnedition
    Телеграм канал https://t.me/nnedition

---

## 📦 Немного информации

YetAnotherLogger - это легковесный логгер, реализующий интерфейс `slf4j-api`, без дополнительных зависимостей и сложных конфигураций.
Он идеально подходит для небольших проектов, учебных целей или ситуаций, когда требуется простое решение для логирования без использования сторонних библиотек.

---

## 🚀 Начало работы

Выберите подходящею версию из доступного [списка версий](https://github.com/nnikitochka/YetAnotherLogger/releases) и добавьте зависимость в ваш проект:

### Gradle
Добавьте следующую зависимость в ваш `build.gradle.kts`:
```kt
repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("com.github.nnikitochka:YetAnotherLogger:Тэг")
}
```


### Maven
Добавьте следующий фрагмент в ваш `pom.xml`:

```xml
<dependency>
    <groupId>com.github.nnikitochka</groupId>
    <artifactId>YetAnotherLogger</artifactId>
    <version>Тэг</version>
</dependency>
```

### Использование

Пример использования логгера:

```java
import nn.edition.yalogger.Logger;
import nn.edition.yalogger.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger("MainLogger");

    public static void main(String[] args) {
        logger.info("Привет, мир!");
    }
}
```

---

## TODO:
- [ ] Сохранение логов в файл
- [ ] Придумать описание для LoggerFactory#terminalWriter