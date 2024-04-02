package ru.tyurin.foodies.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel

@Composable
fun CartScreen(
    viewModel: CatalogViewModel = viewModel()
) {
    val productsInCart = viewModel.productsInCart

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Корзина",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (productsInCart.isEmpty()) {
            Text(text = "Корзина пуста")
        } else {
            productsInCart.forEach { (productId, quantity) ->
                val product = viewModel.getProductById(productId)
                product?.let {
                    CartItem(product = it, quantity = quantity)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Оформить заказ")
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    quantity: Int,
    viewModel: CatalogViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = product.name)
        Text(text = "Количество: $quantity")
        Text(text = "Цена за штуку: ${product.priceCurrent}")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.decreaseQuantity(product.id) }
            ) {
                Text(text = "-")
            }
            Button(
                onClick = { viewModel.removeFromCart(product.id) }
            ) {
                Text(text = "Удалить")
            }
            Button(
                onClick = { viewModel.addToCart(product.id) }
            ) {
                Text(text = "+")
            }
        }
    }
}
