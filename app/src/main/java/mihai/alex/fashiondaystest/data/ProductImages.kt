package mihai.alex.fashiondaystest.data

import com.google.gson.annotations.SerializedName

data class ProductImages(
    @SerializedName("thumb")
    val thumb: List<String>,
    @SerializedName("zoom")
    val zoom: List<String>,
)