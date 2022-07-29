package company.project.profileapp.Profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import company.project.profileapp.Utils.NetworkUtils
import company.project.profileapp.api.ApiService
import company.project.profileapp.model.User
import company.project.profileapp.room.UserDatabase


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

          /*  Log.e("Repository Class", "getCoinsData: method called ",)
            val retrofitCall = apiService.getUsers()

            Log.e("Repository Class", retrofitCall.request().url().toString())
            retrofitCall.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
*/
            val response = apiService.getUsers()
            if (response.body() != null)
                    userDatabase.userDao().insertUser(response.body()!!)
                    dataset.postValue(response.body())
                    Log.e("RetrofitCall", response.body().toString())

            /*    }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("RetrofitCall", t.toString())
                }


            })*/
        }else{
            val user = userDatabase.userDao().getUsers()
            val userList = User(0,user.data)
            Log.e("repo", "getUsersData: "+ userList.data[0].firstname)
           dataset.postValue(userList)

        }
    }
}

