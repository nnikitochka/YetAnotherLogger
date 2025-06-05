package nn.edition.yalogger

import org.slf4j.ILoggerFactory

object LoggerFactory : ILoggerFactory {
    private val loggers = mutableMapOf<String, Logger>()

    override fun getLogger(name: String): Logger =
        loggers.getOrPut(name) { Logger(name) }
}