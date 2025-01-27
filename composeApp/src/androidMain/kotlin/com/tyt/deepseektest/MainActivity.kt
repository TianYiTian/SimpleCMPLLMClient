package com.tyt.deepseektest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.tooling.preview.Preview
import com.tyt.deepseektest.utils.SimpleHolder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val callback = rememberUpdatedState(SimpleHolder { false })
            onBackPressedDispatcher.addCallback(this) {
                if (callback.value.data()){
                    //donothing
                }else {
                    finish()
                }
            }
            App(callback)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val callback = rememberUpdatedState(SimpleHolder { false })
    App(callback)
}