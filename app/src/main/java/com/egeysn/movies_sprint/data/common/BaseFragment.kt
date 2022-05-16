package com.egeysn.movies_sprint.common


import androidx.fragment.app.Fragment
import com.egeysn.movies_sprint.utils.LoadingHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var loadingHelper: LoadingHelper

    override fun onResume() {
        super.onResume()
        try {
            loadingHelper.register(requireContext())
        } catch (e: Exception) {
        }
    }
    fun showLoading() {
        loadingHelper.showDialog()
    }

    fun hideLoading() {
        loadingHelper.hideDialog()
    }
}
