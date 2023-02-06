package lee.module.kotlin.core.helper

class TimeActionHelper<T>(
    private var duration: Long = LONG_DURATION, private val ignoreSame: Boolean = false,
    private val action: (T) -> Unit = {}
) {

    private var lastTimeDoAction: Long = 0L
    private var cache: T? = null

    fun execute(item: T) {
        if (isValidDoAction(item)) {
            action.invoke(item)
            lastTimeDoAction = System.currentTimeMillis()
        }
        cache = item
    }

    private fun isValidDoAction(item: T): Boolean {
        val isValidTime = System.currentTimeMillis() - lastTimeDoAction >= duration
        val isValidItem = ignoreSame.not() || cache != item

        return isValidItem && isValidTime
    }

    companion object {
        const val X_LONG_DURATION = 16_000L
        const val LONG_DURATION = 8_000L
        const val MEDIUM_DURATION = 4_000L
        const val SHORT_DURATION = 2_000L
    }
}