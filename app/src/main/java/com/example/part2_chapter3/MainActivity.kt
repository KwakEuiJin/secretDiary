package com.example.part2_chapter3

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import java.awt.font.NumericShaper

class MainActivity : AppCompatActivity() {
    private val numberPicker1 by lazy { findViewById<NumberPicker>(R.id.numberPicker1).apply {
        minValue=0
        maxValue=9
    } }
    private val numberPicker2 by lazy { findViewById<NumberPicker>(R.id.numberPicker2).apply {
        minValue=0
        maxValue=9
    } }
    private val numberPicker3 by lazy { findViewById<NumberPicker>(R.id.numberPicker3).apply {
        minValue=0
        maxValue=9
    } }

    private val bt_open by lazy { findViewById<AppCompatButton>(R.id.bt_open) }
    private val bt_change by lazy { findViewById<AppCompatButton>(R.id.bt_change) }

    private var changePasswordMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberPicker1
        numberPicker2
        numberPicker3

        bt_open.setOnClickListener{
            if (changePasswordMode){
                Toast.makeText(this,"비밀번호 변경 중 입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser="${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            Log.d("내가 선택한 숫자",passwordFromUser)
            Log.d("현재 비밀번호", passwordPreferences.getString("password","000").toString())

            if (passwordPreferences.getString("password","000").equals(passwordFromUser)){
                //패스워드 성공
                //TODO 다이어리 페이지 작성후 코드 작성
                val intent = Intent(this,DiaryActivity::class.java)
                startActivity(intent)
            }
            else {
                //실패
                showErrorAlerDialog()
            }
        }

        bt_change.setOnClickListener{
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser="${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"
            if (changePasswordMode){
                //새로운 비밀번호를 저장하는 기능
                passwordPreferences.edit(true){
                    putString("password",passwordFromUser)
                }
                changePasswordMode=false
                bt_change.setBackgroundColor(Color.BLACK)
            }
            else{
                //비밀번호 교체모드 발동 :: 비밀번호가 맞는지 체크

                Log.d("내가 선택한 숫자",passwordFromUser)
                Log.d("현재 비밀번호", passwordPreferences.getString("password","000").toString())

                if (passwordPreferences.getString("password","000").equals(passwordFromUser)){
                    //비밀번호가 맞을때
                    changePasswordMode = true
                    Toast.makeText(this,"변경할 비밀번호를 세팅하세요",Toast.LENGTH_SHORT).show()
                    bt_change.setBackgroundColor(Color.RED)
                }
                else {
                    //실패
                    showErrorAlerDialog()
                }

            }
        }
    }
    private fun showErrorAlerDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}