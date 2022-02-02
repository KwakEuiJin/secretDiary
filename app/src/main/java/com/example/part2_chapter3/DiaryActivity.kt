package com.example.part2_chapter3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)
        val diaryEditText = findViewById<EditText>(R.id.diaryEdittext)
        diaryEditText.setText(detailPreferences.getString("detail",""))
        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit{
                putString("detail",diaryEditText.text.toString())
            }
            Log.d("DiaryActivity","SAVE::${diaryEditText.text.toString()}")
        }
        //다이어리 작성중 에러로 인해 정보 손실을 막기위해 edittext가 수정될때마다 sharedpreference에 저장
        diaryEditText.addTextChangedListener{
            Log.d("DiaryActivity","TextChange::$it")
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable,500)
        }
    }
}