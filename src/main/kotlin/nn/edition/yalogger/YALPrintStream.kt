package nn.edition.yalogger

import java.io.PrintStream

object YALPrintStream : PrintStream(System.out) {
    private val logger = LoggerFactory.getLogger("STDOUT")

    private fun process(x: Any?) {
        logger.warn("<yellow>Do not use STDOUT for printing to console.")

        logger.info(x.toString())
    }

    override fun print(x: Any?) = process(x)
    override fun print(x: Boolean) = process(x)
    override fun print(x: Char) = process(x)
    override fun print(x: CharArray) = process(x)
    override fun print(x: Double) = process(x)
    override fun print(x: Float) = process(x)
    override fun print(x: Int) = process(x)
    override fun print(x: Long) = process(x)
    override fun print(x: String?) = process(x)

    override fun println(x: Any?) = process(x)
    override fun println(x: Boolean) = process(x)
    override fun println(x: Char) = process(x)
    override fun println() = process(" ")
    override fun println(x: CharArray) = process(x)
    override fun println(x: Double) = process(x)
    override fun println(x: Float) = process(x)
    override fun println(x: Int) = process(x)
    override fun println(x: Long) = process(x)
    override fun println(x: String?) = process(x)
}