package com.paulo.teixeira.robint.ui.robint

import android.view.LayoutInflater
import com.paulo.teixeira.robint.base.BaseFragment
import com.paulo.teixeira.robint.databinding.FragmentRobintBinding
import com.paulo.teixeira.robint.ui.home.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RobintFragment: BaseFragment<FragmentRobintBinding>() {
    
    override val module = robintModule
    override val viewModel: RobintViewModel? by viewModel()

    private val sharedViewModel: SharedViewModel by sharedViewModel()



    override fun onInitView() {
        binding.ivMenu.setOnClickListener {
            sharedViewModel.toOpenMenu.postValue(true)
        }
    }

    override fun onInitObservers() {
    }

    override fun onFetchInitialData() {
    }

    override fun onError(message: Any) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override val bindingInflater: (LayoutInflater) -> FragmentRobintBinding
        get() = FragmentRobintBinding::inflate

}