package com.example.guru

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.sendButton).setOnClickListener(onClickListener)
    }

    var onClickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.sendButton -> send()
            }
        }

    private fun send() {
        val email =
            (findViewById<View>(R.id.emailEditText) as EditText).text.toString()
        if (email.length > 0) {
            mAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startToast("이메일을 보냈습니다")
                    }
                }
        } else {
            startToast("이메일을 입력해주세요")
        }
    }

    private fun startToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}