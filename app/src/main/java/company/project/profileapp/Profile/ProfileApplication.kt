package company.project.profileapp.Profile

import android.app.Application
import company.project.profileapp.api.ApiService
import company.project.profileapp.api.RetrofitBuilder
import company.project.profileapp.room.UserDatabase

class ProfileApplication : Application (){

    lateinit var repository: ProfileRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        val service = RetrofitBuilder.getInstance().create(ApiService::class.java)
        val database = UserDatabase.getDatabase(applicationContext)
        repository = ProfileRepository(service, database, applicationContext)
    }
}