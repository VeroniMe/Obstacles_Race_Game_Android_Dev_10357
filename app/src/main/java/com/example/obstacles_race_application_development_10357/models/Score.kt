package com.example.obstacles_race_application_development_10357.models

data class Score private constructor (
    val name : String,
    val scorePoints : Int,
    val lan : Double,
    val lng : Double
) {
    class Builder(
        var name : String = "",
        var scorePoints : Int = 0,
        var lat : Double = 0.0,
        var lng : Double = 0.0
    ) {

        fun name(name : String) = apply { this.name = name}
        fun scorePoints(scorePoints : Int) = apply { this.scorePoints = scorePoints}
        fun lan(lan : Double) = apply { this.lat = lan}
        fun lng(lng : Double) = apply { this.lng = lng}
        fun build() = Score(name,
                            scorePoints,
                            lat,
                            lng)


    }
}
