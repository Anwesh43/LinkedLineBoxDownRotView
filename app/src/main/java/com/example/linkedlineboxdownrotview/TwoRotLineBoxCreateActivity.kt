package com.example.linkedlineboxdownrotview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tworotlineboxcreatorview.TwoRotLineBoxCreateView

class TwoRotLineBoxCreateActivity : AppCompatActivity() {

    override fun onCreate(sis : Bundle?) {
        super.onCreate(sis)
        TwoRotLineBoxCreateView.create(this)
        fullScreen()
    }
}