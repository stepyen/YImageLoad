package com.stepyen.yimageload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stepyen.yimageload.glide.ImageLoaderManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val url = "https://cdn.nlark.com/yuque/0/2022/png/511193/1644896111627-04a2f8bd-4152-44f7-a421-a36cbb80aecc.png"  // 226 * 160
        ImageLoaderManager.getInstance().loadImage(iv,url)
    }
}