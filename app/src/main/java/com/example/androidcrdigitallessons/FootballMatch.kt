package com.example.androidcrdigitallessons

class FootballMatch(var pointsFirstTeam: Int = 0,var pointsSecondTeam: Int = 0) {
    // Функцию для задания кол-ва очков (по заданию)
    fun setPoints(firstTeamPoints:Int, secondTeamPoints:Int){
        pointsFirstTeam = firstTeamPoints
        pointsSecondTeam = secondTeamPoints
    }
}