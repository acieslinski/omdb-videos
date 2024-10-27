package com.acieslinski.videos.featues.videos.list.ui.helpers

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class AlertDialogHelper {
    private var alertDialog: AlertDialog? = null
    private lateinit var fragment: Fragment
    var title: String? = null
    var message: Int? = null
    var isVisible: Boolean = false
        set(value) {
            field = value
            setAlertDialog()
        }
    var onDismissListener: (() -> Unit)? = null

    fun attachToFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    fun onDestroyView() {
        alertDialog?.setOnDismissListener(null)
        alertDialog?.dismiss()
    }

    private fun setAlertDialog() = with(fragment) {
        alertDialog?.dismiss()
        if (this@AlertDialogHelper.isVisible) {
            if (isAdded && !requireActivity().isFinishing) {
                alertDialog = with(AlertDialog.Builder(requireContext())) {
                    setTitle(title)
                    message?.let { setMessage(it) }
                    setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    setOnDismissListener {
                        alertDialog = null
                        onDismissListener?.invoke()
                    }
                }
                    .create()
                    .also { it.show() }
            }
        }
    }
}