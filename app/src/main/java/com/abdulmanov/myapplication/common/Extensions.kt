package com.abdulmanov.myapplication.common


import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import com.abdulmanov.myapplication.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


fun FragmentManager.hideFragment(tag:String){
    val fragment = findFragmentByTag(tag)
    if (fragment != null) {
        beginTransaction().hide(fragment).commitNow()
    }
}

fun FragmentManager.showFragment(tag:String, hostFragment:Int, newFragment: Fragment){
    val fragment = findFragmentByTag(tag)
    val transaction = beginTransaction()
    if(fragment!=null){
        transaction.show(fragment)
    } else {
        transaction.add(hostFragment, newFragment, tag)
    }
    transaction.commitNow()
}

fun ViewGroup.inflate(layoutId:Int, attachToRoot:Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl:String?,imageEmpty: Int = R.color.color_background_image) {
    if(!imageUrl.isNullOrEmpty()) {
        Picasso.get()
            .load(imageUrl)
            .error(R.drawable.error_image)
            .fit()
            .centerCrop(ImageView.TEXT_ALIGNMENT_CENTER)
            .into(this)
    }else{
        Picasso.get()
            .load(imageEmpty)
            .fit()
            .centerCrop(ImageView.TEXT_ALIGNMENT_CENTER)
            .into(this)
    }
}

fun Context.hideKeyboard(view:View){
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken,0)
}

fun Context.showKeyboard(){
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun EditText.focus(show:Boolean){
    if(show){
        requestFocus()
        context.showKeyboard()
    }else{
        clearFocus()
        context.hideKeyboard(this)
    }
}
