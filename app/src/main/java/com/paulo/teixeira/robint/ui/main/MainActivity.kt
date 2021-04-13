package com.paulo.teixeira.robint.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.paulo.teixeira.robint.R
import com.paulo.teixeira.robint.databinding.ActivityMainBinding
import com.paulo.teixeira.robint.ui.home.SharedViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}