package ru.tyurin.foodies.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.tyurin.foodies.R
import ru.tyurin.foodies.domain.models.Product

@Composable
fun CartItem(product: Product) {
    Row {
       Image(painter = painterResource(id = R.drawable.product_image), contentDescription = null)
       Column {
           Text(text = product.name)
           Spacer(modifier = Modifier.padding(8.dp))
           Row{
               Button(onClick = { /*TODO*/ }) {
                   Text(text = "-")
               }
               Text(text = "1")
               Button(onClick = { /*TODO*/ }) {
                   Text(text = "-")
               }
               Text(text = "${product.priceCurrent / 100}")
           }
       }
    }
}