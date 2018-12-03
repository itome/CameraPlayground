package team.itome.camera.playground

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import team.itome.camera2sample.R

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.button_preview).setOnClickListener {
            startActivityWithPermissionCheck(
                Intent(this, PreviewActivity::class.java),
                REQUEST_PREVIEW
            )
        }
        findViewById<View>(R.id.button_rotation).setOnClickListener {
            startActivityWithPermissionCheck(
                Intent(this, RotationActivity::class.java),
                REQUEST_ROTATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.size != 2
            || grantResults[0] != PackageManager.PERMISSION_GRANTED
            || grantResults[1] != PackageManager.PERMISSION_GRANTED
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        when (requestCode) {
            REQUEST_PREVIEW -> startActivity(Intent(this, PreviewActivity::class.java))
            REQUEST_ROTATION -> startActivity(Intent(this, RotationActivity::class.java))
        }
    }

    private fun startActivityWithPermissionCheck(intent: Intent, requestCode: Int) {
        if (!hasCameraPermission() || !hasStoragePermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode
            )
        } else {
            startActivity(intent)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_PREVIEW = 1
        private const val REQUEST_ROTATION = 2
    }
}

