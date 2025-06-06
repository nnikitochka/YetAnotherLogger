package nn.edition.yalogger

import org.slf4j.ILoggerFactory
import java.time.format.DateTimeFormatter

object LoggerFactory : ILoggerFactory {

    private val loggers = mutableMapOf<String, Logger>()

    override fun getLogger(name: String): Logger = loggers.getOrPut(name) { Logger(name) }

    val originalOut = System.out
    val originalErr = System.err

    val yalOut = YALPrintStream(System.out, Level.INFO)
    val yalErr = YALPrintStream(System.err, Level.ERROR)

    /**
     * TODO придумать описание
     */
    var terminalWriter: TerminalWriter? = null

    /**
     * Минимальный уровень логирования
     */
    var logLevel: Level = Level.INFO

    /**
     * Формат выводимых в консоль логов.
     *
     * Доступные плейсхолдеры:
     * {date} - текущая дата
     * {time} - текущее время
     * {level} - уровень логирования
     * {name} - имя логгера
     * {trace} - путь к месту вызова логирования (класс.строка)
     * {message} - сообщение лога
     */
    var logFormat = "[{date} {time} {level}] ({trace}) » {message}"

    /**
     * Формат даты, используемый в плейсхолдере {date}.
     * [dateFormat]#set используется для обновления [dateFormatter]
     */
    var dateFormat = "dd.MM.yyyy"
        set(value) {
            field = value
            dateFormatter = DateTimeFormatter.ofPattern(value)
        }
    var dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
        private set

    /**
     * Формат времени, используемый в плейсхолдере {time}.
     * [timeFormat]#set используется для обновления [timeFormatter]
     */
    var timeFormat = "HH:mm:ss"
        set(value) {
            field = value
            timeFormatter = DateTimeFormatter.ofPattern(value)
        }
    var timeFormatter = DateTimeFormatter.ofPattern(timeFormat)
        private set

    /**
     * Нужны ли предупреждения об использовании стандартных
     * методов вывода вместо вывода через логгер?
     */
    var stdWarnings: Boolean = false

    /**
     * Надо ли переопределять стандартные методы
     * вывода на методы вывода логгера?
     */
    var overrideSTD: Boolean = true
        set(value) {
            field = value
            if (value) {
                System.setOut(yalOut)
                System.setErr(yalErr)
            } else {
                System.setOut(originalOut)
                System.setOut(originalErr)
            }
        }

    init {
        if (overrideSTD) {
            System.setOut(yalOut)
            System.setErr(yalErr)
        }
    }
}