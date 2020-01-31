package com.example.xplatformsocket

import TimeResponse
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.createApplicationScreenMessage
import com.example.doSth
import com.example.socket.SomeConnection
import kotlinx.android.synthetic.main.activity_main.*
import model.response.PriceResponse
import java.sql.Time

import com.example.socket.features.Time.TimeUpdateListener

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

        val connection = SomeConnection(st)

        connection.Price.requestPriceUpdate(object : Price.PriceUpdateListener{
            override fun onPriceUpdate(priceResponse: PriceResponse) {
                runOnUiThread {
                    main_text.text = priceResponse.message
                }
            }
        })

        secondary_text.setOnClickListener {
            connection.Time.requestTimeUpdate(object : TimeUpdateListener {
                override fun onPriceUpdate(timeResponse: TimeResponse) {
                    runOnUiThread {
                        secondary_text.text = timeResponse.time
                    }
                }
            })
        }
    }
}
