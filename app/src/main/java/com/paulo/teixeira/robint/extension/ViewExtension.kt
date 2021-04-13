package com.paulo.teixeira.robint.extension

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.paulo.teixeira.robint.R

fun Context.toastLong(resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

fun Context.toastLong(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()


//view
fun AppCompatImageView?.loadImageRounded(urlImage: Bitmap?) {
    this?.let {
        urlImage?.let { url ->

            Glide.with(it.context).load(url).apply(
                RequestOptions.circleCropTransform()
                    .centerCrop()
                    .optionalCircleCrop()
            ).into(it)
        }
    }
}
fun AppCompatImageView?.loadImageRounded(urlImage: String?) {
    this?.let {
        urlImage?.let { url ->

            Glide.with(it.context).load(url).apply(
                RequestOptions.circleCropTransform()
                    .centerCrop()
                    .optionalCircleCrop()
            ).into(it)
        }
    }
}
fun AppCompatImageView?.loadImageRounded(urlImage: Int?) {
    this?.let {
        urlImage?.let { url ->

            Glide.with(it.context).load(url).apply(
                RequestOptions.circleCropTransform()
                    .centerCrop()
                    .optionalCircleCrop()
            ).into(it)
        }
    }
}

fun View?.showKeyboard() {
    this?.let {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

fun View?.hideKeyboard() {
    this?.let {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

fun Context?.showDialogChoice(
    @StringRes titleRes: Int,
    items: List<String?>,
    listener: DialogInterface.OnClickListener
) {
    this?.let {
        val adapter = ArrayAdapter(
            it,
            R.layout.simple_list_item_1,
            items.toTypedArray()
        )

        AlertDialog.Builder(it)
            .setTitle(titleRes)
            .setAdapter(adapter, listener)
            .create()
            .show()
    }
}

fun AppCompatTextView?.setDrawableEnd(@DrawableRes drawableRes: Int?) {
    this?.let {
        val drawable = if (drawableRes == null)
            null
        else
            ContextCompat.getDrawable(context, drawableRes)

        it.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            drawable,
            null
        )
    }
}

fun AppCompatEditText?.setDrawableEnd(@DrawableRes drawableRes: Int?) {
    this?.let {
        val drawable = if (drawableRes == null)
            null
        else
            ContextCompat.getDrawable(context, drawableRes)

        it.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            drawable,
            null
        )
    }
}