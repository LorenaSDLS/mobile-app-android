package mx.sphr.zazil.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://railway.app/project/ecd9de91-c2ab-4f45-8072-3de06ba702ae/service/b8c233aa-f37d-4b89-92d0-63f5f173cdb7/variables/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     val api: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }
}
