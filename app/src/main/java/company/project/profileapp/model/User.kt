package company.project.profileapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// This is the data class which has been used for the database and model class for data fetched from API calls

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerializedName("data" ) var data : List<Data> = arrayListOf()
){

    data class Data (

        @SerializedName("firstname" ) var firstname : String?              = null,
        @SerializedName("lastname"  ) var lastname  : String?              = null,
        @SerializedName("age"       ) var age       : String?              = null,
        @SerializedName("gender"    ) var gender    : String?              = null,
        @SerializedName("picture"   ) var picture   : String?              = null,
        @SerializedName("job"       ) var job       : List<Job>       = arrayListOf(),
        @SerializedName("education" ) var education : List<Education> = arrayListOf()

    ){
        data class Job (

            @SerializedName("role"         ) var role         : String? = null,
            @SerializedName("exp"          ) var exp          : Int?    = null,
            @SerializedName("organization" ) var organization : String? = null

        )
        data class Education (

            @SerializedName("degree"      ) var degree      : String? = null,
            @SerializedName("institution" ) var institution : String? = null

        )
    }
}
