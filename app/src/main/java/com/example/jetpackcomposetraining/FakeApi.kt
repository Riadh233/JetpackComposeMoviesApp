package com.example.jetpackcomposetraining

import android.content.Context

class FakeApi(private val context : Context) {
    fun getMovies() : List<Movie>{
        return listOf(
            Movie(
                0,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
            Movie(
                1,
                "xb",
                "l",
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
            Movie(
                2,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
            Movie(
                3,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
            Movie(
                4,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
            Movie(
                5,
                "Oppenheimer",
                context.getString(R.string.desc_oppenheimer),
                R.drawable.oppenheimer,
                4.5f,
                "1.5M",
                "Cristopher Nolan",
                listOf("Cylian Murphy","Cylian Murphy","Cylian Murphy")
            ),
        )
    }
}