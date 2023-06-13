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
            repository.getClothing()
                .catch {
                    it.message?.let { message -> Log.e("ERROR", message) }
                }.collect {
                    val initialList = it.products.sortedBy { product ->
                        product.productBrand.trim().lowercase()
                    }
                    val elementList: ArrayList<Element> = ArrayList()
                    var lastBrand: String? = null
                    initialList.forEach {product ->
                        val element = Element(
                            product = product,
                            brand = "",
                            type = TypeItem.PRODUCT
                        )

                        if (product.productBrand != lastBrand) {

                            val brand = Element(
                                product = null,
                                brand = product.productBrand,
                                type = TypeItem.HEADER
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
        val element = clothingState.value[productPosition]
        clothingState.value.remove(element)
    }

    fun undoProduct(productPosition: Int, deleteProduct: Element) {
        clothingState.value.add(productPosition, deleteProduct)
    }

    fun refreshList() {
        getClothing()
    }
}