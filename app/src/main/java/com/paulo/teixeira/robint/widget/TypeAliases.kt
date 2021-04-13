package com.paulo.teixeira.robint.widget

import android.view.View

typealias OnNavigationResult<R> = ((result: R) -> Unit)
typealias OnItemClickListener<T> = ((View, Int, T) -> Unit)