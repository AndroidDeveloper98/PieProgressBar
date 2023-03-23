package com.hexalitics.brushtimer.pieprogressbar

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hexalitics.brushtimer.pieprogressbar.adapter.TimerListAdapter
import com.hexalitics.brushtimer.pieprogressbar.databinding.ActivityPieAndBgBinding
import java.util.*
import kotlin.collections.ArrayList

class PieAndBgActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE){
        ActivityPieAndBgBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list : ArrayList<String> = ArrayList()
        for (i in 0..100){
            list.add("")
        }

        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val notificationListAdapter = TimerListAdapter(this, list)
        binding.rvList.adapter = notificationListAdapter
    }


}
