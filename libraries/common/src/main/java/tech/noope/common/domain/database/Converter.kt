package tech.noope.common.domain.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tech.noope.common.domain.data.Publisher

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)

object Converter {

    @TypeConverter
    @JvmStatic
    fun publishersToJson(data: List<Publisher>?): String = Gson().toJson(data)

    @TypeConverter
    @JvmStatic
    fun jsonToPublishers(data: String?): List<Publisher>? = Gson().fromJson(data.toString())
}