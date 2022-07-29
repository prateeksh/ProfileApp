package company.project.profileapp.room

import android.content.Context
import androidx.room.*
import company.project.profileapp.model.User

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        private var INSTANCE: UserDatabase ?= null

        fun getDatabase(context: Context): UserDatabase{

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "userDB"
                ).build()
            }

            return INSTANCE!!
        }
    }
}