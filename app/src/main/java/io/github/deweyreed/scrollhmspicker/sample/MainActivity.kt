package io.github.deweyreed.scrollhmspicker.sample

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.github.deweyreed.scrollhmspicker.ScrollHmsPickerBuilder
import io.github.deweyreed.scrollhmspicker.ScrollHmsPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), ScrollHmsPickerDialog.HmsPickHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDialog.setOnClickListener {
            ScrollHmsPickerBuilder(supportFragmentManager, this).show()
        }
        btnCustomDialog.setOnClickListener {
            ScrollHmsPickerBuilder(supportFragmentManager, this)
                    .setReference(255)
                    .setTime(1, 23, 45)
                    .setAutoStep(true)
                    .setColorNormal(android.R.color.holo_blue_light)
                    .setColorSelected(android.R.color.black)
                    .setColorBackground(android.R.color.holo_orange_light)
                    .setDismissListener(DialogInterface.OnDismissListener {
                        toast("Dismiss")
                    })
                    .show()
        }
        var isShown = false
        btnXml.setOnClickListener {
            val visibility = if (isShown) View.GONE else View.VISIBLE
            isShown = !isShown
            arrayOf(scrollHmsPicker, btnGetTime).forEach {
                it.visibility = visibility
            }
        }
        btnGetTime.setOnClickListener {
            onHmsPick(-1,
                    scrollHmsPicker.hours, scrollHmsPicker.minutes, scrollHmsPicker.seconds)
        }
    }

    override fun onHmsPick(reference: Int, hours: Int, minutes: Int, seconds: Int) {
        longToast("reference: $reference, hours: $hours, minutes: $minutes, seconds: $seconds")
    }
}
