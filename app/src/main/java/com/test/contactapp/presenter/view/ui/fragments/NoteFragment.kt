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
import com.test.contactapp.databinding.FragmentNoteBinding
import com.test.contactapp.di.component.DaggerFragmentComponent
import com.test.contactapp.di.component.FragmentComponent
import com.test.contactapp.presenter.view.ui.activity.ContactDetailActivity
import com.test.contactapp.presenter.view.viewmodel.AddEditViewContactViewModel
import com.test.contactapp.presenter.view.viewmodel.GlobalFViewModelFactory
import javax.inject.Inject


class NoteFragment : Fragment() {

    lateinit var binding: FragmentNoteBinding

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
            inflater, R.layout.fragment_note, container, false);
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentComponent.inject(this)
        viewModel = activity?.let {
            ViewModelProviders.of(it, factory).get(AddEditViewContactViewModel::class.java)
        }
        binding.viewModel=viewModel

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
