package com.example.video_uploader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nxt_pg_btn : Button = findViewById(R.id.nxt_pg_btn)
          nxt_pg_btn.setOnClickListener {
          val intent :Intent  = Intent(this,Loginpage::class.java)
          startActivity(intent)
        }


    }
}