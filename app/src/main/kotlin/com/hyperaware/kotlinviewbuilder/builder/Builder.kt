package com.hyperaware.kotlinviewbuilder.builder

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.lang.reflect.Constructor

const val WRAP = WRAP_CONTENT
const val MATCH = MATCH_PARENT

/**
 * Constructs a view of the given generic type with this Context.
 */
inline fun <reified TV : View> Context.v(init: TV.() -> Unit) : TV {
    val v = ConstructorCache.get(TV::class.java).newInstance(this)
    v.init()
    return v
}

/**
 * Constructs a view of the given generic type and adds it as a
 * child to this ViewGroup.
 */
inline fun <reified TV : View> ViewGroup.v(init: TV.() -> Unit) : TV {
    val av = ConstructorCache.get(TV::class.java).newInstance(context)
    addView(av)
    av.init()
    return av
}


/**
 * Converts dp to pixels as float.
 */
fun View.dp_f(dp: Float) : Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}

/**
 * Converts dp to pixels as int.
 */
fun View.dp_i(dp: Float) : Int {
    return dp_f(dp).toInt()
}


/**
 * Property for this View's left padding.
 */
var View.padLeft: Int
    set(value) {
        setPadding(value, paddingTop, paddingRight, paddingBottom)
    }
    get() {
        return paddingLeft
    }

/**
 * Property for this View's top padding.
 */
var View.padTop: Int
    set(value) {
        setPadding(paddingLeft, value, paddingRight, paddingBottom)
    }
    get() {
        return paddingTop
    }

/**
 * Property for this View's right padding.
 */
var View.padRight: Int
    set(value) {
        setPadding(paddingLeft, paddingTop, value, paddingBottom)
    }
    get() {
        return paddingRight
    }

/**
 * Property for this View's bottom padding.
 */
var View.padBottom: Int
    set(value) {
        setPadding(paddingLeft, paddingTop, paddingRight, value)
    }
    get() {
        return paddingBottom
    }

var TextView.textRes: Int
    set(value) = setText(value)
    get() = 0

/** Alias for Context.v<LinearLayout>. */
fun Context.linearLayout(init: LinearLayout.() -> Unit) = v(init)
/** Alias for ViewGroup.v<LinearLayout>. */
fun ViewGroup.linearLayout(init: LinearLayout.() -> Unit) = v(init)

/** Alias for Context.v<TextView>. */
fun Context.textView(init: TextView.() -> Unit) = v(init)
/** Alias for ViewGroup.v<TextView>. */
fun ViewGroup.textView(init: TextView.() -> Unit) = v(init)

fun Context.Button(init: TextView.() -> Unit) = v(init)
fun ViewGroup.button(init: Button.() -> Unit) = v(init)


/**
 * Used to cache view constructors obtained by reflection to avoid doing
 * the reflection every time a new view is created.
 */
object ConstructorCache {
    private val cache = hashMapOf<Class<*>, Constructor<*>>()
    fun <T> get(clazz: Class<T>): Constructor<T> {
        var constructor = cache[clazz]
        if (constructor == null) {
            constructor = clazz.getConstructor(Context::class.java)
            cache.put(clazz, constructor)
        }
        @Suppress("UNCHECKED_CAST")
        return constructor as Constructor<T>
    }
}
