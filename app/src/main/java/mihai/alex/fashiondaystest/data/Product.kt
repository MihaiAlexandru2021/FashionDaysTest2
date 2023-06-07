package mihai.alex.fashiondaystest.data

data class Product (
    val productId: String,
    val productName: String,
    val productBrand: String,
    val productImages: List<ProductImages>
)