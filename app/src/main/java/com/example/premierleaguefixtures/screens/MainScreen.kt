package com.example.premierleaguefixtures.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.premierleaguefixtures.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "Matches") },
                scrollBehavior = scrollBehavior
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
        ) {
            items(10) { i -> InfoCard(i,"2:10 08/10/2022","GWS Giants","Adelaide Crows",1,1); }
        }
    }
}

@Composable
fun InfoCard(index: Int,dateTime : String, homeTeam: String, awayTeam: String, homeTeamScore:Int?, awayTeamScore:Int?) {
    val cardColor = if (index % 2 == 0) Color(0xFFF7F8FA) else Color.White
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(0.dp)
    ) {
        Box {
            Text(
                modifier = Modifier.padding(8.dp),
                text = dateTime,
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
                        Text(text = homeTeam)
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
                        if(homeTeamScore == null || awayTeamScore == null) Text(text = "×")
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
                        Text(text = awayTeam)
                    }
                }

            }
        }
    }
}