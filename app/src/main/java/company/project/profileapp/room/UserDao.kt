package company.project.profileapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import company.project.profileapp.model.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query(value = "SELECT * FROM user")
    fun getUsers(): User

}