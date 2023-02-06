package lee.module.kotlin.core.util

import com.google.gson.Gson

object GsonUtil {

    inline fun<reified T> fromJson(json: String?): T? {
        return try {
            Gson().fromJson(json, T::class.java)
        } catch (ex: Exception) {
            null
        }
    }
}