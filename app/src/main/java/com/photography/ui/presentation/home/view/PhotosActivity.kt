package com.photography.ui.presentation.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.photography.R
import com.photography.data.remote.client.ResponseEvent
import com.photography.ui.presentation.home.model.PhotosModel
import com.photography.ui.presentation.home.model.Urls
import com.photography.ui.presentation.home.viewmodel.PhotosViewModel
import com.photography.ui.theme.PhotographyTheme
import com.photography.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "PhotosActivity"

@AndroidEntryPoint
class PhotosActivity : ComponentActivity() {

    private val viewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotographyTheme {
                View(viewModel)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun TopAppBarPreview() {
        TopAppBar()
    }

    @Preview(showBackground = true)
    @Composable
    fun PhotosGridPreview() {
        val list = listOf(
            PhotosModel("1", null, null, null,100, 100, null, null, null, null, emptyList<String>(), 10, true, emptyList(), null, null, Urls(null, null, "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png", null, null), null),
            PhotosModel("2", null, null, null,100, 100, null, null, null, null, emptyList<String>(), 10, true, emptyList(), null, null, Urls(null, null, "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png", null, null), null),
            PhotosModel("3", null, null, null,100, 100, null, null, null, null, emptyList<String>(), 10, true, emptyList(), null, null, Urls(null, null, "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png", null, null), null),
            PhotosModel("4", null, null, null,100, 100, null, null, null, null, emptyList<String>(), 10, true, emptyList(), null, null, Urls(null, null, "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png", null, null), null)
        )

        PhotosGrid(list = list)
    }
}

@Composable
fun View(viewModel: PhotosViewModel) {
    val context = LocalContext.current
    // A surface container using the 'background' color from the theme
    Column {
        TopAppBar()
        Column {
            val event: ResponseEvent by viewModel.photosListFlow.collectAsStateWithLifecycle()
            val list = remember {
                mutableStateListOf<PhotosModel>()
            }
            LaunchedEffect(key1 = event) {
                when(event) {
                    is ResponseEvent.Failure -> {
                        Toast.makeText(context, "Failed to load", Toast.LENGTH_LONG).show()
                    }
                    ResponseEvent.Loading -> {

                    }
                    is ResponseEvent.Success -> {
                        list.addAll((event as ResponseEvent.Success).data as List<PhotosModel>)
                    }
                }
            }

            PhotosGrid(list)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val context = LocalContext.current
    Scaffold(modifier = Modifier
        .height(56.dp)
        .fillMaxWidth(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = ContextCompat.getString(context, R.string.app_name),
                        fontSize = 16.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                )
            )
        }) { }
}

@Composable
fun PhotosGrid(list: List<PhotosModel>) {

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
        contentPadding = PaddingValues(all = 4.dp)
    ) {
        items(list) {
            PhotosItem(photosModel = it)
        }
    }
}

@Composable
fun PhotosItem(photosModel: PhotosModel) {
    val imgUrl = photosModel.urls?.regular
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.broken_image),
            placeholder = painterResource(R.drawable.ic_loader),
            contentDescription = photosModel.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RectangleShape)
        )
    }
}
