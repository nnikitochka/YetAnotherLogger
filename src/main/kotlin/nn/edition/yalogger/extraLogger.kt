@file:JvmSynthetic
package nn.edition.yalogger

import kotlin.reflect.KClass

/**
 * Создаёт новый [Logger] или получает существующую инстансу из кеша, если логгер
 * с таким названием уже существует.
 *
 * @param name Имя логгера.
 *
 * @return Инстанса [Logger].
 */
@JvmSynthetic
fun logger(name: String): Logger = LoggerFactory.getLogger(name)

/**
 * Создаёт новый [Logger] или получает существующую инстансу из кеша, если логгер
 * с таким названием уже существует.
 *
 * @param kClass Класс для имени логгера.
 *
 * @return Инстанса [Logger].
 */
@JvmSynthetic
fun logger(kClass: KClass<*>): Logger = logger(kClass.simpleName ?: "Unknown")

/**
 * Создаёт новый [Logger] или получает существующую инстансу из кеша, если логгер
 * с таким названием уже существует.
 *
 * @param clazz Класс для имени логгера.
 *
 * @return Инстанса [Logger].
 */
@JvmSynthetic
fun logger(clazz: Class<*>): Logger = logger(clazz.simpleName ?: "Unknown")