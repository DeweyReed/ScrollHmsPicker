package io.github.deweyreed.scrollhmspicker

import android.content.DialogInterface
import android.support.annotation.ColorRes
import android.support.v4.app.FragmentManager

@Suppress("unused", "MemberVisibilityCanBePrivate")
/**
 * Created on 2018/2/14.
 */

class ScrollHmsPickerBuilder(private val fragmentManager: FragmentManager,
                             private val pickListener: ScrollHmsPickerDialog.HmsPickHandler) {
    private var reference: Int = -1
    private var hours: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0
    private var autoStep: Boolean = false
    @ColorRes
    private var colorBackground: Int = android.R.color.white
    @ColorRes
    private var colorNormal: Int = android.R.color.darker_gray
    @ColorRes
    private var colorSelected: Int = android.R.color.holo_red_light
    private var dismissListener: DialogInterface.OnDismissListener? = null

    fun setReference(r: Int): ScrollHmsPickerBuilder = apply { reference = r }

    fun setTime(h: Int, m: Int, s: Int): ScrollHmsPickerBuilder = apply {
        hours = h
        minutes = m
        seconds = s
    }

    fun setTimeInSeconds(totalSeconds: Int): ScrollHmsPickerBuilder {
        val hours = totalSeconds / 3600
        val remaining = totalSeconds % 3600
        val minutes = remaining / 60
        val seconds = remaining % 60
        return setTime(hours, minutes, seconds)
    }

    fun setTimeInMilliseconds(timeInMilliseconds: Long): ScrollHmsPickerBuilder {
        return setTimeInSeconds((timeInMilliseconds / 1000L).toInt())
    }

    fun setAutoStep(b: Boolean): ScrollHmsPickerBuilder = apply { autoStep = b }

    fun setDismissListener(listener: DialogInterface.OnDismissListener): ScrollHmsPickerBuilder = apply {
        dismissListener = listener
    }

    fun setColorBackground(@ColorRes id: Int): ScrollHmsPickerBuilder = apply {
        colorBackground = id
    }

    fun setColorNormal(@ColorRes id: Int): ScrollHmsPickerBuilder = apply {
        colorNormal = id
    }

    fun setColorSelected(@ColorRes id: Int): ScrollHmsPickerBuilder = apply {
        colorSelected = id
    }

    fun show() {
        val dialogTag = "scroll_hms_dialog"
        fragmentManager.findFragmentByTag(dialogTag)?.let { fragment ->
            fragmentManager.beginTransaction().apply {
                remove(fragment)
            }.commit()
        }

        ScrollHmsPickerDialog().also { dialog ->
            dialog.reference = reference
            dialog.hours = hours
            dialog.minutes = minutes
            dialog.seconds = seconds
            dialog.autoStep = autoStep
            dialog.colorNormal = colorNormal
            dialog.colorSelected = colorSelected
            dialog.colorBackground = colorBackground
            dialog.dismissListener = dismissListener
            dialog.pickListener = pickListener
        }.show(fragmentManager, dialogTag)
    }
}