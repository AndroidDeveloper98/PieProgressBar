package com.hexalitics.brushtimer.pieprogressbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    private var mSeekBar: SeekBar? = null
    private var mProgressPieView: PieProgressView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mProgressPieView = findViewById(R.id.progressPie)

        // SeekBar
        mSeekBar = findViewById(R.id.seekBar)
        mSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                mProgressPieView!!.setProgress(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }


    fun redirect(view: View) {
        startActivity(Intent(this@MainActivity, BgActivity::class.java))
    }

}