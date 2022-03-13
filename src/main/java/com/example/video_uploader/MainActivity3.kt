package com.example.video_uploader

import android.app.ProgressDialog
import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.*
import java.util.jar.Manifest

class MainActivity3 : AppCompatActivity() {
    lateinit var context : Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val getvid: VideoView = findViewById(R.id.getvid)
        val getvidname : EditText = findViewById(R.id.getvidname)
        val getdwn : Button = findViewById(R.id.getdwn)
        context = applicationContext

        getdwn.setOnClickListener {
            val progressDialog : ProgressDialog = ProgressDialog(this)
            progressDialog.setMessage("Downloading...")
            progressDialog.setCancelable(false)
            progressDialog.show()
            val vidname = getvidname.text.toString()
            val storageref : StorageReference = FirebaseStorage.getInstance().reference.child("videos/$vidname")
            val localfile : File = File.createTempFile("$vidname",".mp4")
            storageref.getFile(localfile).addOnSuccessListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                val mediaController: MediaController = MediaController(this)
                mediaController.setAnchorView(getvid)
                getvid.setMediaController(mediaController)
                val vidasuri: Uri = localfile.toUri()
                getvid.setVideoURI(vidasuri)
                getvid.start()
                fun isExternalStorageWritable(): Boolean {
                    Boolean

                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

                        Log.i("State", "Yes, it is writable!")

                        return true

                    } else {

                        return false

                    }
                }
                if (isExternalStorageWritable()) run {
                    val videofile: File =
                        File(Environment.getExternalStorageState(), "$vidname.mp4")
                    try {
                        val fos: FileOutputStream = FileOutputStream(videofile)
                        fos.write(vidasuri.toString().toByteArray())
                        fos.close()
                        Toast.makeText(this, "File Saved", Toast.LENGTH_SHORT).show()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(this, "Cannot save the file", Toast.LENGTH_SHORT).show()
                }

                    fun checkPermission (permission : String): Boolean {

                    val check : Int = ContextCompat.checkSelfPermission( this, permission)

                    return (check == PackageManager.PERMISSION_GRANTED)
                    }


            }.addOnFailureListener{
                if (progressDialog.isShowing){
                    progressDialog.dismiss()
                }
                Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show()
            }
        }




    }
}







