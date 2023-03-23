package com.hexalitics.brushtimer.pieprogressbar.adapter

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.SeekBarBindingAdapter.setProgress
import androidx.recyclerview.widget.RecyclerView
import com.hexalitics.brushtimer.pieprogressbar.PieAndBGprogressView
import com.hexalitics.brushtimer.pieprogressbar.PieProgressView
import com.hexalitics.brushtimer.pieprogressbar.databinding.TimerListItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TimerListAdapter(
    private var context: Context, private var data: ArrayList<String>
) : RecyclerView.Adapter<TimerListAdapter.SingleViewHolder>() {

    var onClick: OnItemClickListener? = null

    fun setOnItemClickLitener(mOnItemClickListener: OnItemClickListener) {
        this.onClick = mOnItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val binding: TimerListItemBinding = TimerListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SingleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {

        var currentProgress: Int = 0
        var currentSeconds = Calendar.getInstance().get(Calendar.SECOND)
        try {
            if (currentSeconds>=30){
                currentSeconds -= 30
            }
            currentProgress = currentSeconds
            holder.binding.pieProgress.setProgress(currentProgress)
            currentSeconds *= 1000
        } catch (e: Exception) {
            Log.e("CurrentSeconds", "---Current--${e.message}")
        }
        Log.d("onTick", "----$currentSeconds")
       val countDownTimer = object : CountDownTimer(30000L, 1000) {
            override fun onTick(tick: Long) {
                if (currentProgress == 30) {
                    onFinish()
                } else {
                    currentProgress++
                    holder.binding.pieProgress.setProgress(currentProgress)
                    //Log.e("CurrentSeconds", "---Current--${currentProgress}")
                    Log.e("CurrentSeconds", "---Current--${tick}")
                }
            }

            override fun onFinish() {
                cancel()
                startCountDown(holder.binding)
            }
        }
        countDownTimer.start()

    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class SingleViewHolder(val binding: TimerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    fun String.getMillis(): Long {
        var tMillis: Long = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        try {
            val mDate = sdf.parse(this)
            tMillis = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return tMillis
    }


    private fun startCountDown(progressView: TimerListItemBinding) {
        var currentProgress: Int = 0
        val countDownTimer = object : CountDownTimer(30000L, 1000) {
            override fun onTick(tick: Long) {
                if (currentProgress == 30) {
                    currentProgress = 0
                } else {
                    currentProgress++
                    progressView.pieProgress.setProgress(currentProgress)
                    //Log.e("CurrentSeconds", "---Current--${currentProgress}")
                    Log.e("CurrentSeconds", "---Current--${tick}")
                }
            }

            override fun onFinish() {
                startCountDown(progressView)
            }
        }
        countDownTimer.start()
    }

}