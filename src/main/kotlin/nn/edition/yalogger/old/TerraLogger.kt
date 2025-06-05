package nn.edition.yalogger.old

import nn.edition.yalogger.Level
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.logging.FileHandler
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import java.util.regex.Pattern

object TerraLogger : Logger("TerraCoreLogger", null) {
    var debug = false

    private val format = "<gray> {1}</gray> <dark_gray>|</dark_gray> <gray>{2}</gray><dark_gray> »</dark_gray> <gray>{3}</gray><reset>"
    private val formatFile = " {1} | {2} » {3}"
    private val dataFormat = SimpleDateFormat("HH:mm:ss")

    private val logsDir = File("logs/")

    private val cacheSize = 50
    private val cachedMessages = ArrayList<Pair<String, Level>>()

    init {
        level = java.util.logging.Level.ALL
        useParentHandlers = false

        System.setProperty("java.util.logging.SimpleFormatter.format", "%5\$s %n")

        if (!logsDir.exists())
            logsDir.mkdirs()

        val simpleFormatter = SimpleFormatter()

        val fileHandler = FileHandler(
            logsDir.canonicalPath + "/${
                SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()) + "-%g.log"
            }", 5242880, 100, false
        )
        fileHandler.encoding = StandardCharsets.UTF_8.name()
        fileHandler.level = java.util.logging.Level.ALL
        fileHandler.formatter = simpleFormatter

        addHandler(fileHandler)

//        System.setOut(TerraPrintStream)

        deleteOldLogs()
        GlobalUncaughtExceptionLogger.register()
    }

    private fun deleteOldLogs() {
        val allLogFiles = (logsDir.listFiles() ?: emptyArray()).filterNotNull()
        allLogFiles.filter { isOlderThanTenDays(it) }.forEach { it.delete() }
    }

    private fun isOlderThanTenDays(logFile: File): Boolean {
        val tenDaysInMillis = TimeUnit.DAYS.toMillis(10)
        return (System.currentTimeMillis() - logFile.lastModified()) > tenDaysInMillis
    }

    @Synchronized
    fun service(msg: String, vararg replace: Any) {
        printMessage(msg.format(replace), Level.TRACE)
    }

//    @Synchronized
//    fun service(msg: Component, vararg replace: Any) {
//        debug(msg.serialize().format(replace))
//    }

    @Synchronized
    fun debug(msg: String, vararg replace: Any) {
        if(debug) printMessage(msg.format(replace), Level.DEBUG)
    }

//    @Synchronized
//    fun debug(msg: Component, vararg replace: Any) {
//        if(debug) debug(msg.serialize().format(replace))
//    }

    @Synchronized
    fun info(msg: String, vararg replace: Any) {
        printMessage(msg.format(replace), Level.INFO)
    }

//    @Synchronized
//    fun info(msg: Component, vararg replace: Any) {
//        info(msg.serialize().format(replace))
//    }

    @Synchronized
    override fun info(msg: String) {
        printMessage(msg, Level.INFO)
    }

    @Synchronized
    fun warning(msg: String, vararg replace: Any) {
        printMessage(msg.format(replace), Level.WARN)
    }

//    @Synchronized
//    fun warning(msg: Component, vararg replace: Any) {
//        warning(msg.serialize().format(replace))
//    }

    @Synchronized
    override fun warning(msg: String) {
        printMessage(msg, Level.WARN)
    }

    @JvmStatic
    @Synchronized
    fun severe(msg: String, vararg replace: Any) {
        printMessage(msg.format(replace), Level.ERROR)
    }

//    @Synchronized
//    fun severe(msg: Component, vararg replace: Any) {
//        severe(msg.serialize().format(replace))
//    }

    @Synchronized
    override fun severe(msg: String) {
        printMessage(msg, Level.ERROR)
    }

    @Synchronized
    fun printCachedMessages() {
        cachedMessages.forEach {
            printMessage(it.first, it.second, false)
        }
    }

    private fun printMessage(msg: String, level: Level, cache: Boolean = false) {
        if (cache && level != Level.WARN) {
            if (cachedMessages.size >= cacheSize) {
                cachedMessages.removeAt(0)
            }

            cachedMessages.add(Pair(msg, level))
        }

        val coloredMessage = formatString(msg, level)

        if (level == Level.TRACE) {
            super.log(level, msg)
//            TerraCore.terminal.write(msg)
            return
        }

        super.log(
            level,
            formatFile.format(
                dataFormat.format(System.currentTimeMillis()),
                level.name,
//                msg.parseMiniMessage().strip()
            )
        )

//        TerraCore.terminal.write(coloredMessage)
    }

    private fun formatString(text: String, type: Level): String {
        return format.format(dataFormat.format(System.currentTimeMillis()), type.name, text)
    }
    private val formatPattern = Pattern.compile("\\{(\\d+)}")

    fun String.format(vararg args: Any): String {
        val sb = StringBuilder()

        val matcher = formatPattern.matcher(this)

        var pos = 0

        while (matcher.find()) {
            sb.append(this.substring(pos, matcher.start()))

            val index = matcher.group(1).toInt()

            if (index > 0 && index <= args.size) {
                val arg = args[index - 1]

//                if (arg is Component) {
//                    sb.append(arg.serialize())
//                } else {
//                    sb.append(arg.toString())
//                }
            }

            pos = matcher.end()
        }

        sb.append(this.substring(pos))

        return sb.toString()
    }

    @Synchronized
    fun exception(cause: Throwable) {
        val sw = StringWriter()
        val pw = PrintWriter(sw, true)
        cause.printStackTrace(pw)
        val stackTraceMessage = "<red>" + sw.buffer.toString()
        severe(stackTraceMessage)
    }

}

object GlobalUncaughtExceptionLogger : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        TerraLogger.severe("<red>Uncaught exception in a thread ${t.name}:")
        TerraLogger.exception(e)
    }

    fun register() {
        Thread.setDefaultUncaughtExceptionHandler(GlobalUncaughtExceptionLogger)
    }
}