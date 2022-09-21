package lee.module.analytics

/**
 * Clients
 * Can be single client: clients = FIREBASE, clients = APPSFLYER
 * or multiple: clients = FIREBASE or APPSFLYER
 * or all: clients = ALL
 */
class AnalyticsManager(
    private val list: List<AnalyticsClient>,
    @AnalyticsClientType private val defaultLogEventClientType: Int = ClientType.FIREBASE
) {

    fun logEvent(event: AnalyticsEvent, @AnalyticsClientType clients: Int = defaultLogEventClientType) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.logEvent(event)
            }
        }
    }

    fun logScreenView(screenView: AnalyticsScreenView, @AnalyticsClientType clients: Int = defaultLogEventClientType) {
        list.forEach { it.logScreenView(screenView) }
    }

    fun setUserProperty(property: AnalyticsUserProperty) {
        list.forEach { it.setUserProperty(property) }
    }

    fun logHandledThrowable(error: AnalyticsError, @AnalyticsClientType clients: Int = defaultLogEventClientType) {
        list.forEach { it.logHandledThrowable(error) }
    }

    fun log(message: String, @AnalyticsClientType clients: Int = defaultLogEventClientType) {
        list.forEach { it.log(message) }
    }

    fun setUserId(userId: String) {
        list.forEach { it.setUserId(userId) }
    }

    private fun invokeIfValid(clients: Int, client: AnalyticsClient, callback: () -> Unit) {
        val isValid = (clients and client.type) != 0
        if (isValid) {
            callback.invoke()
        }
    }

}
