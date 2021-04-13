package com.paulo.teixeira.robint.ui.profile

import android.view.LayoutInflater
import com.paulo.teixeira.robint.base.BaseFragment
import com.paulo.teixeira.robint.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    
    override val module = profileModule
    override val viewModel: ProfileViewModel? by viewModel()

    override fun onInitView() {
    }

    override fun onInitObservers() {
    }

    override fun onFetchInitialData() {
    }

    override fun onError(message: Any) {
    }

    override fun onLoading(isLoading: Boolean) {
    }

    override val bindingInflater: (LayoutInflater) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

}