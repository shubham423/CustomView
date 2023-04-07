package com.example.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.SpannedString
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class SemiCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var sections: List<Section> = emptyList()
    private var strokeWidth: Float = 20f
    private var startAngle: Float = 180f
    private var radius: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var drawLabel=true
    private var spannableText: CharSequence = ""

    data class Section(val label: String, val value: Double, val color: Int)

    @RequiresApi(Build.VERSION_CODES.M)
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
            if (sectionAngle<=180f){
                canvas.drawPath(path, paint)
            }
            if (drawLabel){
                drawLabel(centerX, startAngle, sectionAngle, centerY, section, canvas)
            }
            startAngle += sectionAngle.toFloat()
        }
        // Draw spannable text in the center of the SemiCircleView
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.color = Color.BLACK
        textPaint.textSize = radius / 12f
        textPaint.textAlign = Paint.Align.CENTER

        // Create a layout with the spannable text
        val textLayout = StaticLayout.Builder
            .obtain(spannableText, 0, spannableText.length, textPaint, width)
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            .build()

        // Draw the layout at the center of the SemiCircleView
        val textY = centerY - radius/ 1.5f
        canvas.save()
        canvas.translate(50f, textY)
        textLayout.draw(canvas)
        canvas.restore()

        //reset to initial value
        startAngle=180f
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

    @JvmName("setSections1")
    fun setSections(sections: List<Section>) {
        this.sections = sections
        invalidate()
    }

    fun drawLabels(drawLabel:Boolean) {
        this.drawLabel = drawLabel
    }

    fun setSpannableText(spannableText: SpannedString) {
        this.spannableText = spannableText
    }
}