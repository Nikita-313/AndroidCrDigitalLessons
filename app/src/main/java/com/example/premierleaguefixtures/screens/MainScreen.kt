package com.example.premierleaguefixtures.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.premierleaguefixtures.MainScreenViewModel
import com.example.premierleaguefixtures.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val matchesState by viewModel.getMatches().collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Matches 2021")
                        Box(Modifier.width(10.dp))
                        if (matchesState.isEmpty()) {
                            Text(text = "loading")
                            Box(Modifier.width(10.dp))
                            CircularProgressIndicator(
                                modifier = Modifier.size(size = 28.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }

                },
                scrollBehavior = scrollBehavior
            )
        },
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
        ) {

            items(matchesState.size) { i ->
                InfoCard(
                    i,
                    matchesState[i].dateUtc,
                    matchesState[i].homeTeam,
                    matchesState[i].awayTeam,
                    matchesState[i].homeTeamScore,
                    matchesState[i].awayTeamScore
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCard(
    index: Int,
    dateTime: String,
    homeTeam: String,
    awayTeam: String,
    homeTeamScore: Int?,
    awayTeamScore: Int?
) {
    val cardColor = if (index % 2 == 0) Color(0xFFF7F8FA) else Color.White
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssz")
    val zonedDateTime = ZonedDateTime.parse(dateTime, pattern)
    val date = zonedDateTime.format(DateTimeFormatter.ofPattern("dd.MM")).toString()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(0.dp)
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