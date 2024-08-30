package io.actinis.opengl.sample.dialog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DialogActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showAlertDialog()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert Dialog Title")
        builder.setMessage("This is the message of the Alert Dialog.")

        builder.setPositiveButton("OK") { dialog, which ->
            Toast.makeText(this, "OK button clicked", Toast.LENGTH_SHORT).show()

            showAlertDialog()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            Toast.makeText(this, "Cancel button clicked", Toast.LENGTH_SHORT).show()

            showAlertDialog()
        }

        builder.setNeutralButton("Remind me later") { dialog, which ->
            Toast.makeText(this, "Remind me later button clicked", Toast.LENGTH_SHORT).show()

            showAlertDialog()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}