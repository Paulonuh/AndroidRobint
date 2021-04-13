package com.paulo.teixeira.robint.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.paulo.teixeira.robint.databinding.FragmentHomeNavigationBinding


class NavigationView : ConstraintLayout {

    private var mSelectedViewId: Int = 0

    private var mContainer: ViewGroup? = null
    private var mOnNavigationItemSelected: OnNavigationItemSelectedListener? = null

    private lateinit var binding: FragmentHomeNavigationBinding

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            binding =
                FragmentHomeNavigationBinding.inflate(LayoutInflater.from(context), this, true)
            val navContainer = binding.root as ViewGroup

            mContainer = navContainer//.getChildAt(0) as ViewGroup
            (mContainer?.getChildAt(0) as? ViewGroup)?.getChildAt(0)?.id?.let { id ->
                mSelectedViewId = id
                changeViewTint(mContainer)
            }
            setChildrenListener(mContainer)
        }
    }

    private fun setChildrenListener(container: ViewGroup?) {
        container?.let { cont ->
            if (cont.childCount > 0 && cont.getChildAt(0) is ViewGroup) {
                setChildrenListener(cont.getChildAt(0) as ViewGroup)
            } else {
                for (i in 0 until cont.childCount) {
                    val child = cont.getChildAt(i)
                    child?.setOnClickListener { this.onClick(it) }
                }
            }
        }
    }

    fun onClick(view: View) {
        mSelectedViewId = view.id

        mOnNavigationItemSelected?.onNavigationItemSelected(view)

        changeViewTint(mContainer)
    }

    private fun changeViewTint(container: ViewGroup?) {
        container?.let { cont ->
            if (cont.childCount > 0 && cont.getChildAt(0) is ViewGroup) {
                changeViewTint(cont.getChildAt(0) as ViewGroup)
            } else {
                for (i in 0 until cont.childCount) {
                    val child = cont.getChildAt(i)

                    child?.let {
                        if (child is AppCompatTextView) {
                            if (child.id == mSelectedViewId) {
                                child.isSelected = true
                                child.compoundDrawablePadding = 16
                            } else {
                                child.isSelected = false
                                child.compoundDrawablePadding = 10
                            }

                        }
                    }
                }
            }
        }
    }

    fun setOnNavigationItemSelected(onNavigationItemSelected: OnNavigationItemSelectedListener) {
        this.mOnNavigationItemSelected = onNavigationItemSelected
    }

    fun setSelectedItemId(itemId: Int) {
        mSelectedViewId = itemId
        changeViewTint(mContainer)

        if (mContainer != null) {
            mOnNavigationItemSelected.let {
                it?.onNavigationItemSelected(mContainer!!.findViewById(itemId))
            }
        }
    }

    val selectedViewId get() = mSelectedViewId

    interface OnNavigationItemSelectedListener {

        fun onNavigationItemSelected(item: View)
    }
}