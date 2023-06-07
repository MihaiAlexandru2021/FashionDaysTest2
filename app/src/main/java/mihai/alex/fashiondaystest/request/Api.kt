package mihai.alex.fashiondaystest.request

import mihai.alex.fashiondaystest.data.Response
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("clothing")
    fun getClothing(): Call<Response>
}