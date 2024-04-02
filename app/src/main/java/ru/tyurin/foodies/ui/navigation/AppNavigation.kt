package ru.tyurin.foodies.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.tyurin.foodies.R
import ru.tyurin.foodies.ui.screens.cart.CartScreen
import ru.tyurin.foodies.ui.screens.catalog.CatalogScreen
import ru.tyurin.foodies.ui.screens.product.FullProductItem
import ru.tyurin.foodies.ui.screens.search.SearchBar
import ru.tyurin.foodies.ui.screens.search.SearchScreen
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel
import ru.tyurin.foodies.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navHostController: NavHostController = rememberNavController()
    val catalogViewModel: CatalogViewModel = hiltViewModel()

    val isSearchBarVisible by catalogViewModel.isSearchBarVisible
    val searchQuery by catalogViewModel.searchQuery
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (isSearchBarVisible) {
                TopAppBar(
                    title = {
                        SearchBar(value = searchQuery, onValueChange = { catalogViewModel.setSearchQuery(it) }, placeholder = stringResource(
                            R.string.search_food
                        )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            catalogViewModel.toggleSearchBarVisibility()
                            navHostController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
                        }
                    },
                )
            } else {
                CenterAlignedTopAppBar(
                    modifier = Modifier.padding(top = 16.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* handle settings icon click */ }) {
                            Image(
                                painter = painterResource(id = R.drawable.filter),
                                contentDescription = "Filter Button",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            catalogViewModel.toggleSearchBarVisibility()
                            navHostController.navigate(Routes.Search.name)
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Search",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        content = { paddingValues ->
            Column {
                NavHost(
                    navController = navHostController,
                    startDestination = Routes.Catalog.name,
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    composable(route = Routes.Catalog.name) { CatalogScreen(catalogViewModel, navHostController) }
//                    composable(route = Routes.Cart.name) { CartScreen(catalogViewModel) }
                    composable(route = Routes.Search.name) { SearchScreen(
                        catalogViewModel = catalogViewModel,
                        navHostController = navHostController
                    ) }
                    composable(route = Routes.Product.name) {
                        FullProductItem(
                            product = catalogViewModel.selectedProduct.value!!,
                            navController = navHostController,
                            catalogViewModel
                        )
                    }
                }
            }
        }
    )
}

