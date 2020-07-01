package com.rojer_ko.translator.presentation.description

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.rojer_ko.translator.R
import com.rojer_ko.utils.isOnline
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*

class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        setActionbarHomeButtonAsUp()
        refreshScreen()
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp(){
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun refreshScreen(){
        description_screen_swipe_refresh_layout.setOnRefreshListener {
            startLoadingOrShowError()
        }
    }

    private fun setData(){
        val bundle = intent.extras

        description_header.text = bundle?.getString(WORD_EXTRA)
        description_textview.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)

        if(imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        }
        else{
            usePicassoToLoadPhoto(description_imageview, imageLink)
        }
    }

    private fun startLoadingOrShowError(){
        if(isOnline(applicationContext)){
            setData()
        }
        else{
            Toast.makeText(applicationContext, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded(){
        if(description_screen_swipe_refresh_layout.isRefreshing){
            description_screen_swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String){
        Picasso.with(applicationContext).load("https:$imageLink")
            .placeholder(R.drawable.ic_baseline_photo_24).fit().centerCrop()
            .into(imageView, object: Callback{
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_baseline_photo_24)
                }
            })
    }

    companion object{
        private const val WORD_EXTRA = "b078e06d-a54e-4ffb-a966-2786c7c80442"
        private const val DESCRIPTION_EXTRA = "79ece470-29fb-40fb-af27-8ab7debd29ec"
        private const val URL_EXTRA = "731375d9-2d15-44b8-ad8e-eebe39628792"

        fun getIntent(context: Context, word: String, description: String, url: String?): Intent =
            Intent(context, DescriptionActivity::class.java).apply {
                putExtra(WORD_EXTRA, word)
                putExtra(DESCRIPTION_EXTRA, description)
                putExtra(URL_EXTRA, url)
            }
    }

}