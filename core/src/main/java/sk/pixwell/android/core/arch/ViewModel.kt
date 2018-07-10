package sk.pixwell.android.core.arch

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    factory: ViewModelProvider.Factory,
    owner: Fragment = this
) = ViewModelProviders.of(owner, factory).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    factory: ViewModelProvider.Factory,
    owner: AppCompatActivity
) = ViewModelProviders.of(owner, factory).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    owner: Fragment = this
) =
    ViewModelProviders.of(owner).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
    owner: Activity
) =
    ViewModelProviders.of(owner as FragmentActivity).get(T::class.java)

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(
    factory: ViewModelProvider.Factory,
    owner: AppCompatActivity = this
) = ViewModelProviders.of(owner, factory).get(T::class.java)

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel(
    owner: AppCompatActivity = this
) =
    ViewModelProviders.of(owner).get(T::class.java)
