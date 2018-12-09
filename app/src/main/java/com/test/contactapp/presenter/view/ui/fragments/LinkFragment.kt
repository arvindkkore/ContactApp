package com.test.contactapp.presenter.view.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.test.contactapp.R
import com.test.contactapp.databinding.FragmentLinkBinding
import com.test.contactapp.di.component.DaggerFragmentComponent
import com.test.contactapp.di.component.FragmentComponent
import com.test.contactapp.presenter.view.ui.activity.ContactDetailActivity
import com.test.contactapp.presenter.view.viewmodel.AddEditViewContactViewModel
import com.test.contactapp.presenter.view.viewmodel.GlobalFViewModelFactory
import javax.inject.Inject


class LinkFragment : Fragment() {


    lateinit var binding: FragmentLinkBinding

    val fragmentComponent: FragmentComponent by lazy {
        DaggerFragmentComponent.builder().activityComponent((activity as ContactDetailActivity).activityComponent).build()
    }

    @Inject
    lateinit var factory: GlobalFViewModelFactory<AddEditViewContactViewModel>
    var viewModel: AddEditViewContactViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_link, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentComponent.inject(this)
        viewModel = activity?.let {
            ViewModelProviders.of(it, factory).get(AddEditViewContactViewModel::class.java)        }
        binding.viewModel=viewModel

        }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LinkFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
