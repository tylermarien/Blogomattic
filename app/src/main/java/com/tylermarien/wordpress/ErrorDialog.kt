package com.tylermarien.wordpress

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ErrorDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(arguments?.getString(ARG_MESSAGE) ?: DEFAULT_MESSAGE)
                .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "ErrorDialog"

        private const val ARG_MESSAGE = "message"
        private const val DEFAULT_MESSAGE = "There was an error"

        fun create(message: String?): ErrorDialog {
            val dialog = ErrorDialog()

            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            dialog.arguments = args

            return dialog
        }
    }

}