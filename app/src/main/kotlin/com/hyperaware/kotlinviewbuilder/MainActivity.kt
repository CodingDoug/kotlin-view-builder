package com.hyperaware.kotlinviewbuilder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var inflatingItemViewFactory: ViewFactory
    private lateinit var buildingItemViewFactory: ViewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflatingItemViewFactory = InflatingItemViewFactory(layoutInflater, R.layout.item, null)
        (findViewById(R.id.inflated) as FrameLayout).addView(inflatingItemViewFactory.newView())

        buildingItemViewFactory = BuildingItemViewFactory(this)
        (findViewById(R.id.built) as FrameLayout).addView(buildingItemViewFactory.newView())

        findViewById(R.id.bench_inflate)!!.setOnClickListener {
            val tv = (findViewById(R.id.inflate_time) as TextView)//.text = "" + inflate_ms + "ms"

            Thread({
                runOnUiThread({
                    tv.text = "Running..."
                })

                Runtime.getRuntime().gc()
                Thread.sleep(1000)
                val inflate_ms = TimeUnit.NANOSECONDS.toMillis(bench(inflatingItemViewFactory))

                runOnUiThread({
                    tv.text = "1000x in " + inflate_ms + "ms"
                })
            }).start()
        }

        findViewById(R.id.bench_build)!!.setOnClickListener {
            val tv = (findViewById(R.id.build_time) as TextView)

            Thread({
                runOnUiThread({
                    tv.text = "Running..."
                })

                Runtime.getRuntime().gc()
                Thread.sleep(1000)
                val inflate_ms = TimeUnit.NANOSECONDS.toMillis(bench(buildingItemViewFactory))

                runOnUiThread({
                    tv.text = "1000x in " + inflate_ms + "ms"
                })
            }).start()
        }
    }

    private fun bench(factory: ViewFactory): Long {
        val start = System.nanoTime()
        for (i in 0..999) {
            factory.newView()
        }
        val end = System.nanoTime()
        return end - start;
    }
}

