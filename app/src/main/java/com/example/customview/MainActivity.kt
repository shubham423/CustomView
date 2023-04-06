package com.example.customview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionList=listOf(
            SemiCircleView.Section("Protein", 25.0, Color.RED),
            SemiCircleView.Section("Fats", 35.0, Color.GREEN),
            SemiCircleView.Section("Minerals", 15.0, Color.BLUE),
            SemiCircleView.Section("Vitamins", 25.0, Color.YELLOW),
        )
        val semiCircleView = findViewById<SemiCircleView>(R.id.semiCircleView)
        semiCircleView.setSections(sectionList)
    }
}