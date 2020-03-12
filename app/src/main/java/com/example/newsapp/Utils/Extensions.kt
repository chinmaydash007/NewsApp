package com.example.newsapp.Utils

import android.content.Context
import android.util.Log
import android.widget.Toast


fun Context.showtoast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(this, msg, duration).show()
}
fun log(msg:String){
    Log.d("mytag",msg)
}