package com.omer.ilhanli.applike.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omer.ilhanli.applike.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).run { setContentView(root) }
    }
}