package com.paulo.teixeira.robint.ui.menu

import android.view.LayoutInflater
import com.paulo.teixeira.robint.base.BaseFragment
import com.paulo.teixeira.robint.base.BaseViewModel
import com.paulo.teixeira.robint.databinding.FragmentMenuBinding
import com.paulo.teixeira.robint.ui.home.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.module.Module


class MenuFragment : BaseFragment<FragmentMenuBinding>() {

    companion object {
        const val MENU_TAG = "menu_fragment"

        const val PROFILE_OPTION = 1
    }

    override val module: Module? = null
    override val viewModel: BaseViewModel? = null

    override val bindingInflater: (LayoutInflater) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    val sharedViewModel: SharedViewModel by sharedViewModel()

    //region override
    override fun onInitView() {
        binding.ivLogout.setOnClickListener {
            sharedViewModel.toLogout.postValue(true)
            sharedViewModel.toOpenMenu.postValue(false)
        }

        binding.ivBackButton.setOnClickListener {
            sharedViewModel.toOpenMenu.postValue(false)
        }

        binding.tvProfile.setOnClickListener {
            openMenuOption(PROFILE_OPTION)
        }
    }

    override fun onInitObservers() {
        sharedViewModel.openOption.observe(this, { option ->
            openMenuOption(option)
        })
    }

    override fun onFetchInitialData() {

    }

    override fun onError(message: Any) {
    }

    override fun onLoading(isLoading: Boolean) {
    }


    //endregion override
    private fun openMenuOption(optionValue: Int) {
        sharedViewModel.openOption.postValue(optionValue)
        sharedViewModel.toOpenMenu.postValue(false)
    }
}