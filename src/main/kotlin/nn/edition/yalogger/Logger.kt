package nn.edition.yalogger

import org.slf4j.Logger
import org.slf4j.Marker
import java.time.LocalDate
import java.time.LocalTime
import kotlin.Throwable

class Logger(private val name: String) : Logger {
    fun isLevelEnabled(level: Level) = level.intValue() <= LoggerFactory.logLevel.intValue()

    override fun getName(): String = name

    fun log(
        level: Level,
        message: String?,
        args: Array<out Any?> = arrayOf(),
        throwable: Throwable? = null
    ) {
        if (!isLevelEnabled(level)) return

        val text = when {
            message == null -> "null"
            args.isEmpty() -> message
            else -> format(message, args)
        }

        val formattedMessage = level.color+LoggerFactory.logFormat
            .replaceIfExist("{date}") { LocalDate.now().format(LoggerFactory.dateFormatter) }
            .replaceIfExist("{time}") { LocalTime.now().format(LoggerFactory.timeFormatter) }
            .replaceIfExist("{level}") { level.name }
            .replaceIfExist("{trace}") { Throwable().stackTrace.last().let { "${it.className}.${it.lineNumber}" } }
            .replaceIfExist("{name}") { name }
            .replace("{message}", text)

        LoggerFactory.terminalWriter?.write(formattedMessage) ?: LoggerFactory.originalOut.println(formattedMessage)
        throwable?.printStackTrace()
    }

    // --- DEBUG ---
    override fun isDebugEnabled(): Boolean = true
    override fun isDebugEnabled(marker: Marker?): Boolean = true

    override fun debug(msg: String?) = log(Level.DEBUG, msg)
    override fun debug(msg: String?, t: Throwable?) = log(Level.DEBUG, msg, throwable = t)
    override fun debug(format: String?, arg: Any?) = log(Level.DEBUG, format, arrayOf(arg))
    override fun debug(format: String?, arg1: Any?, arg2: Any?) = log(Level.DEBUG, format, arrayOf(arg1, arg2))
    override fun debug(format: String?, vararg arguments: Any?) = log(Level.DEBUG, format, arguments)

    override fun debug(marker: Marker?, msg: String?) = debug(msg)
    override fun debug(marker: Marker?, msg: String?, t: Throwable?) = debug(msg, t)
    override fun debug(marker: Marker?, format: String?, arg: Any?) = debug(format, arg)
    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = debug(format, arg1, arg2)
    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) = debug(format, *arguments)

    // --- INFO ---
    override fun isInfoEnabled(): Boolean = true
    override fun isInfoEnabled(marker: Marker?): Boolean = true

//    fun info(msg: Component?) = log(Level.INFO, msg)
    override fun info(msg: String?) = log(Level.INFO, msg)
    override fun info(msg: String?, t: Throwable?) = log(Level.INFO, msg, throwable = t)
    override fun info(format: String?, arg: Any?) = log(Level.INFO, format, arrayOf(arg))
    override fun info(format: String?, arg1: Any?, arg2: Any?) =
        log(Level.INFO, format, arrayOf(arg1, arg2))
    override fun info(format: String?, vararg arguments: Any?) =
        log(Level.INFO, format, arguments)

    override fun info(marker: Marker?, msg: String?) = info(msg)
    override fun info(marker: Marker?, msg: String?, t: Throwable?) = info(msg, t)
    override fun info(marker: Marker?, format: String?, arg: Any?) = info(format, arg)
    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = info(format, arg1, arg2)
    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) = info(format, *arguments)

    // --- WARN ---
    override fun isWarnEnabled(): Boolean = true
    override fun isWarnEnabled(marker: Marker?): Boolean = true

    override fun warn(msg: String?) = log(Level.WARN, msg)
    override fun warn(msg: String?, t: Throwable?) = log(Level.WARN, msg, throwable = t)
    override fun warn(format: String?, arg: Any?) = log(Level.WARN, format, arrayOf(arg))
    override fun warn(format: String?, arg1: Any?, arg2: Any?) =
        log(Level.WARN, format, arrayOf(arg1, arg2))
    override fun warn(format: String?, vararg arguments: Any?) =
        log(Level.WARN, format, arguments)

    override fun warn(marker: Marker?, msg: String?) = warn(msg)
    override fun warn(marker: Marker?, msg: String?, t: Throwable?) = warn(msg, t)
    override fun warn(marker: Marker?, format: String?, arg: Any?) = warn(format, arg)
    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = warn(format, arg1, arg2)
    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) = warn(format, *arguments)

    // --- ERROR ---
    override fun isErrorEnabled(): Boolean = true
    override fun isErrorEnabled(marker: Marker?): Boolean = true

    override fun error(msg: String?) = log(Level.ERROR, msg)
    override fun error(msg: String?, t: Throwable?) = log(Level.ERROR, msg, throwable = t)
    override fun error(format: String?, arg: Any?) = log(Level.ERROR, format, arrayOf(arg))
    override fun error(format: String?, arg1: Any?, arg2: Any?) =
        log(Level.ERROR, format, arrayOf(arg1, arg2))
    override fun error(format: String?, vararg arguments: Any?) =
        log(Level.ERROR, format, arguments)

    override fun error(marker: Marker?, msg: String?) = error(msg)
    override fun error(marker: Marker?, msg: String?, t: Throwable?) = error(msg, t)
    override fun error(marker: Marker?, format: String?, arg: Any?) = error(format, arg)
    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = error(format, arg1, arg2)
    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) = error(format, *arguments)

    // --- TRACE (Optional) ---
    override fun isTraceEnabled(): Boolean = true
    override fun isTraceEnabled(marker: Marker?): Boolean = true

    override fun trace(msg: String?) = log(Level.TRACE, msg)
    override fun trace(msg: String?, t: Throwable?) = log(Level.TRACE, msg, throwable = t)
    override fun trace(format: String?, arg: Any?) = log(Level.TRACE, format, arrayOf(arg))
    override fun trace(format: String?, arg1: Any?, arg2: Any?) = log(Level.TRACE, format, arrayOf(arg1, arg2))
    override fun trace(format: String?, vararg arguments: Any?) = log(Level.TRACE, format, arguments)

    override fun trace(marker: Marker?, msg: String?) = trace(msg)
    override fun trace(marker: Marker?, msg: String?, t: Throwable?) = trace(msg, t)
    override fun trace(marker: Marker?, format: String?, arg: Any?) = trace(format, arg)
    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) = trace(format, arg1, arg2)
    override fun trace(marker: Marker?, format: String?, vararg arguments: Any?) = trace(format, *arguments)

    private fun format(format: String, args: Array<out Any?>) = StringBuilder().apply {
        var argIndex = 0
        var i = 0
        while (i < format.length) {
            if (i + 1 < format.length && format[i] == '{' && format[i + 1] == '}') {
                append(args.getOrNull(argIndex) ?: "null")
                argIndex++
                i += 2
            } else {
                append(format[i])
                i++
            }
        }
    }.toString()

    private fun String.replaceIfExist(placeholder: String, replacement: () -> String): String {
        return if (this.contains(placeholder)) {
            this.replace(placeholder, replacement.invoke())
        } else {
            this
        }
    }
}