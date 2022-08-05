package com.example.guru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class Login_start_view : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_start_view)


////
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.gotoLoginButton).setOnClickListener(onClickListener)
        findViewById<View>(R.id.signUpBotton).setOnClickListener(onClickListener)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Process.killProcess(Process.myPid())
        System.exit(1)
    }

    var onClickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.gotoLoginButton -> mystartActivity(LoginActivity::class.java)
                R.id.signUpBotton -> mystartActivity(SignUpActivity::class.java)
            }
        }

    private fun mystartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }





}