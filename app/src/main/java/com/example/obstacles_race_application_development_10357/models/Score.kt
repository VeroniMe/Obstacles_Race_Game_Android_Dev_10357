package com.example.obstacles_race_application_development_10357.models

data class Score private constructor (
    val lat : Double,
    val lng : Double,
    val name : String,
    val scorePoints : Int,

) {
    class Builder(
        var lat : Double = 0.0,
        var lng : Double = 0.0,
        var name : String = "",
        var scorePoints : Int = 0,

    ) {

        fun name(name : String) = apply { this.name = name}
        fun scorePoints(scorePoints : Int) = apply { this.scorePoints = scorePoints}
        fun lat(lat : Double) = apply { this.lat = lat}
        fun lng(lng : Double) = apply { this.lng = lng}
        fun build() = Score(lat,
                            lng,
                            name,
                            scorePoints)


    }
}
