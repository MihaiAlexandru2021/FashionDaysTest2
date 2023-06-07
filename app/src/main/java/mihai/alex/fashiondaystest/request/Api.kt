package mihai.alex.fashiondaystest.request

import mihai.alex.fashiondaystest.data.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {
    @Headers("x-mobile-app-locale: ro_RO")
    @GET("clothing")
    fun getClothing(): Call<Response>
}