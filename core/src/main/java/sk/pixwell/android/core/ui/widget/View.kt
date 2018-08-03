package sk.pixwell.android.core.ui.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.toBitmap(): Bitmap {
    measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    layout(0, 0, measuredWidth, measuredHeight)
    val bitmap = Bitmap.createBitmap(
        measuredWidth, measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
    background?.draw(canvas)
    draw(canvas)
    return bitmap
}

fun View.onGlobalLayout(onGlobalLayout: View.() -> Unit) {
    val view = this
    viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                onGlobalLayout(view)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    @Suppress("DEPRECATION")
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        }
    )
    requestLayout()
}