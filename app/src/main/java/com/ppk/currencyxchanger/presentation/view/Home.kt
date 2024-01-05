package com.ppk.currencyxchanger.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    Scaffold(topBar = {
        Text(text = "Currency XChanger")
    }) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {

            //amount  Currency type
            //search box
            //currency list

        }
    }
}
