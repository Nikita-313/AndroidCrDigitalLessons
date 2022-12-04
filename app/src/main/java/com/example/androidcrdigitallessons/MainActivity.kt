package com.example.androidcrdigitallessons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TEG = "MainActivityTask1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Создание массива и генерация очков команд
        val footballMatches: Array<FootballMatch> = Array<FootballMatch>(10) {
            FootballMatch(
                (0..5).random(),
                (0..5).random()
            )
        }

        // Вывод массива
        Log.i(TEG, "Создание массива с 10ю матчами:")
        printMatches(footballMatches);

        // Удаляем матчи с однаковым счетом
        val uniqueFootballMatches = filterNotUniqueMatches(footballMatches);

        // Вывод спика после удаления одинаковых матчей
        Log.i(TEG, "Вывод массива после удаления матчей:")
        printMatches(uniqueFootballMatches);

        // Создание множества Set c максимальным разрывом в очках
        val maxGapSet = setOf(foundMamGap(uniqueFootballMatches))
        Log.i(TEG, "Максимальный разрыв в очках: ${maxGapSet.first()}")
    }

    // Удаление матчей с одинаковым счетом
    private fun filterNotUniqueMatches(array: Array<FootballMatch>): Array<FootballMatch> {
        Log.i(TEG, "Удалени матчей с одинаковым счетом:")
        val uniqueFootballMatchesList = mutableListOf<FootballMatch>()
        var isUnique = true;
        for (i in array.indices) {
            for (j in i + 1 until array.size) {
                if (array[i].pointsFirstTeam == array[j].pointsFirstTeam &&
                    array[i].pointsSecondTeam == array[j].pointsSecondTeam
                )
                    isUnique = false;
            }
            if (isUnique) uniqueFootballMatchesList.add(array[i])
            else Log.i(TEG, "Удален матч с индексом: $i ")
            isUnique = true;
        }
        return uniqueFootballMatchesList.toTypedArray();
    }

    // Поиск максимального разрыва в очках
    private fun foundMamGap(array: Array<FootballMatch>): Int {
        Log.i(TEG, "Поиск максимального разрыва в очках:")
        var maxGap = 0
        array.forEachIndexed { i, el ->
            run {
                val gap = kotlin.math.abs(el.pointsFirstTeam - el.pointsSecondTeam)
                maxGap = if (gap > maxGap) gap else maxGap
                Log.i(TEG, "$i) ${el.pointsFirstTeam}:${el.pointsSecondTeam}")
                Log.i(TEG, "Разрыв в очках: $gap")
            }
        }
        return maxGap;
    }

    // Вывод списка матчей
    private fun printMatches(array: Array<FootballMatch>) {
        array.forEachIndexed { i, el ->
            Log.i(TEG, "$i) ${el.pointsFirstTeam}:${el.pointsSecondTeam}")
        }
    }
}