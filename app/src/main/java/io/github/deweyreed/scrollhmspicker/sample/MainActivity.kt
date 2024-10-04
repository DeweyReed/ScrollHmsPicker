package io.github.deweyreed.scrollhmspicker.sample

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.github.deweyreed.scrollhmspicker.ScrollHmsPicker
import io.github.deweyreed.scrollhmspicker.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDialog.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setView(R.layout.dialog_picker)
                .setPositiveButton(android.R.string.ok, null)
                .show()
            val picker = dialog.findViewById<View>(R.id.picker) as ScrollHmsPicker
            picker.setTypeface(Typeface.DEFAULT_BOLD)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                onHmsPick(hours = picker.hours, minutes = picker.minutes, seconds = picker.seconds)
                dialog.dismiss()
            }
        }
        binding.btnGetTime.setOnClickListener {
            onHmsPick(
                hours = binding.scrollHmsPicker.hours,
                minutes = binding.scrollHmsPicker.minutes,
                seconds = binding.scrollHmsPicker.seconds
            )
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
