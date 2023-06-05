package com.example.scooterpooling

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scooterpooling.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        if(auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }




        binding.SignIn.setOnClickListener {
            val email = binding.EmailSI.text.toString()
            val password = binding.PasswordSI.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"User Logged In!",Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        startActivity(Intent(this,MainActivity::class.java))
                        finishAffinity()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Sign In failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.ToSU.setOnClickListener {
            startActivity(Intent(this , SignUpActivity::class.java))
        }



    }
}