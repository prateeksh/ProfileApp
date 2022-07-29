package company.project.profileapp.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import company.project.profileapp.model.User
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun listToJson(value: List<User.Data>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<User.Data>::class.java).toList()



/*    @TypeConverter
    fun listToJsonStringEdu(value: ArrayList<User.Data.Education>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToListEdu(value: String) = Gson().fromJson(value, Array<User.Data.Education>::class.java).toList()

    @TypeConverter
    fun listToJsonStringJob(value: ArrayList<User.Data.Job>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToListJob(value: String) = Gson().fromJson(value, Array<User.Data.Job>::class.java).toList()*/

}