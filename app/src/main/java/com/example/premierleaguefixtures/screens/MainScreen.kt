package com.example.premierleaguefixtures.screens


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.premierleaguefixtures.MainScreenViewModel
import com.example.premierleaguefixtures.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen( viewModel: MainScreenViewModel = hiltViewModel(),  navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val matchesState by viewModel.getMatches().collectAsState()
    val isFetchingData by viewModel.getIsFetchingData().collectAsState()
    var isShowSearch by remember { mutableStateOf(false) }
    val searchEditText by viewModel.searchEditText.collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Matches 2021")
                            Box(Modifier.width(5.dp))
                            if (isFetchingData) {
                                Text(text = "Fetching...")
                            }
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = viewModel::fetchServerData,
                            enabled = !isFetchingData
                        ) {
                            Icon(Icons.Filled.Refresh, null)
                        }
                        IconButton(
                            onClick = {
                                isShowSearch = !isShowSearch
                                viewModel.setSearchEditText("")
                            },
                            enabled = !isFetchingData
                        ) {
                            Icon(Icons.Filled.Search, null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
                if (isFetchingData) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                if (isShowSearch)
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Filled.Search, null) },
                        value = searchEditText,
                        onValueChange = viewModel::setSearchEditText,
                        label = { Text(text = "Team name") }
                    )
            }

        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            items(matchesState.size) { i ->
                InfoCard(
                    i,
                    matchesState[i].matchNumber,
                    matchesState[i].dateUtc,
                    matchesState[i].homeTeam,
                    matchesState[i].awayTeam,
                    matchesState[i].homeTeamScore,
                    matchesState[i].awayTeamScore,
                    navController = navController,
                    isFetchingData
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCard(
    index: Int,
    matchNumber:Int,
    dateTime: String,
    homeTeam: String,
    awayTeam: String,
    homeTeamScore: Int?,
    awayTeamScore: Int?,
    navController: NavController,
    isFetchingData: Boolean,
) {
    val cardColor = if (index % 2 == 0) Color(0xFFF7F8FA) else Color.White
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssz")
    val zonedDateTime = ZonedDateTime.parse(dateTime, pattern)
    val date = zonedDateTime.format(DateTimeFormatter.ofPattern("dd.MM")).toString()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(0.dp),
        onClick = {
            if (!isFetchingData)
            navController.navigate("details/$matchNumber")
        }
    ) {
        Box {
            Text(
                modifier = Modifier.padding(8.dp),
                text = date,
                color = Color(0xFF897995),
                fontSize = 12.sp
            )
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.weight(2f), color = Color.Black.copy(0.0f)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = homeTeam,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                }
                Surface(
                    modifier = Modifier
                        .weight(3f)
                        .padding(start = 20.dp, end = 20.dp),
                    color = Color.Black.copy(0.0f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ball),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(25.dp)
                        )
                        if (homeTeamScore == null || awayTeamScore == null) Text(text = "×")
                        else Text(text = "$homeTeamScore:$awayTeamScore")
                        Image(
                            painter = painterResource(id = R.drawable.ball),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
                Surface(modifier = Modifier.weight(2f), color = Color.Black.copy(0.0f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = awayTeam,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

            }
        }
    }
}