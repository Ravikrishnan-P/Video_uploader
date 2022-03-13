package com.example.video_uploader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity4 : AppCompatActivity() {
    lateinit var context: Context
    lateinit var newrecylerview : RecyclerView
    lateinit var newarraylist : ArrayList<Videodata>
    lateinit var video_id : Array<String>
    lateinit var vid_name : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        //Button upl_btl  = new Button() ;


        val upl_btn : Button = findViewById(R.id.upl_btn)
        val dwn_btn : Button = findViewById(R.id.dwn_btn)
        newrecylerview = findViewById(R.id.recycle)

        upl_btn.setOnClickListener {
            val intent1 : Intent = Intent(this,MainActivity2::class.java)
            startActivity(intent1)
        }

        dwn_btn.setOnClickListener {
            val intent2 : Intent = Intent(this,MainActivity3::class.java)
            startActivity(intent2)
        }

        vid_name = arrayOf(
            "Trial007",
            "Trial009",
            "Hurt You",
            "Spiderman",
            "Smile",

        )

        video_id = arrayOf(
            "https://firebasestorage.googleapis.com/v0/b/video-uploader-1e308.appspot.com/o/videos%2FTrial007?alt=media&token=0e541d5b-ac51-4833-87d8-6564e648e3d8",
            "https://firebasestorage.googleapis.com/v0/b/video-uploader-1e308.appspot.com/o/videos%2FTrial009?alt=media&token=d5faee55-5022-4520-ac21-9011dc259b96",
            "https://firebasestorage.googleapis.com/v0/b/video-uploader-1e308.appspot.com/o/videos%2Fhurt%20you?alt=media&token=6eb5ae5f-b50e-4587-8fb7-eefe20243be9",
            "https://firebasestorage.googleapis.com/v0/b/video-uploader-1e308.appspot.com/o/videos%2FSpiderman?alt=media&token=d6fd4a5d-a550-4b21-a7e0-56ab77ab0b34",
            "https://firebasestorage.googleapis.com/v0/b/video-uploader-1e308.appspot.com/o/videos%2FSmile?alt=media&token=54319923-88f0-4ceb-a8b2-a5f9c3417893"

        )


        newrecylerview.layoutManager = LinearLayoutManager(this)
        newrecylerview.setHasFixedSize(true)

        newarraylist = arrayListOf<Videodata>()
        getuserdata()

    }

    private fun getuserdata() {
        for (i in video_id.indices)
        {
            val videos = Videodata(vid_name[i], video_id[i])
            newarraylist.add(videos)
        }
        newrecylerview.adapter = VideoAdapter(newarraylist)
    }

}


