package com.eraorder.arena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eraorder.arena.core.di.ServiceLocator
import com.eraorder.arena.core.theme.AppTheme
import com.eraorder.arena.presentation.app.AppRoot

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ServiceLocator.init(applicationContext)
        setContent {
            AppTheme {
                AppRoot()
            }
        }
    }
}
