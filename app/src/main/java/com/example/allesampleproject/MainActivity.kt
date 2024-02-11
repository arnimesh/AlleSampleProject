package com.example.allesampleproject

import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.allesampleproject.interfaces.OnSnapPositionChangeListener
import com.example.allesampleproject.adapters.ImagePicsAdapter
import com.example.allesampleproject.data.ImagePics
import com.example.allesampleproject.utils.ScaleLayoutManager
import com.example.allesampleproject.listeners.SnapOnScrollListener
import com.example.allesampleproject.utils.attachSnapHelperWithListener


class MainActivity : AppCompatActivity(), OnSnapPositionChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageList: ArrayList<ImagePics>
    private lateinit var ImagePicsAdapter: ImagePicsAdapter
    private lateinit var imageView: ImageView
    private lateinit var btn: Button
    private val GALLERY_CODE = 1221
    private var currImagePositon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        btn = findViewById(R.id.button)
        btn.setOnClickListener(View.OnClickListener {
            openGalleryForImages()
        })


    }

    private fun openGalleryForImages() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_CODE);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == GALLERY_CODE) {
            if (data?.clipData != null) {
                val count = data.clipData?.itemCount
                Log.d("test", data.data.toString())
                for (i in 0..<count!!) {
                    val uri: Uri = data.clipData?.getItemAt(i)!!.uri
                    imageList.add(currImagePositon, ImagePics(uri))
                    ImagePicsAdapter.notifyItemInserted(currImagePositon)
                    imageView.setImageURI(uri)
                   // loadImage(this, imageView, uri)

                }
            } else if (data?.data != null) {
                val uri = data.data
                imageList.add(currImagePositon, ImagePics(uri))
                ImagePicsAdapter.notifyItemInserted(currImagePositon)
                imageView.setImageURI(uri)

                //loadImage(this, imageView, uri)

            }
        }
    }


    private fun init() {
        recyclerView = findViewById(R.id.recylerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = ScaleLayoutManager(this, RecyclerView.HORIZONTAL, false)
        imageList = ArrayList()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        addDataToList()


        recyclerView.attachSnapHelperWithListener(
            snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            this
        )

        ImagePicsAdapter = ImagePicsAdapter(imageList, this)
        recyclerView.adapter = ImagePicsAdapter
        ImagePicsAdapter.setOnItemClickListener(object : ImagePicsAdapter.onItemClickListener {

            override fun onItemClick(pos: Int) {
                val position = pos % imageList.size
//                Log.d("test123", imageList[position].foodImage.toString())
//                Log.d("test", position.toString())
                imageView = findViewById(R.id.imageView1)
                imageView.setImageURI(imageList[position].imagePics)
            }
        })
    }


    private fun addDataToList() {
        for (i in 1..50) {
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


    override fun onSnapPositionChange(pos: Int) {
        currImagePositon = pos
        imageView = findViewById(R.id.imageView1)
        imageView.setImageURI(imageList[pos].imagePics)

       // loadImage(imageView.context, imageView, imageList[pos].imagePics)
    }

//    private fun loadImage(context: Context, image: ImageView, uri: Uri?) {
//        Glide.with(this)
//            .load(uri)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.test) // Placeholder image
//                    .error(R.drawable.test) // Error image in case of loading failure
//            )
//            .into(imageView)
//    }
}


