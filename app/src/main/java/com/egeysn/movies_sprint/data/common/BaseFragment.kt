package com.naylalabs.kotlinbaseproject.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.egeysn.movies_sprint.utils.LoadingHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    lateinit var loadingHelper: LoadingHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingHelper = LoadingHelper.getInstance(requireActivity())
    }

    fun showLoading() {
        requireActivity().runOnUiThread {
            loadingHelper.showDialog()
        }
    }

    fun hideLoading() {
        requireActivity().runOnUiThread { loadingHelper.hideDialog() }
    }

}