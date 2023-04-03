package com.example.quziapplication

//viewBindingを使用するためのコード
import android.content.Intent
import com.example.quziapplication.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //rightAnswer は正しい答え
    //rightAnswerCountは正当数
    //quizCountは現在の問題数
    //totalQuizはクイズの総数
    private var rightAnswer: String? = null
    private var rightAnswerCount: Int = 0
    private  var quizCount: Int = 1
    private val totalQuiz: Int = 10

    //↓で変更可能なリストを作成(二重リストを作成)
    //mutableListで選択肢、出題順序のシャッフルが簡単となる
    private val quizData = mutableListOf(
        mutableListOf("しゃっくりを止めるための調味料は何でしょうか?", "お酢", "砂糖", "醤油", "塩"),//砂糖
        mutableListOf("羊羹はあるものを煮込んだスープです。あるスープとは何でしょうか?", "牛の肉", "羊の肉", "羊乳", "牛乳"),//羊の肉
        mutableListOf("ドジョウは人間と同じようにあることをします。それは何でしょうか?", "あくび", "くしゃみ", "まばたき", "おなら"),//くしゃみ
        mutableListOf("ナメクジは塩で溶けますが、他にもある調味料で溶けます。その調味料は何でしょうか?", "醤油", "砂糖", "小麦粉", "片栗粉"),//砂糖
        mutableListOf("長野県のりんごの収穫量はおよそどのくらいでしょうか?", "5000トン", "150000トン", "15000トン", "500000トン"),//150000
        mutableListOf("植物にも人間と同じようにあるものは何でしょうか?", "脳みそ", "血液型", "心臓", "髪の毛"),//血液型
        mutableListOf("4つ葉のクローバに似ている形の物は何でしょうか?", "風車", "十字架", "ハート", "心臓"),//十字架
        mutableListOf("飛行機の中で食べるように作られた野菜は何でしょうか?", "パプリカ", "ミニトマト", "アボカド", "ズッキーニ"),//ミニトマト
        mutableListOf("大根おろしが辛くなるすり方は何でしょうか?", "ゆっくりする", "早くする", "力を込めてする", "力を弱めてする"),//早く
        mutableListOf("日本で最初に販売されたアイスクリームの値段はいくらでしょうか?", "300円", "8000円", "1500円", "10000円")//ans=8000
    )

    //画像データの配列を作成する
    private val img = intArrayOf(
        R.drawable.hiccup, R.drawable.yokan, R.drawable.loach, R.drawable.slug, R.drawable.apple,
        R.drawable.plant, R.drawable.clover, R.drawable.plane, R.drawable.radish, R.drawable.ice
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /*下のコードでviewBindingを使用することができる*/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //問題をシャッフルで出題
        quizData.shuffle()
        showNextQuiz()
    }

    //クイズの出題
    fun showNextQuiz() {
        //カウントラベルの更新
        binding.countLabel.text = getString(R.string.count_label, quizCount)
        //クイズを1問取り出す(1番上)
        val quiz = quizData[0]

        //問題をセット
        binding.questionLabel.text = quiz[0]

        //画像のIdを決定する
        val imgId = when(quiz[1]) {
            "お酢" -> img[0]
            "牛の肉" -> img[1]
            "あくび" -> img[2]
            "醤油" -> img[3]
            "5000トン" -> img[4]
            "脳みそ" -> img[5]
            "風車" -> img[6]
            "パプリカ" -> img[7]
            "ゆっくりする" -> img[8]
            else -> img[9]
        }
        //画像をセット
        binding.questionImage.setImageResource(imgId)

        //正解をセット, quiz[2]が正解となる
        rightAnswer = quiz[2]

        //問題を削除, removeAtでリストの要素を削除
        quiz.removeAt(0)

        //正解と選択肢をシャッフル
        quiz.shuffle()

        //選択肢をセット
        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]

        //出題したクイズを削除する
        quizData.removeAt(0)
    }

    //解答ボタンが押されたら呼ばれる
    fun checkAnswer(view: View) {
        //どの解答ボタンが押されたか
        //ボタンのテキストを得る
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        //ダイアログのタイトルを作成
        val alertTitle: String
        if (btnText == rightAnswer) {
            alertTitle = "正解!"
            rightAnswerCount ++
        } else {
            alertTitle = "不正解..."
        }

        //ダイアログを作成
        //下のコードでGUIを作成
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("答え : $rightAnswer")
            .setPositiveButton("OK") { dialogInterface, i ->
                checkQuizCount()//QuizCountを増やす
            }
                //okを押さないと、画面が閉じない
            .setCancelable(false)
            .show()
    }
    fun checkQuizCount() {
        if (quizCount == totalQuiz) {
            //結果画面を表示
            //新しくアクティビティを開くときはIntentクラスを使用
            /*
            * put extra(取り出すときに使うキー, 渡したい値)で値を渡す
            * startActivity(intent)で次のアクティビティに移行する*/
            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount)
            startActivity(intent)
        }else {
            quizCount ++
            showNextQuiz()
        }
    }
}