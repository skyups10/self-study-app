package com.example.guru

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.loginButton).setOnClickListener(onClickListener)
        findViewById<View>(R.id.gotoPasswordReset).setOnClickListener(onClickListener)
    }

    var onClickListener =
        View.OnClickListener { view ->
            when (view.id) {
                R.id.loginButton -> login()
                R.id.gotoPasswordReset -> mystartActivity(PasswordResetActivity::class.java)
            }
        }

    private fun login() {
        val email =
            (findViewById<View>(R.id.emailEditText) as EditText).text.toString()
        val password =
            (findViewById<View>(R.id.passwordEditText) as EditText).text
                .toString()
        if (email.length > 0 && password.length > 0) {
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth!!.currentUser
                        startToast("로그인에 성공하였습니다!")
                        mystartActivity(MainActivity::class.java)
                    } else {
                        if (task.exception != null) {
                            startToast("로그인 실패하였습니다")
                            //startToast(task.getException().toString());
                        }
                    }
                }
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요")
        }
    }

    private fun startToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun mystartActivity(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }
}