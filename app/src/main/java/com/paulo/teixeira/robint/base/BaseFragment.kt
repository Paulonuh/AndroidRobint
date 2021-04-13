package com.paulo.teixeira.robint.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.paulo.teixeira.robint.R
import com.paulo.teixeira.robint.extension.TransitionAnimation
import com.paulo.teixeira.robint.extension.hideKeyboard
import com.paulo.teixeira.robint.extension.navigate
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.net.HttpURLConnection

abstract class BaseFragment<VB : ViewBinding> : Fragment(), BaseContract.View {

    private var mIsLayoutCreated = false
    private var mLayoutView: View? = null

    private var mViewBinding: ViewBinding? = null
    private var mBaseViewModel: BaseViewModel? = null

    private val mHandler: Handler by lazy { Handler() }

    abstract val bindingInflater: (LayoutInflater) -> VB

    private var toolBar: Toolbar? = null
    private var toolBarTitle: Int = R.string.clear
    private var toolBarIcon: Int = -1
    private var displayHome: Boolean = true
    private var isRounded: Boolean = false
    private var appBarLayout: AppBarLayout? = null
    private var isBackButton: Boolean = false

    private lateinit var callback: OnBackPressedCallback

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = mViewBinding as VB

    open fun onBackButtonPressed(callback: OnBackPressedCallback) {
        callback.isEnabled = false
        activity?.onBackPressed()
    }

    open fun getHandler() = mHandler

    //region Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        loadModule()
        super.onCreate(savedInstanceState)

        mBaseViewModel = viewModel

        initDefaultObservers()
        onInitObservers()

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackButtonPressed(this)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mLayoutView == null) {
            mViewBinding = bindingInflater.invoke(inflater)
            mLayoutView = requireNotNull(mViewBinding).root
        } else {
            (mLayoutView?.parent as? ViewGroup)?.removeView(mLayoutView)
        }
        hideKeyboard()
        return mLayoutView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!mIsLayoutCreated) {
            onInitView()
            onFetchInitialData()
            mIsLayoutCreated = true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }
    //endregion Fragment

    //region Local
    private fun initDefaultObservers() {
        mBaseViewModel?.redirect?.observe(this, { code ->
            activity?.let {
                when (code) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        navigate(
                            resId = R.id.homeFragment, animation = TransitionAnimation.FADE,
                            clearBackStack = true
                        )
                    }
                }
            }
        })
        mBaseViewModel?.loading?.observe(this, { isLoading ->
            onLoading(isLoading)
        })
        mBaseViewModel?.message?.observe(this, { message ->
            when (message) {
                is String -> {
                    onError(message)
                }
                is Int -> {
                    context?.getString(message)?.let { onError(it) }
                }
                else -> {
                    context?.getString(R.string.failed_server)?.let { onError(it) }
                }
            }

        })
    }

    private fun loadModule() {
        try {
            module?.let {
                unloadKoinModules(it)
                loadKoinModules(it)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    //region StatusBar
    fun changeStatusBarIconsColor(toWhite: Boolean) {
        activity?.let {
            it.window.decorView.systemUiVisibility = if (toWhite) {
                0
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
    //endregion StatusBar

    //region appbar/toolbar
    fun toolbar(toolBar: Toolbar?): BaseFragment<VB> {
        this.toolBar = toolBar
        return this
    }

    fun title(title: Int): BaseFragment<VB> {
        this.toolBarTitle = title
        return this
    }

    fun icon(@DrawableRes icon: Int): BaseFragment<VB> {
        this.toolBarIcon = icon
        return this
    }

    fun displayHome(displayHome: Boolean): BaseFragment<VB> {
        this.displayHome = displayHome
        return this
    }

    fun homeIsBackButton(isBackButton: Boolean): BaseFragment<VB> {
        this.isBackButton = isBackButton
        return this
    }

    private fun redirectTo(@IdRes destination: Int) {
        navigate(resId = destination, animation = TransitionAnimation.FADE)
    }
}