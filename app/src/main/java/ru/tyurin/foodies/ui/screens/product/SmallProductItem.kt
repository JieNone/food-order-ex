package ru.tyurin.foodies.ui.screens.product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.tyurin.foodies.R
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.ui.navigation.Routes
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel
import ru.tyurin.foodies.ui.theme.Dark
import ru.tyurin.foodies.ui.theme.DarkBackGround
import ru.tyurin.foodies.ui.theme.Orange
import ru.tyurin.foodies.ui.theme.White


@Composable
fun ProductGrid(
    catalogViewModel: CatalogViewModel,
    navController: NavHostController
) {
    val productsState = catalogViewModel.products.collectAsState()
    val products = productsState.value
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(products) { product ->
            SmallProductScreen(
                product = product,
                navController = navController,
                catalogViewModel = catalogViewModel,
                onAddToCartClicked = { catalogViewModel.addToCart(product.id) }
            )
        }
    }
}

@Composable
fun SmallProductScreen(
    product: Product,
    isSelected: Boolean = false,
    navController: NavHostController,
    catalogViewModel: CatalogViewModel,
    onAddToCartClicked: () -> Unit
) {

    val (selected) = remember { mutableStateOf(isSelected) }
    val backgroundColor = if (selected) Orange else White
    val contentColor = if (selected) White else Dark

    val price = product.priceCurrent / 100

    Card(
        colors = CardDefaults.cardColors(
            containerColor = DarkBackGround
        ),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 200.dp, height = 340.dp),
        onClick = {
            navController.navigate(Routes.Product.name) {
                launchSingleTop = true
            }
            catalogViewModel.selectProduct(product = product)

        }
    ) {
        Column {

            Box{
                Image(
                    painter = painterResource(id = R.drawable.product_image),
                    contentDescription = null,
                )
                if (product.priceOld != null) {
                    Image(
                        painter = painterResource(id = R.drawable.discount),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(text = product.name)
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "${product.measure} ${product.measureUnit}")
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Bottom
            ) {
                ElevatedButton(
                    onClick = {
                        onAddToCartClicked()
                    }
                    ,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonColors(
                        containerColor = backgroundColor,
                        contentColor = contentColor,
                        disabledContainerColor = backgroundColor,
                        disabledContentColor = contentColor
                    ),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (product.priceOld == null) {
                            Text(text = "$price ₽")
                        } else {
                            Text(text = "$price ₽", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                text = "${product.priceOld / 100} ₽",
                                style = TextStyle(textDecoration = TextDecoration.LineThrough)
                            )
                        }
                    }
                }
            }
        }
    }
}
