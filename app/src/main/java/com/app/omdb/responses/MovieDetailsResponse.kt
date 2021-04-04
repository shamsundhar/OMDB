package com.app.omdb.responses

data class MovieDetailsResponse(
    val Plot: String,
    val Poster: String,
    val Ratings: List<Rating>,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
)

data class Rating(
    val Source: String,
    val Value: String
)