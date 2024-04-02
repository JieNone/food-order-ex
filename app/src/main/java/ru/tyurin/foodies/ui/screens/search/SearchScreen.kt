package ru.tyurin.foodies.ui.screens.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.tyurin.foodies.ui.screens.product.SmallProductScreen
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel

@Composable
fun SearchScreen(
    catalogViewModel: CatalogViewModel,
    navHostController: NavHostController
) {
    val productsState = catalogViewModel.products.collectAsState()
    val products = productsState.value
    val searchQuery = catalogViewModel.searchQuery.value.lowercase()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(products.filter { it.name.lowercase().contains(searchQuery) }) { product ->
            SmallProductScreen(
                product = product,
                catalogViewModel = catalogViewModel,
                navController = navHostController,
                onAddToCartClicked = { catalogViewModel.addToCart(product.id) }
            )
        }
    }
}
