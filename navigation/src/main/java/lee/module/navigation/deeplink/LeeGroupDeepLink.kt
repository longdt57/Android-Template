package lee.module.navigation.deeplink

import com.airbnb.deeplinkdispatch.DeepLinkSpec

@DeepLinkSpec(prefix = [DeepLinkConst.Prefix])
annotation class LeeGroupDeepLink(vararg val value: String)
