package com.hyperaware.kotlinviewbuilder

import android.content.Context
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import com.hyperaware.kotlinviewbuilder.builder.*

class BuildingItemViewFactory(private val context: Context) : ViewFactory {

    override fun newView(): View {
        return context.linearLayout {
            layoutParams = ViewGroup.LayoutParams(MATCH, WRAP)
            orientation = HORIZONTAL
            padTop = dp_i(8.0f)
            padBottom = dp_i(8.0f)
            padLeft = dp_i(16.0f)
            padRight = dp_i(16.0f)

            textView {
                with(layoutParams as LinearLayout.LayoutParams) {
                    width = WRAP
                    height = WRAP
                    rightMargin = dp_i(16f)
                    marginEnd = rightMargin
                    gravity = CENTER_VERTICAL
                }
                textSize = 24f
                textRes = R.string.time
            }

            linearLayout {
                with(layoutParams as LinearLayout.LayoutParams) {
                    width = 0
                    height = WRAP
                    weight = 1f
                    gravity = CENTER_VERTICAL
                }
                orientation = VERTICAL

                textView {
                    textRes = R.string.day
                }

                textView {
                    textRes = R.string.location
                }
            }
        }
    }

}
