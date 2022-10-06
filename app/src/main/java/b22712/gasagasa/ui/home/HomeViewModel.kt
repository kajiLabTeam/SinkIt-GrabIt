package b22712.gasagasa.ui.home

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import b22712.gasagasa.MainApplication
import b22712.gasagasa.ui.basePressure.BasePressureFragment

class HomeViewModel(application: MainApplication) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun viewBasePressureFragment(fragment: Fragment, layoutId: Int) {
        val transaction: FragmentTransaction = fragment.parentFragmentManager.beginTransaction()
        transaction.replace(layoutId, BasePressureFragment.newInstance())
        transaction.addToBackStack(null)
        transaction.commit()
    }



}