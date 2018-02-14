package io.github.deweyreed.scrollhmspicker

import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * Created on 2018/2/14.
 */

class ScrollHmsPickerDialog : DialogFragment() {
    interface HmsPickHandler {
        fun onHmsPick(reference: Int, hours: Int, minutes: Int, seconds: Int)
    }

    var reference: Int = -1
    var hours: Int = 0
    var minutes: Int = 0
    var seconds: Int = 0
    var autoStep: Boolean = false
    @ColorRes
    var colorNormal: Int = android.R.color.darker_gray
    @ColorRes
    var colorSelected: Int = android.R.color.holo_red_light
    @ColorRes
    var colorBackground: Int = android.R.color.white
    var dismissListener: DialogInterface.OnDismissListener? = null
    var pickListener: ScrollHmsPickerDialog.HmsPickHandler? = null

    private lateinit var hmsPicker: ScrollHmsPicker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_picker_dialog, container, false)
        hmsPicker = view.findViewById<ScrollHmsPicker>(R.id.hms_picker).also { picker ->
            picker.hours = hours
            picker.minutes = minutes
            picker.seconds = seconds
            picker.setAutoStep(autoStep)
            picker.setColorNormal(colorNormal)
            picker.setColorSelected(colorSelected)
        }
        val textColor = ContextCompat.getColor(view.context, colorSelected)
        view.findViewById<Button>(R.id.button_cancel).apply {
            setTextColor(textColor)
            setOnClickListener { dismiss() }
        }
        view.findViewById<Button>(R.id.button_ok).apply {
            setTextColor(textColor)
            setOnClickListener {
                pickListener?.onHmsPick(reference,
                        hmsPicker.hours, hmsPicker.minutes, hmsPicker.seconds)
                dismiss()
            }
        }
        dialog?.window?.setBackgroundDrawableResource(colorBackground)
        return view
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dismissListener?.onDismiss(dialog)
    }
}