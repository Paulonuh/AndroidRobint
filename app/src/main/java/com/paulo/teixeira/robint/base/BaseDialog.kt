package com.paulo.teixeira.robint.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.paulo.teixeira.robint.R

open class BaseDialog : AppCompatDialogFragment() {

    enum class DialogAnimation {
        ANIMATION_FULL_SCALE, ANIMATION_HALF_SCALE
    }

    private var mAnimate = false
    private var mStartDialogAnimation = DialogAnimation.ANIMATION_FULL_SCALE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ThemeOverlay_AppCompat_Dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)

                window.setBackgroundDrawable(
                    android.graphics.drawable.ColorDrawable(
                        android.graphics.Color.TRANSPARENT
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (mAnimate) {
            showAnimation()
        }
    }

    private fun showAnimation() {
        if (dialog != null && dialog?.window != null) {
            val decorView = dialog?.window!!.decorView

            val endScaleX = 1.0f
            val endScaleY = 1.0f
            var startScaleX = 0.0f
            var startScaleY = 0.0f

            if (mStartDialogAnimation == DialogAnimation.ANIMATION_HALF_SCALE) {
                startScaleX = 0.8f
                startScaleY = 0.8f
            }

            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                decorView,
                PropertyValuesHolder.ofFloat("scaleX", startScaleX, endScaleX),
                PropertyValuesHolder.ofFloat("scaleY", startScaleY, endScaleY),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
            )
            scaleDown.duration = 400
            scaleDown.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAnimate) {
            hideAnimation()
        }
    }

    protected fun hideAnimation(
        animation: DialogAnimation? = DialogAnimation.ANIMATION_FULL_SCALE
    ) {
        if (dialog != null && dialog?.window != null) {

            val decorView = dialog?.window?.decorView

            var endScaleX = 0.0f
            var endScaleY = 0.0f
            val startScaleX = 1.0f
            val startScaleY = 1.0f

            if (animation == DialogAnimation.ANIMATION_HALF_SCALE) {
                endScaleX = 1.1f
                endScaleY = 1.1f
            }

            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                decorView,
                PropertyValuesHolder.ofFloat("scaleX", startScaleX, endScaleX),
                PropertyValuesHolder.ofFloat("scaleY", startScaleY, endScaleY),
                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f)
            )
            scaleDown.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    if (activity != null) {
                        dismiss()
                    }
                }

                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
            scaleDown.duration = 400
            scaleDown.start()
        }
    }

    fun setAnimate(animate: Boolean) {
        mAnimate = animate
    }

    fun setStartDialogAnimation(animation: DialogAnimation) {
        mStartDialogAnimation = animation
    }
}
