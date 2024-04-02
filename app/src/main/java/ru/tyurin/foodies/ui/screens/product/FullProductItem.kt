package ru.tyurin.foodies.ui.screens.product

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.tyurin.foodies.R
import ru.tyurin.foodies.domain.models.Product
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel
import ru.tyurin.foodies.ui.theme.Dark
import ru.tyurin.foodies.ui.theme.Orange
import ru.tyurin.foodies.ui.theme.White

@Composable
fun FullProductItem(
    product: Product,
    navController: NavHostController,
    catalogViewModel: CatalogViewModel
) {
    val scrollState = rememberScrollState()
    Box {
        FloatingActionButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            containerColor = White,
            shape = CircleShape,
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Dark
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
        ) {

            Image(
                painter = painterResource(id = R.drawable.product_image),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = product.name,
                modifier = Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                text = product.description, maxLines = 2, softWrap = true,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp, end = 16.dp, start = 16.dp)
            )
            Column {
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(text = "Вес", modifier = Modifier.weight(1f))
                    Text(
                        text = "${product.measure} ${product.measureUnit} ",
                        textAlign = TextAlign.End
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(text = stringResource(R.string.energy), modifier = Modifier.weight(1f))
                    Text(text = product.energyPer100Grams.toString(), textAlign = TextAlign.End)
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(text = stringResource(R.string.proteins), modifier = Modifier.weight(1f))
                    Text(text = product.proteinsPer100Grams.toString(), textAlign = TextAlign.End)
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(text = stringResource(R.string.fats), modifier = Modifier.weight(1f))
                    Text(text = product.fatsPer100Grams.toString(), textAlign = TextAlign.End)
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.padding(vertical = 13.dp, horizontal = 16.dp)
                ) {
                    Text(text = "Углеводы", modifier = Modifier.weight(1f))
                    Text(
                        text = product.carbohydratesPer100Grams.toString(),
                        textAlign = TextAlign.End
                    )
                }
                HorizontalDivider()
            }
            FloatingActionButton(
                onClick = {
                    catalogViewModel.addToCart(product.id)
                    Log.d("CART", catalogViewModel.productsInCart.toString())
                          },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                containerColor = Orange
            ) {
                Text(
                    text = stringResource(R.string.in_basket,
                        (product.priceCurrent / 100)
                    ),
                    fontSize = 16.sp,
                    color = White
                )
            }
        }
    }

}

