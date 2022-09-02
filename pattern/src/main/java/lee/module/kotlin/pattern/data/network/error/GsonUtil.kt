package lee.module.kotlin.pattern.data.network.error

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