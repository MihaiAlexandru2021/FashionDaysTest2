package mihai.alex.fashiondaystest.data

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_brand")
    val productBrand: String,
    @SerializedName("product_images")
    val productImages: ProductImages
)