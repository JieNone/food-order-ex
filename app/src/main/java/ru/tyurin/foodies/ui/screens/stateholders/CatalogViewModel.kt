package ru.tyurin.foodies.ui.screens.stateholders

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.domain.models.Tag
import ru.tyurin.foodies.domain.repository.Repository
import javax.inject.Inject


/**
 * ViewModel для экрана CatalogScreen
 */

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: Repository
) : ViewModel() {


    init {
        loadTags()
        loadProducts()
    }



    /**
     * Список тегов
     */
    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    val tags: MutableStateFlow<List<Tag>>
        get() = _tags

    private fun loadTags() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tagsList = productRepository.getTags()
                _tags.value = tagsList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /**
     * Список элементов
     */
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: MutableStateFlow<List<Product>>
        get() = _products


    private fun loadProducts() {
        viewModelScope.launch {
            try {

                val productsList = productRepository.getProducts()
                _products.value = productsList
            } catch (e: Exception) {
                Log.e("Loading products exception", e.toString())
                e.printStackTrace()
            }
        }
    }

    /**
     * Поиск по элементу
     */

    val isSearchBarVisible: MutableState<Boolean> = mutableStateOf(false)

    val searchQuery: MutableState<String> = mutableStateOf("")

    fun toggleSearchBarVisibility() {
        isSearchBarVisible.value = !isSearchBarVisible.value
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    val selectedProduct: MutableState<Product?> = mutableStateOf(null)

    fun selectProduct(product: Product) {
        selectedProduct.value = product
    }


    /**
     * Работа с корзиной
     */
    // ключ - id продукта, значение - количество в корзине
    val productsInCart: MutableMap<Int, Int> = mutableMapOf()

    fun addToCart(productId: Int) {
        if (productsInCart.containsKey(productId)) {
            val currentQuantity = productsInCart[productId] ?: 0
            productsInCart[productId] = currentQuantity + 1
        } else {
            productsInCart[productId] = 1
        }
    }

    fun decreaseQuantity(productId: Int) {
        if (productsInCart.containsKey(productId)) {
            val currentQuantity = productsInCart[productId] ?: 0
            if (currentQuantity > 0) {
                productsInCart[productId] = currentQuantity - 1
            }
        }
    }

    fun removeFromCart(productId: Int) {
        productsInCart.remove(productId)
    }

    fun getTotalQuantityInCart(): Int {
        return productsInCart.values.sum()
    }

    fun getTotalPriceInCart(): Int {
        var totalPrice = 0
        for ((productId, quantity) in productsInCart) {
            val product = getProductById(productId)
            product?.let {
                totalPrice += it.priceCurrent * quantity
            }
        }
        return totalPrice
    }

    fun getProductById(productId: Int): Product? {
        return runBlocking {
            try {
                val products = productRepository.getProducts()
                products.find { it.id == productId }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}
