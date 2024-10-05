package io.github.deweyreed.scrollhmspicker

import android.content.Context
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import io.github.deweyreed.scrollhmspicker.databinding.ShpScrollhmspickerBinding

/**
 * Created on 2018/2/13.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class ScrollHmsPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding =
        ShpScrollhmspickerBinding.inflate(LayoutInflater.from(context), this)

    private val allPickers =
        listOf(binding.pickerHours, binding.pickerMinutes, binding.pickerSeconds)

    private val allTexts =
        listOf(binding.textHours, binding.textMinutes, binding.textSeconds)

    private var autoStep: Boolean = false
    private var enable99Hours = false

    var hours: Int
        get() = binding.pickerHours.value
        set(value) = setSafeHours(value)

    var minutes: Int
        get() = binding.pickerMinutes.value
        set(value) = setSafeMinutes(value)

    var seconds: Int
        get() = binding.pickerSeconds.value
        set(value) = setSafeSeconds(value)

    init {
        orientation = HORIZONTAL

        val res = resources

        val ta = context.obtainStyledAttributes(attrs, R.styleable.ScrollHmsPicker)

        val colorNormal = ta.getColor(
            R.styleable.ScrollHmsPicker_shp_normal_color,
            color(android.R.color.darker_gray)
        )
        @ColorInt val colorSelected = ta.getColor(
            R.styleable.ScrollHmsPicker_shp_selected_color,
            color(android.R.color.holo_red_light)
        )
        val hours = ta.getInteger(R.styleable.ScrollHmsPicker_shp_hours, 0)
        val minutes = ta.getInteger(R.styleable.ScrollHmsPicker_shp_minutes, 0)
        val seconds = ta.getInteger(R.styleable.ScrollHmsPicker_shp_seconds, 0)
        val autoStep = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_auto_step, false)

        val showHours = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_show_hours, true)
        val showMinutes = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_show_minutes, true)
        val showSeconds = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_show_seconds, true)

        enable99Hours = ta.getBoolean(R.styleable.ScrollHmsPicker_shp_enable_99_hours, false)
        ta.recycle()

        binding.pickerHours.maxValue = 99
        setHoursVisibility(showHours)
        set99Hours(enable99Hours)

        binding.pickerMinutes.maxValue = 59
        setMinutesVisibility(showMinutes)

        binding.pickerSeconds.maxValue = 59
        setSecondsVisibility(showSeconds)

        setSafeHours(hours)
        setSafeMinutes(minutes)
        setSafeSeconds(seconds)
        setAutoStep(autoStep)

        allPickers.forEach {
            it.setContentTextTypeface(Typeface.SANS_SERIF)
            it.setNormalTextColor(colorNormal)
            it.setSelectedTextColor(colorSelected)
        }
        //
        //----------|
        //          |
        // selected |     |--|   <- label
        //          |     |--|
        //          |         ---->  move label to the bottom of selected item
        // ---------|                padding == (selected size - label size) / 2
        //
        val textMarginTop = ((res.getDimension(R.dimen.shp_text_size_selected_item)
                - res.getDimension(R.dimen.shp_text_size_label)) / 2).toInt()

        allTexts.forEach { view ->
            view.setTextColor(colorSelected)
            // align texts to the bottom of the selected text
            view.layoutParams = (view.layoutParams as LayoutParams).also {
                it.topMargin = textMarginTop
            }
        }
    }

    fun setColorNormal(@ColorRes res: Int) {
        setColorIntNormal(color(res))
    }

    fun setColorIntNormal(@ColorInt color: Int) {
        allPickers.forEach {
            it.setNormalTextColor(color)
        }
    }

    fun setColorSelected(@ColorRes res: Int) {
        setColorIntSelected(color(res))
    }

    fun setColorIntSelected(@ColorInt color: Int) {
        allPickers.forEach {
            it.setSelectedTextColor(color)
        }
        allTexts.forEach {
            it.setTextColor(color)
        }
    }

    fun setAutoStep(newValue: Boolean) {
        if (newValue != autoStep) {
            autoStep = newValue
            if (autoStep) {
                binding.pickerMinutes.setOnValueChangeListenerInScrolling { _, oldVal, newVal ->
                    val hoursVal = binding.pickerHours.value
                    if (oldVal == 59 && newVal == 0) {
                        binding.pickerHours.smoothScrollToValue((hoursVal + 1) % (if (enable99Hours) 100 else 24))
                    } else if (oldVal == 0 && newVal == 59) {
                        binding.pickerHours.smoothScrollToValue(if (hoursVal > 0) hoursVal - 1 else ((if (enable99Hours) 99 else 23)))
                    }
                }
                binding.pickerSeconds.setOnValueChangeListenerInScrolling { _, oldVal, newVal ->
                    val minutesVal = binding.pickerMinutes.value
                    if (oldVal == 59 && newVal == 0) {
                        binding.pickerMinutes.smoothScrollToValue((minutesVal + 1) % 60)
                    } else if (oldVal == 0 && newVal == 59) {
                        binding.pickerMinutes.smoothScrollToValue(if (minutesVal > 0) minutesVal - 1 else 59)
                    }
                }
            } else {
                binding.pickerMinutes.setOnValueChangeListenerInScrolling(null)
                binding.pickerSeconds.setOnValueChangeListenerInScrolling(null)
            }
        }
    }

    fun setHoursVisibility(show: Boolean) {
        val visibility = if (show) VISIBLE else GONE
        binding.pickerHours.visibility = visibility
        binding.textHours.visibility = visibility
    }

    fun setMinutesVisibility(show: Boolean) {
        val visibility = if (show) VISIBLE else GONE
        binding.pickerMinutes.visibility = visibility
        binding.textMinutes.visibility = visibility
    }

    fun setSecondsVisibility(show: Boolean) {
        val visibility = if (show) VISIBLE else GONE
        binding.pickerSeconds.visibility = visibility
        binding.textSeconds.visibility = visibility
    }

    fun set99Hours(enable: Boolean) {
        enable99Hours = enable
        binding.pickerHours.setMinAndMaxShowIndex(0, if (enable) 99 else 23)
    }

    fun setTypeface(newTypeface: Typeface) {
        binding.pickerHours.setContentTextTypeface(newTypeface)
        binding.pickerHours.setHintTextTypeface(newTypeface)
        binding.textHours.typeface = newTypeface

        binding.pickerMinutes.setContentTextTypeface(newTypeface)
        binding.pickerMinutes.setHintTextTypeface(newTypeface)
        binding.textMinutes.typeface = newTypeface

        binding.pickerSeconds.setContentTextTypeface(newTypeface)
        binding.pickerSeconds.setHintTextTypeface(newTypeface)
        binding.textSeconds.typeface = newTypeface
    }

    fun getTypeface(): Typeface {
        return binding.textHours.typeface
    }

    private fun setSafeHours(hours: Int) {
        if (hours in 0..(if (enable99Hours) 99 else 23)) {
            binding.pickerHours.value = hours
        }
    }

    private fun setSafeMinutes(minutes: Int) {
        if (minutes in 0..59) {
            binding.pickerMinutes.value = minutes
        }
    }

    private fun setSafeSeconds(seconds: Int) {
        if (seconds in 0..59) {
            binding.pickerSeconds.value = seconds
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parent = super.onSaveInstanceState()
        return (if (parent != null) SavedState(parent) else SavedState()).also { state ->
            state.hours = hours
            state.minutes = minutes
            state.seconds = seconds
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            hours = state.hours
            minutes = state.minutes
            seconds = state.seconds
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private class SavedState : BaseSavedState {
        var hours: Int = 0
        var minutes: Int = 0
        var seconds: Int = 0

        constructor() : super(Parcel.obtain())

        constructor(superState: Parcelable) : super(superState)

        private constructor(source: Parcel?) : super(source) {
            source?.run {
                hours = source.readInt()
                minutes = source.readInt()
                seconds = source.readInt()
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.run {
                writeInt(hours)
                writeInt(minutes)
                writeInt(seconds)
            }
        }

        private companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel?): SavedState = SavedState(source)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
            }
        }
    }

    private fun View.color(@ColorRes id: Int) = ContextCompat.getColor(this.context, id)
}
