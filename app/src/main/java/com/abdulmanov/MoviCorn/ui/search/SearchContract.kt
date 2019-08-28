package com.abdulmanov.MoviCorn.ui.search

interface SearchContract{

    interface View{
        fun performSearch(key:String?)
    }
}