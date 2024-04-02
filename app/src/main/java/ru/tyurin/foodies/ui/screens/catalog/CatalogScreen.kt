package ru.tyurin.foodies.ui.screens.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.tyurin.foodies.R
import ru.tyurin.foodies.ui.navigation.Routes
import ru.tyurin.foodies.ui.screens.product.ProductGrid
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel
import ru.tyurin.foodies.ui.theme.Orange
import ru.tyurin.foodies.ui.theme.White

@Composable
fun CatalogScreen(
    catalogViewModel: CatalogViewModel,
    navController: NavHostController
) {

    val productsInCart = catalogViewModel.productsInCart

    Box {

        Column(modifier = Modifier.fillMaxWidth()) {
            TagScreen(catalogViewModel)
            ProductGrid(catalogViewModel, navController)
        }
    }
}
