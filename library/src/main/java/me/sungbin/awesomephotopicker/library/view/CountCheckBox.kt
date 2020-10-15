package me.sungbin.awesomephotopicker.library.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import me.sungbin.awesomephotopicker.library.R


/**
 * Created by SungBin on 2020-07-26.
 */

class CountCheckBox : FrameLayout {

    private lateinit var flLayout: FrameLayout
    private lateinit var tvCount: TextView

    constructor(context: Context) : super(context) {
        CountCheckBox(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_round_checkbox, this, false)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CountCheckBox,
            0,
            0
        )

        flLayout = view.findViewById(R.id.fl_main)
        tvCount = view.findViewById(R.id.tv_count)

        typedArray.recycle()
        addView(view)
        invalidate()
    }

}