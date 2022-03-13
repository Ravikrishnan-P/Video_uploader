package com.example.video_uploader

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class Loginpage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val email: EditText = findViewById(R.id.email)

        val password: EditText = findViewById(R.id.password)
        password.text.toString().trim()
        val login: Button = findViewById(R.id.login_btn)
        val regis_nav : TextView = findViewById(R.id.regis_nav)

        regis_nav.setOnClickListener(){
            val intent22 : Intent = Intent(this,Registerpage::class.java)
            startActivity(intent22)
        }


        fun validatedata() {

            val user_email = email.text.toString().trim();
            val user_password = password.text.toString();
            if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                email.error = "Invalid Email Format"
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(user_email,user_password).addOnSuccessListener()
            {
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                //val user_email : String? = firebaseUser!!.email
                Toast.makeText(this,"Logged in Sucessfully", Toast.LENGTH_SHORT).show()
                val intent55 : Intent = Intent(this,MainActivity4::class.java)
                startActivity(intent55)
            }.addOnFailureListener(){
                Toast.makeText(this,"Please check your credinals", Toast.LENGTH_SHORT).show()
            }
        }

        login.setOnClickListener(){
            validatedata()
        }


    }


}
