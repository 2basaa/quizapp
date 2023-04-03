package com.example.quziapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.quziapplication.databinding.ActivityFirstBinding

class firstActivity : AppCompatActivity() {
    //first Acitivity
    private lateinit var  binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_first)
        //viewBindingを使用する
        binding = ActivityFirstBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //[START]ボタンの処理
        binding.startBtn.setOnClickListener {
            startActivity(Intent(this@firstActivity, MainActivity::class.java))
        }
    }
}