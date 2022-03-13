package com.example.video_uploader

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
class MainActivity2 : AppCompatActivity() {
    @SuppressLint("NewApi")
    lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val ch_btn: Button = findViewById(R.id.ch_btn)
        val upl_btn: Button = findViewById(R.id.upl_btn)
        val dwn_btn: Button = findViewById(R.id.dwn_btn)
        val view_video: VideoView = findViewById(R.id.view_video)
        var video_uri: Uri? = null
        val vid_name : EditText = findViewById(R.id.vid_name)
        val camera: Button = findViewById(R.id.camera)



        dwn_btn.setOnClickListener(){
            val intent : Intent = Intent(this,MainActivity3::class.java)
            startActivity(intent)
        }

        val requestCamera = registerForActivityResult(ActivityResultContracts.RequestPermission(),{
            if (it)
            {
              Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }

        })

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult? ->
             if (result!!.resultCode==Activity.RESULT_OK)
             {
                 val video = result.data!!.data
                 view_video.setVideoURI(video)
                 video_uri = video

             }
        }



        val getaction = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->

                view_video.setVideoURI(uri)
                video_uri = uri


            })


        ch_btn.setOnClickListener {
            getaction.launch("video/*")


        }
        camera.setOnClickListener {
            requestCamera.launch(android.Manifest.permission.CAMERA)
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            activityResultLauncher.launch(intent)
        }

        val mediaController :MediaController = MediaController(this)
        mediaController.setAnchorView(view_video)
        view_video.setMediaController(mediaController)

        view_video.start()
        fun getfileextensions(videoUri: Uri): String = Unit.toString()


        upl_btn.setOnClickListener {

            if (video_uri!=null){
                val pd = ProgressDialog(this)
                pd.setTitle("Uploading")
                pd.show()

                val filename = vid_name.text.toString()
                val Video_ref : StorageReference = FirebaseStorage.getInstance().getReference("videos/$filename")


                Video_ref.putFile(video_uri!!)
                    .addOnSuccessListener{
                        Video_ref.downloadUrl.addOnSuccessListener (OnSuccessListener<Uri> { uri ->
                            val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-uploader-1e308-default-rtdb.firebaseio.com/")
                                .child(filename)
                            val hashmap : HashMap<String,Uri> = HashMap()
                            hashmap.put(filename,uri)
                            databaseReference.setValue(hashmap)
                            })
                        pd.dismiss()

                        Toast.makeText(this,"File Uploaded", Toast.LENGTH_LONG).show()

                    }
                    .addOnFailureListener {
                        pd.dismiss()
                        Toast.makeText(this,"Error Occured", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener { p0 ->
                        val progress : Double = (100.0 * p0.bytesTransferred)/p0.totalByteCount
                        pd.setMessage("Uploaded ${progress.toInt()} %")
                    }

            }


        }
        }






}


