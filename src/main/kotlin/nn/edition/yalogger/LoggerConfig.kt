package nn.edition.yalogger

object LoggerConfig {
    var terminalWriter: TerminalWriter? = null

    /**
     * Минимальный уровень логирования
     */
    var globalLevel: Level = Level.INFO

    var logFormat = "[{date} {time} {level}] {name} » {message}"
    var dateFormat = "dd.MM.yyyy"
    var timeFormat = "HH:mm:ss"

    fun isLevelEnabled(level: Level) = level.intValue() <= globalLevel.intValue()
}