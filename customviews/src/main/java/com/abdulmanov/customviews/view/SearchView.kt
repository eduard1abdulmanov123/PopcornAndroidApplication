package com.abdulmanov.customviews.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.abdulmanov.customviews.R
import com.abdulmanov.customviews.focus
import kotlinx.android.synthetic.main.search_view.view.*

class SearchView: CardView {

    private lateinit var searchButton: ImageButton
    private lateinit var backButton:ImageButton
    private lateinit var clearButton:ImageButton
    lateinit var editText: EditText

    private val clickSearchButton:()->Unit = {
        editText.focus(true)
    }

    private val clickBackButton:()->Unit = {
        editText.focus(false)
        showSearchView(false)
    }

    private val clickClearButton:()->Unit = {
        editText.text.clear()
    }

    private val clickEditor:()->Unit = {
        editText.focus(false)
    }

    private val onTextChanged:(s:CharSequence?)->Unit = {
        clearButton.visibility = if(it.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE

    }

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr){
        init()
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.SearchView)
        try{
            searchButton.setImageDrawable(typedArray.getDrawable(R.styleable.SearchView_icon_search))
            searchButton.background = typedArray.getDrawable(R.styleable.SearchView_background_button)
            backButton.setImageDrawable(typedArray.getDrawable(R.styleable.SearchView_icon_back))
            backButton.background = typedArray.getDrawable(R.styleable.SearchView_background_button)
            clearButton.setImageDrawable(typedArray.getDrawable(R.styleable.SearchView_icon_clear))
            clearButton.background = typedArray.getDrawable(R.styleable.SearchView_background_button)
            editText.setTextColor(
                typedArray.getColor(R.styleable.SearchView_text_color,
                    ContextCompat.getColor(context,android.R.color.black))
            )
        }finally {
            typedArray.recycle()
        }
    }

    private fun init(){
        val view = inflate(context,R.layout.search_view,this)
        searchButton = view.search_view_ic_search
        searchButton.setOnClickListener {
            clickSearchButton.invoke()
        }

        backButton = view.search_view_ic_back
        backButton.setOnClickListener {
            clickBackButton.invoke()
        }

        clearButton = view.search_view_ic_clear
        clearButton.setOnClickListener {
            clickClearButton.invoke()
        }

        editText = view.search_view_edit_text
        editText.setOnFocusChangeListener { _, focus ->
            if (focus) {
                showSearchView(true)
            }
        }
        editText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    clickEditor.invoke()
                    return true
                }
                return false
            }
        })

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged.invoke(p0)
            }
        })
    }

    private fun showSearchView(show:Boolean){
        if(show){
            searchButton.visibility = View.INVISIBLE
            backButton.visibility = View.VISIBLE
        }else{
            searchButton.visibility = View.VISIBLE
            backButton.visibility = View.INVISIBLE
            editText.text.clear()
        }
    }

    fun setSearchButtonClickListener(click:()->Unit){
        searchButton.setOnClickListener {
            clickSearchButton.invoke()
            click.invoke()
        }
    }

    fun setBackButtonClickListener(click:()->Unit){
        backButton.setOnClickListener {
            clickBackButton.invoke()
            click.invoke()
        }
    }

    fun setClearButtonClickListener(click:()->Unit){
        clearButton.setOnClickListener {
            clickClearButton.invoke()
            click.invoke()
        }
    }

    fun setOnEditorActionListener(click: () -> Unit){
        editText.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    clickEditor.invoke()
                    click.invoke()
                    return true
                }
                return false
            }
        })
    }

    fun addTextChangedListener(
        afterTextChanged:((s:Editable?)->Unit)?=null,
        beforeTextChanged:((s:CharSequence?, start:Int, count:Int, after:Int)->Unit)?=null,
        onTextChanged:((s:CharSequence?,start:Int,before:Int,count:Int)->Unit)?=null
    ){
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                afterTextChanged?.invoke(p0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                beforeTextChanged?.invoke(p0,p1,p2,p3)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                this@SearchView.onTextChanged.invoke(p0)
                onTextChanged?.invoke(p0,p1,p2,p3)
            }
        })
    }
}