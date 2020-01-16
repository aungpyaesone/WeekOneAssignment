package com.aungpyaesone.weekoneassignment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_VIDEO_CAPTURE = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCrateTimer.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE,"hello timer")
                putExtra(AlarmClock.EXTRA_LENGTH,60)
                putExtra(AlarmClock.EXTRA_SKIP_UI,true)
            }
            if (intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

        btnCreateAnEvent.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE,"hello event")
                putExtra(CalendarContract.Events.EVENT_LOCATION,"In Yangon")
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 1000)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 10000)
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        btnCallPhone.setOnClickListener {
            if( etPhoneNumber.text.toString() !=""){
                dailPhoneNumber(etPhoneNumber.text.toString())
            }
            else {
                Toast.makeText(this,"Pls enter phno",Toast.LENGTH_SHORT).show()
            }
        }

        btnOpenVideo.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent->
                takeVideoIntent.resolveActivity(packageManager)?.also{
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
        }

        btnNextActivity.setOnClickListener {
            startActivity(SecondActivity.newIntent(this))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK && data != null)
        {
            val videoUri: Uri? = data.data
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }

    fun dailPhoneNumber(phoneNumber: String)
    {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
