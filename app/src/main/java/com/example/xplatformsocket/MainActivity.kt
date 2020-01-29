package com.example.xplatformsocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.createApplicationScreenMessage
import com.example.doSth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secondary_text.text = createApplicationScreenMessage()
        doSth{
            findViewById<TextView>(R.id.main_text).text = it
        }
    }
}
