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

class FashionDaysViewModel : ViewModel() {

    private val service = Service
    var productList = MutableLiveData<MutableList<Product>>()


    fun getProducts() {
        val retrofitService = service.getRetrofitInstance().create(Api::class.java)
        retrofitService.getClothing().enqueue(object : Callback<mihai.alex.fashiondaystest.data.Response> {
            override fun onResponse(call: Call<mihai.alex.fashiondaystest.data.Response>, response: Response<mihai.alex.fashiondaystest.data.Response>) {
                if (response.isSuccessful) {
                    response.body()?.listProduct?.forEach {
                        productList.value?.add(it)
                    }
                }
            }

            override fun onFailure(call: Call<mihai.alex.fashiondaystest.data.Response>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
            }

        })
    }
}