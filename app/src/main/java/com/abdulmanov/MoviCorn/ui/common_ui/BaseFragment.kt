package com.abdulmanov.MoviCorn.ui.common_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class BaseFragment: Fragment() {

    companion object {
        private const val KEY_LAYOUT = "layout_key"
        private const val KEY_NAV_HOST = "nav_host_key"
        private const val KEY_FIRST_FRAGMENT = "first_fragment"
        fun newInstance(layoutRes: Int, navHostId: Int, firstFragment:String) = BaseFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_LAYOUT, layoutRes)
                putInt(KEY_NAV_HOST, navHostId)
                putString(KEY_FIRST_FRAGMENT,firstFragment)
            }
        }
    }

    private var layoutRes: Int = -1
    private var navHostId: Int = -1
    private var firstFragment:String = ""

    private val backStack = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(KEY_LAYOUT)
            navHostId = it.getInt(KEY_NAV_HOST)
            firstFragment = it.getString(KEY_FIRST_FRAGMENT)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutRes == -1) null
        else inflater.inflate(layoutRes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            startFragment(childFragmentManager.fragmentFactory.instantiate(context!!.classLoader,firstFragment))
        } else {
            childFragmentManager.fragments.forEach { backStack.add(it) }
        }
    }

    fun startFragment(fragment: Fragment){
        val transaction = childFragmentManager.beginTransaction()
        if(backStack.isNotEmpty()){
            transaction.hide(backStack.last())
        }
        transaction.add(navHostId,fragment)
        backStack.add(fragment)
        transaction.commitNow()
    }

    //возвращение назад, если открыт фрагмент
    fun onBackPressed(): Boolean {
        return if(backStack.size>1){
            val transaction = childFragmentManager.beginTransaction()
            transaction.remove(backStack.last())
            backStack.remove(backStack.last())
            transaction.show(backStack.last())
            transaction.commitNow()
            true
        }else{
            false
        }
    }

    //Возвращение к корню
    fun popToRoot() {
        if(backStack.size>1){
            val transaction = childFragmentManager.beginTransaction()
            for(i in backStack.size-1 downTo 1){
                transaction.remove(backStack[i])
                backStack.remove(backStack[i])
            }
            transaction.show(backStack.last())
            transaction.commitNow()
        }
    }
}