package com.example.project4.data

object Constants {
    //Read documentation here: https://www.themealdb.com/api.php

    //No key needed since developer key is just "1" which I included here in base url
    const val BASE_URL = "https://themealdb.com/api/json/v1/1"

    //Append to BASE_URL to get some recipes (to fill in recipe list at the start).
    //The API unfortunately doesn't give you an endpoint for a list of every meal,
    //this returns a short, default list. We will just work with this list for convenience.
    const val GET_ALL = "/search.php?s="

    //Append to BASE_URL to get list of categories (to fill in categories dropdown at the start)
    const val GET_CATEGORIES = "/categories.php"

    //When user searches or selects from category dropdown, we use kotlin functions
    //like .filter rather than sending out another api request
}