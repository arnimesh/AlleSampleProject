package com.example.allesampleproject

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView
    private lateinit var imageList: ArrayList<ImagePics>
    private lateinit var imagePicsAdapter: ImagePicsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        addDataToList()

        recyclerView = findViewById(R.id.recylerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)


        imagePicsAdapter = ImagePicsAdapter(imageList, this)
        recyclerView.adapter = imagePicsAdapter


    }


    private fun addDataToList() {
        for (i in 1..100){
            imageList
            imageList.add(ImagePics(parseDrawableImage(R.drawable.one)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.two)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.three)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.four)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.five)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.six)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.seven)))
            imageList.add(ImagePics(parseDrawableImage(R.drawable.eight)))
        }
    }
    private fun parseDrawableImage(image: Int): Uri? {
        val resources: Resources = applicationContext.resources
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(
                image
            ) + '/' + resources.getResourceTypeName(image) + '/' + resources.getResourceEntryName(
                image
            )
        )
    }


}