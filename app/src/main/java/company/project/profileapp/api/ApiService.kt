package company.project.profileapp.api

import company.project.profileapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/prod/t2/employee/getAllDetails")
    suspend fun getUsers(): Response<User>

}