package mihai.alex.fashiondaystest.data

data class Product (
    val productId: String,
    val productName: String,
    val productBrand: String,
    val productImages: List<ProductImages>
)

data class ProductImages(
    val thumb: List<String>,
    val zoom: List<String>,
)