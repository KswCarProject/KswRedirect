package projekt.redirector

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import projekt.redirector.utils.ParseConfig
import java.io.File


class Redirect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Dexter.withContext(applicationContext)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            val toParse =
                                File(Environment.getExternalStorageDirectory().absolutePath + "/redirects.xml")
                            if (toParse.exists()) {
                                val read = ParseConfig.read(
                                    toParse.absolutePath,
                                    BuildConfig.APPLICATION_ID
                                )
                                val packageName = read[0]
                                val launchIntent =
                                    applicationContext.packageManager.getLaunchIntentForPackage(
                                        packageName
                                    )
                                if (launchIntent != null) {
                                    applicationContext.startActivity(launchIntent)
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        String.format(
                                            getString(
                                                R.string.error_config_entry,
                                                packageName
                                            )
                                        ),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    getString(R.string.error_config),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Toast.makeText(applicationContext, it.name, Toast.LENGTH_SHORT).show()
            }
            .check()

        finish()
        finishAffinity()
    }
}
