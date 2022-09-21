package lee.module.analytics

open class AnalyticsEvent(
    val name: String,
    val params: Map<String, Any?> = mapOf(),
)