package ru.skillbranch.devintensive.ui.custom
import android.content.Context

import android.graphics.*

import android.graphics.Bitmap.Config

import android.graphics.PorterDuff.Mode

import android.graphics.drawable.BitmapDrawable

import android.util.AttributeSet

import android.widget.ImageView

import androidx.annotation.ColorRes

import ru.skillbranch.devintensive.R

import ru.skillbranch.devintensive.utils.Utils

import kotlin.math.min





class CircleImageView @JvmOverloads constructor (

    context: Context,

    attrs: AttributeSet? = null,

    defStyleAttr: Int = 0

): ImageView(context, attrs, defStyleAttr) {

    companion object {

        private const val DEFAULT_BORDER_COLOR: Int = Color.WHITE

    }



    private var borderColor = DEFAULT_BORDER_COLOR

    private var borderWidth = Utils.convertDpToPx(context, 2)



    init {

        if (attrs != null) {

            val attrVal = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)

            borderColor = attrVal.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)

            borderWidth = attrVal.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, borderWidth)

            attrVal.recycle()

        }

    }



    override fun onDraw(canvas: Canvas) {

        val bitmap = getBitmapFromDrawable() ?: return

        if (width == 0 || height == 0) return



        val scaledBmp = getScaledBitmap(bitmap, width)

        val croppedBmp = getCenterCroppedBitmap(scaledBmp, width)

        val circleBmp = getCircleBitmap(croppedBmp)

        val strokedBmp = getStrokedBitmap(circleBmp, borderWidth, borderColor)



        canvas.drawBitmap(strokedBmp, 0F, 0F, null)

    }



    fun getBorderWidth(): Int = Utils.convertPxToDp(context, borderWidth)



    fun setBorderWidth(dp: Int) {

        borderWidth = Utils.convertDpToPx(context, dp)

        this.invalidate()

    }



    fun getBorderColor(): Int = borderColor



    fun setBorderColor(hex: String) { borderColor = Color.parseColor(hex) }



    fun setBorderColor(@ColorRes colorId: Int) {

        borderColor = resources.getColor(colorId, context.theme)

        this.invalidate()

    }



    fun generateAvatar(text: String, size: Int) : Bitmap{

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        paint.textSize = size.toFloat()

        paint.color = resources.getColor(R.color.color_accent, context.theme)

        paint.textAlign = Paint.Align.LEFT

        val baseline = -paint.ascent()

        val width = (paint.measureText(text) + 0.5f).toInt()

        val height = (baseline + paint.descent() + 0.5f).toInt()

        val image = Bitmap.createBitmap(width, height, Config.ARGB_8888)

        val canvas = Canvas(image)

        canvas.drawText(text, 0F, baseline, paint)

        return image

    }



    private fun getStrokedBitmap(squareBmp: Bitmap, strokeWidth: Int, color: Int): Bitmap {

        val inCircle = RectF()

        val strokeStart = strokeWidth / 2F

        val strokeEnd = squareBmp.width - strokeWidth / 2F



        inCircle.set(strokeStart , strokeStart, strokeEnd, strokeEnd)



        val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)

        strokePaint.color = color

        strokePaint.style = Paint.Style.STROKE

        strokePaint.strokeWidth = strokeWidth.toFloat()



        val canvas = Canvas(squareBmp)

        canvas.drawOval(inCircle, strokePaint)



        return squareBmp

    }



    private fun getCenterCroppedBitmap(bitmap: Bitmap, size: Int): Bitmap {

        val cropStartX = (bitmap.width - size) / 2

        val cropStartY = (bitmap.height - size) / 2



        return Bitmap.createBitmap(bitmap, cropStartX, cropStartY, size, size)

    }



    private fun getScaledBitmap(bitmap: Bitmap, minSide: Int) =

        if (bitmap.width != minSide || bitmap.height != minSide) {

            val smallest = min(bitmap.width, bitmap.height).toFloat()

            val factor = smallest / minSide

            Bitmap.createScaledBitmap(bitmap, (bitmap.width / factor).toInt(), (bitmap.height / factor).toInt(), false)

        }

        else bitmap



    private fun getBitmapFromDrawable(): Bitmap? {

        if (drawable == null)

            return null



        if (drawable is BitmapDrawable)

            return (drawable as BitmapDrawable).bitmap



        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)

        drawable.draw(canvas)



        return bitmap

    }



    private fun getCircleBitmap(bitmap: Bitmap): Bitmap {

        val smallest = min(bitmap.width, bitmap.height)

        val outputBmp = Bitmap.createBitmap(smallest, smallest, Config.ARGB_8888)

        val canvas = Canvas(outputBmp)



        val paint = Paint()

        val rect = Rect(0, 0, smallest, smallest)



        paint.isAntiAlias = true

        paint.isFilterBitmap = true

        paint.isDither = true

        canvas.drawARGB(0, 0, 0, 0)

        canvas.drawCircle(smallest / 2F, smallest / 2F, smallest / 2F, paint)



        paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)

        canvas.drawBitmap(bitmap, rect, rect, paint)



        return outputBmp

    }

}

