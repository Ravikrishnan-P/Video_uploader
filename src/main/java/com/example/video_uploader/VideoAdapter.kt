package com.example.video_uploader


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.example.video_uploader.VideoAdapter as VideoAdapter1


class VideoAdapter (private val videolist : ArrayList<Videodata>) : RecyclerView.Adapter<VideoAdapter1.Myviewholder> () {
    


    override fun getItemCount(): Int {
        return videolist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.video_adapter,parent,false)
        return Myviewholder(itemview)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        val currentitem = videolist[position]
        holder.name.text = currentitem.name

        holder.video.setVideoURI(currentitem.video_uri.toUri())
        val mediaController :MediaController = MediaController(holder.video.context)
        mediaController.setAnchorView(holder.video)
        holder.video.setMediaController(mediaController)
        holder.video.start()



    }

    class Myviewholder(itemview : View) : RecyclerView.ViewHolder(itemview){

        val video : VideoView = itemview.findViewById(R.id.adap_video)
        val name : TextView = itemview.findViewById(R.id.adap_vid_name)




    }
}