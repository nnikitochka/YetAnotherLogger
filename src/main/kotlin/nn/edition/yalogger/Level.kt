package nn.edition.yalogger

class Level(
    name: String,
    value: Int,
    color: String,
): java.util.logging.Level(name, value) {
    companion object {
        @JvmStatic
        val ERROR = Level("ERROR", 1, RED)

        @JvmStatic
        val WARN = Level("WARN", 2, YELLOW)

        @JvmStatic
        val INFO = Level("INFO", 3, RESET)

        @JvmStatic
        val DEBUG = Level("DEBUG", 4, RESET)

        @JvmStatic
        val TRACE = Level("TRACE", 5, RESET)

        private const val RESET = "\u001B[0m"
        private const val RED = "\u001B[31m"
        private const val GREEN = "\u001B[32m"
        private const val YELLOW = "\u001B[33m"
        private const val BLUE = "\u001B[34m"
        private const val MAGENTA = "\u001B[35m"
        private const val CYAN = "\u001B[36m"
    }
}