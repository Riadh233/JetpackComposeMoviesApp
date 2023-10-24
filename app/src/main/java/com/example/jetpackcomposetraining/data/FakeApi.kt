package com.example.jetpackcomposetraining.data

import android.content.Context
import com.example.jetpackcomposetraining.R

class FakeApi(private val context : Context) {
    fun getMovies(query: String): List<Movie> {
        val movies = listOf(
            Movie(
                0,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4f,
                "1.1M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Robert Downey", "Matt Damon")
            ),
            Movie(
                1,
                "The Shawshank Redemption",
                context.getString(R.string.shawshank),
                R.drawable.the_shawshank_redemption,
                4.5f,
                "2M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                2,
                "Inception",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.inception,
                4f,
                "1.8M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                3,
                "The Dark Knight",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.the_dark_knight,
                4.5f,
                "800K",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                4,
                "Killers of the Flower Moon",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.killers_of_the_flower_moon,
                3f,
                "1.2M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                5,
                "Avengers:Infinity War",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.avengers_infinity_war,
                3f,
                "700K",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
        )
        val popularMovies = listOf( Movie(
            0,
            "Forrest Gump",
            context.getString(R.string.forrest_gump),
            R.drawable.forrest_gump,
            5f,
            "1.8M",
            "Robert Zemeckis",
            listOf("Tom Hanks", "Robin Wright", "Winston Groom")
        ),
            Movie(
                1,
                "The Shawshank Redemption",
                context.getString(R.string.shawshank),
                R.drawable.the_shawshank_redemption,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                2,
                "Inception",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.inception,
                5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                3,
                "The Dark Knight",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.the_dark_knight,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                4,
                "Killers of the Flower Moon",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.killers_of_the_flower_moon,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),
            Movie(
                5,
                "Avengers:Infinity War",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.avengers_infinity_war,
                3f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy", "Cylian Murphy", "Cylian Murphy")
            ),)
        return if (query == "popular")
            popularMovies else movies
    }

}