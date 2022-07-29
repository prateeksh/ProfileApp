package company.project.profileapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import company.project.profileapp.Utils.NetworkUtils
import company.project.profileapp.api.ApiService
import company.project.profileapp.model.User
import company.project.profileapp.room.UserDatabase

/*This Repository Class is used for generating API call to the server
and saving the data to the Room Database if the internet is available
here if the internet is not available the previous saved data will be populated */

class ProfileRepository (
    private val apiService: ApiService,
    private val userDatabase: UserDatabase,
    private val applicationContext: Context
    ) {

    private val dataset = MutableLiveData<User>()

    val users: LiveData<User>
    get() = dataset

    suspend fun getUsersData() {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {

            val response = apiService.getUsers()
            if (response.body() != null)
                    userDatabase.userDao().insertUser(response.body()!!)
                    dataset.postValue(response.body())
        }else{
            val user = userDatabase.userDao().getUsers()
            val userList = User(0,user.data)
            dataset.postValue(userList)

        }
    }
}

