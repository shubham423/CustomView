package com.example.customview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannedString
import androidx.core.text.buildSpannedString
import androidx.core.text.scale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionList=listOf(
            SemiCircleView.Section("Protein", 25.0, Color.RED),
            SemiCircleView.Section("Fats", 35.0, Color.GREEN),
            SemiCircleView.Section("Minerals", 15.0, Color.BLUE),
            SemiCircleView.Section("Vitamins", 25.0, Color.YELLOW),
            SemiCircleView.Section("ere", 25.0, Color.BLUE),
        )
        val semiCircleView = findViewById<SemiCircleView>(R.id.semiCircleView)
        val spannableString=generateCenterSpannableText("570","Energy","Kcal")
        semiCircleView.setSpannableText(spannableString)
        semiCircleView.setStrokeWidth(15f)
        semiCircleView.drawLabels(true)
        semiCircleView.setSections(sectionList)
    }

    private fun generateCenterSpannableText(text1:String,text2:String,text3:String,): SpannedString {
        return buildSpannedString {
            scale(2.5f) {
                append(text1 + "\n")
            }
            scale(1.5f) {
                append(text2 + "\n")
            }
            scale(1.3f) {
                append(text3+"   ")
            }
        }
    }
}