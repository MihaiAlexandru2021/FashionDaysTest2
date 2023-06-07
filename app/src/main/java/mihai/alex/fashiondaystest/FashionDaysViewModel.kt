package mihai.alex.fashiondaystest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mihai.alex.fashiondaystest.data.Product
import mihai.alex.fashiondaystest.request.Api
import mihai.alex.fashiondaystest.request.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class FashionDaysViewModel : ViewModel() {

    private val service = Service
    var productList = MutableLiveData<MutableList<Product>>()


    fun getProducts() {
        val retrofitService = Service.getRetrofitInstance().create(Api::class.java)
        retrofitService.getClothing().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                response.body()?.forEach {
                    productList.value?.add(it)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}