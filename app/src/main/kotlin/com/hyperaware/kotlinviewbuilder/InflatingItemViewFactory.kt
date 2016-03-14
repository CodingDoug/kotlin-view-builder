package com.hyperaware.kotlinviewbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class InflatingItemViewFactory(
        private val inflater: LayoutInflater,
        private val resource: Int,
        private val root: ViewGroup?
) : ViewFactory {

    override fun newView(): View {
        return inflater.inflate(resource, root)
    }

}
