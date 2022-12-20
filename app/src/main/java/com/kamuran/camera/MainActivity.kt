package com.kamuran.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    var gecerliDosyaYolu:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IzınKontrol()
    }

    private fun IzınKontrol() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
            &&ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
            &&ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            kameraAc()
        }else{
           ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,
           Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        kameraAc()
    }

    private fun kameraAc() {
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            val fotoCekıntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(
                fotoCekıntent.resolveActivity(packageManager) !=null
            ){
                val storageDir:File?= getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val resimDosyasi:File= File.createTempFile("resimDosyası",".jpg",storageDir)
                gecerliDosyaYolu=resimDosyasi.absolutePath
                if (resimDosyasi !=null)
                {
                    val photoUri:Uri=FileProvider.getUriForFile(this,packageName,resimDosyasi)
                    fotoCekıntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                    startActivityForResult(fotoCekıntent,1)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK){
            println("fotoğraf cekildi..")
        }
    }
}