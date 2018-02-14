package io.github.deweyreed.scrollhmspicker

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * Created on 2018/2/13.
 */

@Suppress("unused", "MemberVisibilityCanBePrivate")
/**
 * Displays text with no padding at the top.
 */
internal class ZeroTopPaddingTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {

    companion object {

        private const val NORMAL_FONT_PADDING_RATIO = 0.328f
        // the bold fontface has less empty space on the top
        private const val BOLD_FONT_PADDING_RATIO = 0.208f

        private const val NORMAL_FONT_BOTTOM_PADDING_RATIO = 0.25f
        // the bold fontface has less empty space on the top
        private const val BOLD_FONT_BOTTOM_PADDING_RATIO = 0.208f

        // pre-ICS (Droid Sans) has weird empty space on the bottom
        private const val PRE_ICS_BOTTOM_PADDING_RATIO = 0.233f

        private val SAN_SERIF_BOLD = Typeface.create("san-serif", Typeface.BOLD)
        private val SAN_SERIF_CONDENSED_BOLD =
                Typeface.create("sans-serif-condensed", Typeface.BOLD)
    }

    private var mPaddingRight = 0

    init {
        includeFontPadding = false
        updatePadding()
    }

    fun updatePadding() {
        var paddingRatio = NORMAL_FONT_PADDING_RATIO
        var bottomPaddingRatio = NORMAL_FONT_BOTTOM_PADDING_RATIO
        if (paint.typeface != null && paint.typeface == Typeface.DEFAULT_BOLD) {
            paddingRatio = BOLD_FONT_PADDING_RATIO
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO
        }
        if (typeface != null && typeface == SAN_SERIF_BOLD) {
            paddingRatio = BOLD_FONT_PADDING_RATIO
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO
        }
        if (typeface != null && typeface == SAN_SERIF_CONDENSED_BOLD) {
            paddingRatio = BOLD_FONT_PADDING_RATIO
            bottomPaddingRatio = BOLD_FONT_BOTTOM_PADDING_RATIO
        }
        // Since we're targeting API 14 or later, this piece of code is unnecessary
        //        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
        //                getText() != null &&
        //                (getText().toString().equals(decimalSeparator) ||
        //                        getText().toString().equals(timeSeparator))) {
        //            bottomPaddingRatio = PRE_ICS_BOTTOM_PADDING_RATIO;
        //        }

        // no need to scale by display density because getTextSize() already returns the font
        // height in px
        setPadding(0, (-paddingRatio * textSize).toInt(), mPaddingRight,
                (-bottomPaddingRatio * textSize).toInt())
    }

    fun updatePaddingForBoldDate() {
        // no need to scale by display density because getTextSize() already returns the font
        // height in px
        setPadding(0, (-BOLD_FONT_PADDING_RATIO * textSize).toInt(), mPaddingRight,
                (-BOLD_FONT_BOTTOM_PADDING_RATIO * textSize).toInt())
    }

    fun setPaddingRight(padding: Int) {
        mPaddingRight = padding
        updatePadding()
    }
}