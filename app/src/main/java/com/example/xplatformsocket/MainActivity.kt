package com.example.xplatformsocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.socket.MessagesInterceptor
import com.example.createApplicationScreenMessage
import com.example.doSth
import com.example.socket.handlers.PriceMessageHandler
import com.example.socket.handlers.TimeMessageHandler
import kotlinx.android.synthetic.main.activity_main.*
import model.response.PriceResponse

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secondary_text.text = createApplicationScreenMessage()
        doSth {
            findViewById<TextView>(R.id.main_text).text = it
        }


        val st = SocketThing()
        st.connect()

        st.requestTime(object : TimeMessageHandler.OnTimeMessageListener {
            override fun onTimeMessage(time: String) {
                runOnUiThread {
                    findViewById<TextView>(R.id.main_text).text = time
                }
            }
        })

        st.requestPrice(object : PriceMessageHandler.OnPriceMessageListener {
            override fun onPriceUpdate(time: PriceResponse) {
                Log.d("Socket", time.toString())
                runOnUiThread {
                    findViewById<TextView>(R.id.secondary_text).text = time.message
                }
            }
        })

    }
}
