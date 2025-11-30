package com.photography.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.photography.R
import com.photography.ui.theme.Purple40

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(scrollBehavior: TopAppBarScrollBehavior) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple40)
    ) {
        MediumTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 16.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Purple40,
                scrolledContainerColor = Purple40,
                titleContentColor = Color.White
            ),
            scrollBehavior = scrollBehavior
        )
    }
}