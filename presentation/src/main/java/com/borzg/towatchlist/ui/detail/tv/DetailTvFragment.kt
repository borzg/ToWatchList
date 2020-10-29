package com.borzg.towatchlist.ui.detail.tv

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.borzg.towatchlist.R

class DetailTvFragment : Fragment() {

    companion object {
        fun newInstance() = DetailTvFragment()
    }

    private lateinit var viewModel: DetailTvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_tv_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}