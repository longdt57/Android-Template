package lee.module.navigation.deeplink

import lee.module.navigation.deeplink.DeepLinkScreenName.Root

object DeepLinkScreenName {
    const val Root = "Root"
}

object DeeplinkIntents {
    fun getRootIntent() = DeepLinkBuilder(Root).build()
}
