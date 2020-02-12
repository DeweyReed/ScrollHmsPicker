package io.github.deweyreed.scrollhmspicker.sample

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_picker.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setView(R.layout.dialog_picker)
                .setPositiveButton(android.R.string.yes, null)
                .show()
            val picker = dialog.picker
            picker.setTypeface(Typeface.DEFAULT_BOLD)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                onHmsPick(picker.hours, picker.minutes, picker.seconds)
                dialog.dismiss()
            }
        }
        btnGetTime.setOnClickListener {
            onHmsPick(scrollHmsPicker.hours, scrollHmsPicker.minutes, scrollHmsPicker.seconds)
        }
    }

    private fun onHmsPick(hours: Int, minutes: Int, seconds: Int) {
        Toast.makeText(
            this,
            "hours: $hours, minutes: $minutes, seconds: $seconds",
            Toast.LENGTH_SHORT
        ).show()
    }
}
