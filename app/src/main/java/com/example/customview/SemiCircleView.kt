package com.example.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class SemiCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var sections: List<Section> = emptyList()
    private var strokeWidth: Float = 20f
    private var startAngle: Float = 180f
    private var radius: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    data class Section(val label: String, val value: Double, val color: Int)

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = (width / 2f)
        val centerY = (height / 2f)
        Log.d("CustomView", "X=$centerX and Y=$centerY")

        radius = min(centerX - (paddingLeft+paddingRight), centerY - (paddingTop+paddingBottom)) - strokeWidth / 2f

        val rectF = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color=Color.WHITE

        for (section in sections) {
            val sectionAngle = section.value * 180 / 100

            paint.color = section.color
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeJoin = Paint.Join.ROUND
            paint.style = Paint.Style.STROKE
            val path = Path()
            if (sections.last()==section){
                path.addArc(rectF, startAngle, sectionAngle.toFloat()-2f)
            }else{
                path.addArc(rectF, startAngle, sectionAngle.toFloat() -5f)
            }
            canvas.drawPath(path, paint)
            drawLabel(centerX, startAngle, sectionAngle, centerY, section, canvas)
            startAngle += sectionAngle.toFloat()
        }

        insideCircleText(centerY, canvas, centerX)
    }

    private fun drawLabel(
        centerX: Float,
        currentAngle: Float,
        sectionAngle: Double,
        centerY: Float,
        section: Section,
        canvas: Canvas
    ) {
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = Color.BLACK
        textPaint.textSize = radius / 12f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.strokeWidth = 5f

        val labelX =
            centerX + ((radius + radius * 0.4f) * cos(Math.toRadians((currentAngle + sectionAngle / 2f)))).toFloat()
        val labelY =
            centerY + ((radius + radius * 0.2f) * sin(Math.toRadians((currentAngle + sectionAngle / 2f)))).toFloat()

        Log.d(
            "CustomView",
            "labelX=${((radius + radius * 0.4f) * cos(Math.toRadians((currentAngle + sectionAngle / 2f)))).toFloat()} and labelY= ${
                ((radius + radius * 0.2f) * sin(
                    Math.toRadians((currentAngle + sectionAngle / 2f))
                )).toFloat()
            } and ${section.label}"
        )
        canvas.drawText("${section.label}\n${section.value}%", labelX, labelY, textPaint)
    }

    private fun insideCircleText(
        centerY: Float,
        canvas: Canvas,
        centerX: Float
    ) {
        val text1 = "570"
        val text2 = "Energy"
        val text3 = "Kcal/kg"

        val textPaint1 = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint1.color = Color.BLACK
        textPaint1.textSize = radius / 4f
        textPaint1.textAlign = Paint.Align.CENTER

        val textPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint2.color = Color.BLACK
        textPaint2.textSize = radius / 9f
        textPaint2.textAlign = Paint.Align.CENTER

        val textPaint3 = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint3.color = Color.GRAY
        textPaint3.textSize = radius / 12f
        textPaint3.textAlign = Paint.Align.CENTER

        val textHeight1 = textPaint1.descent() - textPaint1.ascent()
        val textHeight2 = textPaint2.descent() - textPaint2.ascent()
        val textHeight3 = textPaint3.descent() - textPaint3.ascent()

        val totalTextHeight = textHeight1 + textHeight2 + textHeight3

        val text1Y = centerY - totalTextHeight / 2f + textHeight1
        val text2Y = text1Y + textHeight1 + textHeight2 / 2f
        val text3Y = text2Y + textHeight2 + textHeight3 / 2f

        canvas.drawText(text1, centerX, text1Y - radius / 2, textPaint1)
        canvas.drawText(text2, centerX, text2Y - radius / 1.5f, textPaint2)
        canvas.drawText(text3, centerX, text3Y - radius / 1.5f, textPaint3)
    }

    @JvmName("setSections1")
    fun setSections(sections: List<Section>) {
        this.sections = sections
        invalidate()
    }
}