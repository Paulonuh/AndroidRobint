package com.paulo.teixeira.robint.extension

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.paulo.teixeira.robint.R
import com.paulo.teixeira.robint.widget.OnNavigationResult
import java.util.*

enum class TransitionAnimation {
    TRANSLATE_FROM_RIGHT, TRANSLATE_FROM_LEFT, TRANSLATE_FROM_DOWN, TRANSLATE_FROM_UP, NO_ANIMATION, FADE
}

fun isAtLeastMarshmallow(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

val Fragment.appCompatActivity get() = this.activity as? AppCompatActivity

fun Fragment.showMessage(message: Any) {
    when(message) {
        is Int -> {
            context?.toastLong(getString(message))
        }
        is CharSequence -> {
            context?.toastLong(message)
        }
        else -> {
            throw RuntimeException("Message must be Int or CharSequence.")
        }
    }
}

fun Fragment.hideKeyboard() {
    val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
}


fun Fragment.hasPermissionAndAsk(permissionRequestCode: Int, permissionsCheck: Array<String>): Boolean {
    val permissions = ArrayList<String>()

    permissionsCheck.forEach {
        if(context?.let {it1 -> ActivityCompat.checkSelfPermission(it1, it)} != PackageManager.PERMISSION_GRANTED) {
            permissions.add(it)
        }
    }

    if(permissions.isNotEmpty()) {
        val array = permissions.toTypedArray()
        requestPermissions(array, permissionRequestCode)
        return false
    }

    return true
}

fun Fragment.hasPermission(permissionsCheck: Array<String>): Boolean {
    return permissionsCheck.all {permission ->
        context?.let {context ->
            ActivityCompat.checkSelfPermission(context, permission)
        } != PackageManager.PERMISSION_GRANTED
    }
}

//region Navigation

//navigate to NavDirections, pass data with safeArgs, clearbackstack, and set a destination
fun Fragment.navigate(directions: NavDirections, animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT,
                      popUpTo: Int? = null, clearBackStack: Boolean? = false) {
    val navController = NavHostFragment.findNavController(this)
    val destinationId = if(clearBackStack == true) navController.graph.id else popUpTo
    val options = buildOptions(animation, clearBackStack, destinationId)

    navController.navigate(directions, options)
}


//navigate to resID, is possible to add bundle, clearbackstack and set a destination
fun Fragment.navigate(@IdRes resId: Int, bundle: Bundle? = null,
                      animation: TransitionAnimation? = TransitionAnimation.TRANSLATE_FROM_RIGHT, popUpTo: Int? = null,
                      clearBackStack: Boolean? = false) {
    val navController = NavHostFragment.findNavController(this)
    val destinationId = if(clearBackStack == true) navController.graph.id else popUpTo
    val options = buildOptions(animation, clearBackStack, destinationId)

    navController.navigate(resId, bundle, options)
}

fun <R> Fragment.navigateForResult(key: String, directions: NavDirections, owner: LifecycleOwner? = null,
                                   onNavigationResult: OnNavigationResult<R>) {
    setNavigationResultObserver(key = key, owner = owner, onNavigationResult = onNavigationResult)

    navigate(directions)
}

fun <R> Fragment.setNavigationResult(key: String, result: R, destinationId: Int? = null) {
    val navController = NavHostFragment.findNavController(this)
    val savedStateHandle = if(destinationId != null) navController.getBackStackEntry(destinationId).savedStateHandle
    else navController.previousBackStackEntry?.savedStateHandle

    savedStateHandle?.set(key, result)
}

fun <R> Fragment.setNavigationResultObserver(key: String, owner: LifecycleOwner? = null,
                                             onNavigationResult: OnNavigationResult<R>) {
    val lifecycleOwner = owner ?: this
    val navController = NavHostFragment.findNavController(this)
    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<R>(key)?.observe(lifecycleOwner) {result ->
        navController.currentBackStackEntry?.savedStateHandle?.remove<R>(key)
        onNavigationResult.invoke(result)
    }
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.popUpTo(@IdRes destinationId: Int) {
    findNavController().popBackStack(destinationId, false)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

private fun Fragment.buildOptions(transitionAnimation: TransitionAnimation?, clearBackStack: Boolean?, @IdRes destinationId: Int?): NavOptions {
    return navOptions {
        anim {
            when(transitionAnimation) {
                TransitionAnimation.TRANSLATE_FROM_RIGHT -> {
                    enter = R.anim.translate_left_enter
                    exit = R.anim.translate_left_exit
                    popEnter = R.anim.translate_right_enter
                    popExit = R.anim.translate_right_exit
                }
                TransitionAnimation.TRANSLATE_FROM_DOWN -> {
                    enter = R.anim.translate_slide_bottom_up
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_slide_bottom_down
                }
                TransitionAnimation.TRANSLATE_FROM_LEFT -> {
                    enter = R.anim.translate_right_enter
                    exit = R.anim.translate_right_exit
                    popEnter = R.anim.translate_left_enter
                    popExit = R.anim.translate_left_exit
                }
                TransitionAnimation.TRANSLATE_FROM_UP -> {
                    enter = R.anim.translate_slide_bottom_down
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_slide_bottom_up
                }
                TransitionAnimation.NO_ANIMATION -> {
                    enter = R.anim.translate_no_change
                    exit = R.anim.translate_no_change
                    popEnter = R.anim.translate_no_change
                    popExit = R.anim.translate_no_change
                }
                TransitionAnimation.FADE -> {
                    enter = R.anim.translate_fade_in
                    exit = R.anim.translate_fade_out
                    popEnter = R.anim.translate_fade_in
                    popExit = R.anim.translate_fade_out
                }
                else -> {
                }
            }
        }

        // To clean the stack below the current fragment,
        // you must set the 'destinationId' = navGraphId and 'inclusive' = true
        destinationId?.let {
            popUpTo(destinationId) {
                inclusive = clearBackStack == true
            }
        }
    }
}
//endregion Navigation