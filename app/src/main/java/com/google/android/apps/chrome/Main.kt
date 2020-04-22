package com.google.android.apps.chrome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import projekt.redirector.Redirect


class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, Redirect::class.java))
        finish()
        finishAffinity()
    }
}