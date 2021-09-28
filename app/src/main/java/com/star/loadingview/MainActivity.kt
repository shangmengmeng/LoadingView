package com.star.loadingview

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.star.starloading.star_dialog.StarLoadingDialog


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dialog: StarLoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = StarLoadingDialog(this)
        dialog.start()

        findViewById<TextView>(R.id.btn_start).setOnClickListener(this)

        findViewById<TextView>(R.id.btn_stop).setOnClickListener {
            dialog.stop()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_start -> {
                dialog.start()
            }
        }
    }
}