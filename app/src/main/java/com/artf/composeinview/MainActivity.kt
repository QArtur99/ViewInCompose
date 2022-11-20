package com.artf.composeinview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.artf.composeinview.databinding.ActivityMainBinding
import com.artf.composeinview.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AndroidViewBinding(ActivityMainBinding::inflate) {
                    val fragment = container.getFragment<MainFragment>()
                    //...
                }
            }
        }
    }
}