package com.example.weightloss_pathway_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class FireBase : AppCompatActivity() {
    private var signInRequestCode : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Chat"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire_base)

        if(FirebaseAuth.getInstance().currentUser == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(),
                signInRequestCode
            )
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                "Welcome " + FirebaseAuth.getInstance()
                    .currentUser
                    ?.displayName,
                Toast.LENGTH_LONG)
                .show()

            // Load chat room contents
            displayChatMessages()
        }
    }

    private fun displayChatMessages() {
        TODO("Not yet implemented")
    }


}