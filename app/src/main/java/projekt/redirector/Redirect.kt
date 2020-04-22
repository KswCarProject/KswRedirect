package projekt.redirector

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Redirect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Specify the launch intent based on the app package
        val packageName = BuildConfig.APPLICATION_ID
        val launchIntent = if (packageName == SELF_PACKAGE_PL) {
            applicationContext.packageManager.getLaunchIntentForPackage(PACKAGE_PL)
        } else {
            applicationContext.packageManager.getLaunchIntentForPackage(PACKAGE_DVR)
        }

        if (launchIntent != null) {
            applicationContext.startActivity(launchIntent)
        } else {
            Toast.makeText(applicationContext, "Package not found", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
