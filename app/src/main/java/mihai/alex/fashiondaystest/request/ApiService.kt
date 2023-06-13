package mihai.alex.fashiondaystest.request

import mihai.alex.fashiondaystest.data.Response
import retrofit2.http.GET

interface ApiService {
    @GET("clothing")
    suspend fun getClothing(): Response
}