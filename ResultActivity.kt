package com.example.quziapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quziapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    //Result Activity
    private lateinit var  binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_result)
        //viewBindingを使用する
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //正解数を表示
        //getIntExtra(key, default_value)
        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)

        val evaluateLabel : String = when(score) {
            0, 1, 2, 3 -> "伸びしろが大きいです!"
            4, 5, 6 -> "満点まで残り半分!"
            7, 8, 9 -> "満点までもう一息!"
            else -> "全問正解です!!"
        }

        val encourageLabel : String = when(score) {
            0, 1, 2, 3 -> "もっと頑張りましょう!"
            4, 5, 6 -> "もう少し頑張りましょう!"
            7, 8, 9 -> "頑張ろう!!"
            else -> "素晴らしい!!"
        }

        //TextViewに正答率を表示
        binding.resultLabel.text = getString(R.string.result_score, score)

        //TextViewに評価を表示
        binding.evaluation.text = evaluateLabel

        //TextViewに励ましの言葉を表示
        binding.encourage.text = encourageLabel

        //[もう一度]ボタンを押すと、MainActivityへ
        binding.tryAgainBtn.setOnClickListener{
            startActivity(Intent(this@ResultActivity, firstActivity::class.java))
        }
    }
}