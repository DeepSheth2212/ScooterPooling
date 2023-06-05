package com.example.scooterpooling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.scooterpooling.ModelClasses.User
import com.example.scooterpooling.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class SignUpActivity : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.Register.setOnClickListener {
            val name = binding.nameSU.text.toString()
            val email = binding.EmailSU.text.toString()
            val password = binding.PasswordSU.text.toString()

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"User Created Successfully!",Toast.LENGTH_SHORT).show()

                    val userId = auth.currentUser?.uid
                    val databaseRef = FirebaseDatabase.getInstance().reference
                    val userRef = databaseRef.child("Users")
                    val user = User(name,email)

                    userId?.let { it1 ->
                        userRef.child(it1).setValue(user).addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Log.d("auth","User Info saved successfully!")
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                            }
                            else{
                                Toast.makeText(this,"Please try again!",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, task.exception?.localizedMessage.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ToSI.setOnClickListener {
            startActivity(Intent(this , SignInActivity::class.java))
        }
    }
}