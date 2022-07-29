package company.project.profileapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val baseUrl = "https://bbf2a516-7989-4779-a5bf-ecb2777960c4.mock.pstmn.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
   /* val apiService : ApiService = getRetrofit().create(ApiService::class.java)
*/
}