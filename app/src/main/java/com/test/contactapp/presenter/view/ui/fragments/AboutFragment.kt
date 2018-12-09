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
import com.test.contactapp.databinding.FragmentAboutBinding
import com.test.contactapp.di.component.DaggerFragmentComponent
import com.test.contactapp.di.component.FragmentComponent
import com.test.contactapp.presenter.view.ui.activity.ContactDetailActivity
import com.test.contactapp.presenter.view.viewmodel.AddEditViewContactViewModel
import com.test.contactapp.presenter.view.viewmodel.GlobalFViewModelFactory
import javax.inject.Inject


class AboutFragment : Fragment() {

    lateinit var binding:FragmentAboutBinding

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
            inflater, R.layout.fragment_about, container, false);
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentComponent.inject(this)
        viewModel = activity?.let{
            ViewModelProviders.of(it, factory).get(AddEditViewContactViewModel::class.java)
        }
        binding.viewModel=viewModel

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {

                }
            }
        val TAG = "AboutFragment"
    }
}
