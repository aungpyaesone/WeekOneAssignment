package com.aungpyaesone.weekoneassignment

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_SELECT_CONTACT = 1
        fun newIntent(context: Context):Intent{
            val intent = Intent(context,SecondActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btnSelectContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
               // type = ContactsContract.Contacts.CONTENT_TYPE
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE

            }
            if(intent.resolveActivity(packageManager) != null)
            {
                startActivityForResult(intent, REQUEST_SELECT_CONTACT)
            }
        }

        btnWebSearch.setOnClickListener {
            when{
                etWebSearch.text.toString() != "" -> webSearch(etWebSearch.text.toString())
                else -> Toast.makeText(this,"Please enter web search query",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun webSearch(query:String)
    {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY,query)
        }
        if(intent.resolveActivity(packageManager) != null)
        {
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_SELECT_CONTACT && resultCode == Activity.RESULT_OK && data != null)
        {
            val contactUri: Uri? = data.data
            val projection: Array<String> = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
            contactUri?.let { contentResolver.query(it,projection,null,null,null,null).use {
                cursor-> if(cursor!!.moveToFirst())
            {
                val numberIndex =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberIndex)
                tvPhoneResult.text = number
            }
            }}
        }
    }
}
