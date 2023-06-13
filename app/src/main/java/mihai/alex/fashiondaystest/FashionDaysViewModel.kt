package mihai.alex.fashiondaystest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import mihai.alex.fashiondaystest.data.Element
import mihai.alex.fashiondaystest.data.TypeItem
import mihai.alex.fashiondaystest.model.ClothingRepository

class FashionDaysViewModel : ViewModel() {

    private val repository = ClothingRepository()
    val clothingState = MutableStateFlow(ArrayList<Element>())

    init {
        getClothing()
    }

    private fun getClothing() {
        viewModelScope.launch {
            repository.getClothing().catch {
                it.message?.let { message -> Log.e("ERROR", message) }
            }.collect {
                val initialList = it.products.sortedBy { product ->
                    product.productBrand.trim().lowercase()
                }
                val elementList: ArrayList<Element> = ArrayList()
                var lastBrand: String? = null
                initialList.forEach { product ->
                    val element = Element(
                        product = product, brand = "", type = TypeItem.PRODUCT
                    )

                    if (product.productBrand != lastBrand) {

                        val brand = Element(
                            product = null, brand = product.productBrand, type = TypeItem.HEADER
                        )

                        elementList.add(brand)
                        lastBrand = brand.brand
                    }
                    elementList.add(element)
                }

                clothingState.value = elementList
            }
        }
    }

    fun removeProduct(productPosition: Int) {
        val removingElement = clothingState.value[productPosition]
        clothingState.value.remove(removingElement)
        val exist = clothingState.value.any {
            it.product?.productBrand == removingElement.product?.productBrand
        }
        if (!exist) {
            val brand = clothingState.value.filter {
                it.brand == removingElement.product?.productBrand
            }
            brand.forEach {
                clothingState.value.remove(it)
            }
        }
    }

    fun undoProduct(productPosition: Int, deleteProduct: Element) {

        val exist = clothingState.value.any {
            it.product?.productBrand == deleteProduct.product?.productBrand
        }

        if (exist) {
            clothingState.value.add(productPosition, deleteProduct)
        } else {

            val undoPosition = productPosition - 1
            clothingState.value.add(undoPosition, deleteProduct)

            val brandList = clothingState.value.filter {
                it.brand == deleteProduct.product?.productBrand
            }

            if (brandList.isEmpty()) {
                val undoHeader = Element(
                    product = null,
                    brand = deleteProduct.product?.productBrand,
                    type = TypeItem.HEADER
                )
                clothingState.value.add(undoPosition, undoHeader)
            }
        }
    }

    fun refreshList() {
        getClothing()
    }
}