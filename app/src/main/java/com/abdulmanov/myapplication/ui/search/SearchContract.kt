package com.abdulmanov.myapplication.ui.search

interface SearchContract{

    interface View{
        fun showSearchView(show:Boolean)

        fun performSearch(key:String?)
    }
}