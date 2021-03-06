package com.abdulmanov.MoviCorn.common


import android.content.Context
import android.graphics.Point
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


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

fun ImageView.loadImg(imageUrl:String?,placeHolder: Int? = null,errorImage:Int?=null,callback: Callback?=null) {
    val picasso = Picasso.get().load(imageUrl)
    if(placeHolder!=null)
        picasso.placeholder(placeHolder)
    if(errorImage!=null)
        picasso.error(errorImage)
    picasso.fit().centerCrop(ImageView.TEXT_ALIGNMENT_CENTER)

    if(callback!=null)
        picasso.into(this,callback)
    else
        picasso.into(this)
}

fun ImageView.loadImg(imageUrl:Int,placeHolder: Int? = null,errorImage:Int?=null,callback: Callback?=null) {
    val picasso = Picasso.get().load(imageUrl)
    if (placeHolder != null)
        picasso.placeholder(placeHolder)
    if (errorImage != null)
        picasso.error(errorImage)
    picasso.fit().centerCrop(ImageView.TEXT_ALIGNMENT_CENTER)

    if (callback != null)
        picasso.into(this, callback)
    else
        picasso.into(this)
}


fun RecyclerView.initHorizontalRecyclerView(customAdapter: RecyclerView.Adapter<*>){
    ViewCompat.setNestedScrollingEnabled(this,false)
    layoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.HORIZONTAL,
        false
    )
    adapter = customAdapter
}

fun Context.getScreenSize():Point{
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return Point().apply {
        wm.defaultDisplay.getSize(this)
    }
}
