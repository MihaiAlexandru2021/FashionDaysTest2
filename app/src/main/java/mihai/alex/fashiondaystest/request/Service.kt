package mihai.alex.fashiondaystest.request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    val BASE_URL = "https://m.fashiondays.com/mobile/8/g/women/"

    fun getRetrofitInstance(): Retrofit {
       return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
}