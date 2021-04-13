package com.paulo.teixeira.robint.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.paulo.teixeira.robint.R
import com.paulo.teixeira.robint.base.BaseFragment
import com.paulo.teixeira.robint.databinding.FragmentHomeBinding
import com.paulo.teixeira.robint.extension.*
import com.paulo.teixeira.robint.ui.menu.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val module = homeModule
    override val viewModel: HomeViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onInitView() {
        val navGraphs = listOf(
            R.navigation.forum_nav_graph,
            R.navigation.robint_nav_graph
        )

        binding.navBottom.setupWithNavController(
            navGraphIds = navGraphs,
            fragmentManager = childFragmentManager,
            containerId = R.id.navHostContainer
        )

        childFragmentManager.beginTransaction()
            .add(R.id.menuContainer, MenuFragment(), MenuFragment.MENU_TAG)
            .commit()
        onSideMenuUpdated(false)

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerClosed(drawerView: View) {
                onSideMenuUpdated(false)
            }

            override fun onDrawerOpened(drawerView: View) {
                onSideMenuUpdated(true)
            }
        })
    }

    override fun onInitObservers() {
        sharedViewModel.toLogout.observe(this, { toLogout ->
            if (toLogout)
                viewModel.logout()
        })

        viewModel.isLoggedOut.observe(this, { isLoggedOut ->
            if (isLoggedOut) ""// redirect to login

        })

        sharedViewModel.toOpenMenu.observe(this, { openMenu ->
            if (openMenu) {
                binding.drawerLayout.openDrawer(GravityCompat.START, true)
            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START, true)
            }
            onSideMenuUpdated(openMenu)
        })

        sharedViewModel.openOption.observe(this, Observer { option ->
            openMenuOption(option)
        })
    }

    override fun onFetchInitialData() {
    }

    override fun onError(message: Any) {}

    override fun onLoading(isLoading: Boolean) {
    }


    private fun openMenuOption(option: Int) {
        when (option) {
            MenuFragment.PROFILE_OPTION -> {
                val direction = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
                navigate(direction, TransitionAnimation.FADE, null, false)
            }
        }
    }

    private fun onSideMenuUpdated(opened: Boolean) {
        activity?.window?.apply {
            if (opened) {
                setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

}