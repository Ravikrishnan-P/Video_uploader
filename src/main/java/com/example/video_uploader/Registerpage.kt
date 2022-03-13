package com.example.video_uploader

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Registerpage : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val register: Button = findViewById(R.id.register_btn)

        fun createUser() {

            val user_email = email.text.toString().trim();
            val user_password = password.text.toString();
            if (user_email.isEmpty()) {
                Toast.makeText(this, "Email should not be empty", Toast.LENGTH_LONG).show()
            } else if (user_password.isEmpty()) {
                Toast.makeText(this, "Password should not be empty", Toast.LENGTH_LONG).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(user_email, user_password)
                    .addOnSuccessListener {
                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                        val user_email = firebaseUser?.email
                        Toast.makeText(this, "Registered Sucessfully", Toast.LENGTH_LONG).show()
                        val intent: Intent = Intent(this, Loginpage::class.java)
                        startActivity(intent)
                    }.addOnFailureListener() { e ->
                    Toast.makeText(this, "Register Failed due to ${e.message}", Toast.LENGTH_LONG)
                        .show()

                }

            }
        }


            register.setOnClickListener() {
                createUser()
            }




    }
}
