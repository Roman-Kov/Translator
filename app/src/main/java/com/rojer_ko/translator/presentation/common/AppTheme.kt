package com.rojer_ko.translator.presentation.common

import com.rojer_ko.translator.R

fun getMyTheme(isCustomAppBar : Boolean): Int {

    return if(isCustomAppBar){
        R.style.AppTheme_NoActionBarMyTheme
        }
    else{
        R.style.AppTheme_MyTheme
        }
}