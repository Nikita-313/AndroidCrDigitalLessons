package com.example.premierleaguefixtures.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.premierleaguefixtures.DetailsScreenViewModel
import com.example.premierleaguefixtures.MainScreenViewModel
import com.example.premierleaguefixtures.R
import com.example.premierleaguefixtures.data.model.Match
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(matchNumber: Int?,viewModel: DetailsScreenViewModel = hiltViewModel(), navController: NavController) {
    if (matchNumber != null) {
        viewModel.getMatchByNumber(matchNumber)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val matchState by viewModel.getMatch().collectAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = matchState?.homeTeam + " vs " + matchState?.awayTeam) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = it.calculateTopPadding())
            ) {
                item { TopBar(matchState) }
                item { InfoCard(tag = "Home team", logo = R.drawable.home_team, name = matchState?.homeTeam.toString()) }
                item { InfoCard(tag = "Away team", logo = R.drawable.away_team, name = matchState?.awayTeam.toString()) }
                item {
                    InfoCard(
                        tag = "Location",
                        logo = R.drawable.stadium_icon,
                        name = matchState?.location.toString()
                    )
                }
                item { InfoCard(tag = "Group", logo = R.drawable.group, matchState?.group.toString()) }
                item { Box(modifier = Modifier.height(10.dp)) }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TopBar(matchState:Match?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(top = 2.dp, start = 15.dp, end = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            text = "Round number: " + matchState?.roundNumber.toString(),
            color = Color.Black,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = matchState?.dateUtc.toString(),
            color = Color.Black,
            fontSize = 16.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
        ) {
            Surface(modifier = Modifier.weight(3f), color = Color.Black.copy(0.0f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = matchState?.homeTeamScore.toString(),
                        color = Color.Black,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = matchState?.homeTeam.toString(),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Surface(modifier = Modifier.weight(1f), color = Color.Black.copy(0.0f)) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = ":",
                        color = Color.Black,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Surface(modifier = Modifier.weight(3f), color = Color.Black.copy(0.0f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = matchState?.awayTeamScore.toString(),
                        color = Color.Black,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = matchState?.awayTeam.toString(),
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCard(tag: String, logo: Int, name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
    ) {
        Row {
            Surface(modifier = Modifier.weight(3f), color = Color.Black.copy(0.0f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = tag,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                    )
                    Image(
                        painter = painterResource(id = logo),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(65.dp)
                    )
                }
            }
            Surface(modifier = Modifier.weight(4f), color = Color.Black.copy(0.0f)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )

                }

            }
        }

    }
}
