package lee.module.navigation.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle

class DeepLinkBuilder {

    private var parameters: Bundle? = null

    private val uri: Uri

    constructor(link: String) {
        uri = Uri.parse(addScheme(link))
    }

    constructor(link: String, parameters: Bundle?) {
        uri = Uri.parse(addScheme(link))
        parameters?.let {
            this.parameters = Bundle(it)
        }
    }

    private fun addScheme(link: String): String {
        return DeepLinkConst.Prefix + link
    }

    fun build(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        parameters?.let {
            intent.putExtras(it)
        }
        return intent
    }
}
