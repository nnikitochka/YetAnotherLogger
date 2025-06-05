package nn.edition.yalogger

import org.slf4j.ILoggerFactory
import java.time.format.DateTimeFormatter

object LoggerFactory : ILoggerFactory {

    val originalOut = System.out
    private val loggers = mutableMapOf<String, Logger>()

    override fun getLogger(name: String): Logger = loggers.getOrPut(name) { Logger(name) }

    init {
        System.setOut(YALPrintStream)
    }

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
    var logFormat = "[{date} {time} {level}] {trace} » {message}"

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
}