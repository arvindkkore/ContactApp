package com.test.contactapp.di.component

import com.test.contactapp.di.module.FragmentModule
import com.test.contactapp.di.scope.FragmentScope
import com.test.contactapp.presenter.view.ui.fragments.AboutFragment
import com.test.contactapp.presenter.view.ui.fragments.LinkFragment
import com.test.contactapp.presenter.view.ui.fragments.NoteFragment
import dagger.Component

@Component(dependencies = arrayOf(ActivityComponent::class) , modules = arrayOf(FragmentModule::class))
@FragmentScope
interface FragmentComponent {
   fun inject(aboutFragment: AboutFragment)
   fun inject(linkFragment: LinkFragment)
   fun inject(noteFragment: NoteFragment)

}