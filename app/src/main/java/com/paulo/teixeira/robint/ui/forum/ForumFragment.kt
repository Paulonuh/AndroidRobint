package com.paulo.teixeira.robint.ui.forum

import android.view.LayoutInflater
import com.paulo.teixeira.robint.base.BaseFragment
import com.paulo.teixeira.robint.databinding.FragmentForumBinding
import com.paulo.teixeira.robint.ui.home.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ForumFragment: BaseFragment<FragmentForumBinding>() {
    
    override val module = forumModule
    override val viewModel: ForumViewModel? by viewModel()

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

    override val bindingInflater: (LayoutInflater) -> FragmentForumBinding
        get() = FragmentForumBinding::inflate

}