package com.aman.chatapp

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class OptionsDialog(activity: Activity, listener: DialogInterface) :
    AdapterView.OnItemSelectedListener {

    private var dialog: Dialog? = null
    private var btnOk: Button? = null
    private var btnCancel: Button? = null
    private var tvTitle: TextView? = null
    private var tvMessage: TextView? = null
    private var name: TextView? = null
    private lateinit var radioGroup: RadioGroup
    private var radioadmin: RadioButton? = null
    private var radioastro: RadioButton? = null
    private var radioclient: RadioButton? = null
    private var context: Context? = null
    private val TAG = "OptionsDialog"
    private var refId: String? = ""

    init {
        dialog = Dialog(activity)
        dialog!!.setContentView(R.layout.layout_package_dialog)
        btnOk = dialog!!.findViewById(R.id.btnOk)
        btnCancel = dialog!!.findViewById(R.id.btnCancel)
        tvTitle = dialog!!.findViewById(R.id.tvDialogTitle)
        tvMessage = dialog!!.findViewById(R.id.stationame)
        name = dialog!!.findViewById(R.id.name)
        radioGroup = dialog!!.findViewById(R.id.radioGroup)
        radioadmin = dialog!!.findViewById(R.id.admin)
        radioastro = dialog!!.findViewById(R.id.astrologer)
        radioclient = dialog!!.findViewById(R.id.client)

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.admin -> {
                    name!!.visibility = View.GONE

                }
                R.id.astrologer -> {
                   name!!.visibility = View.VISIBLE
                }
                R.id.client -> {
                    name!!.visibility = View.GONE

                }
            }
        })
        btnOk!!.setOnClickListener {
            Log.e(TAG, "radioGroup!!.checkedRadioButtonId  ${radioGroup!!.checkedRadioButtonId}")
            if (radioGroup!!.checkedRadioButtonId == -1) {
                Toast.makeText(context, R.string.select_type, Toast.LENGTH_LONG).show()

            } else if (radioGroup.getCheckedRadioButtonId() != -1) {
                    if (radioastro!!.isChecked) {
                        if(name!!.text.toString().isNullOrEmpty()){
                            return@setOnClickListener
                        }
                    Log.i("astro", radioGroup!!.checkedRadioButtonId.toString())
                    listener.dialogOk(UserType.ASTROLOGER.type, name!!.text.toString().trim(), refId)


                } else if (radioclient!!.isChecked) {
                        listener.dialogOk(UserType.CLIENT.type, "", refId)

                    Log.i("client", radioGroup!!.checkedRadioButtonId.toString())

                } else if (radioadmin!!.isChecked) {
                        listener.dialogOk(UserType.ADMIN.type, "", refId)

                    Log.i("client", radioGroup!!.checkedRadioButtonId.toString())

                }}


        }


        btnCancel!!.setOnClickListener{ listener.dialogCancel() }

        dialog!!.setCancelable(true)
    }

    fun Builder(): OptionsDialog {
        return this
    }


    fun setRefId(refId: String) {
        this.refId= refId
    }
fun show() {
        dialog!!.show()
    }

    fun dismiss() {
        try {
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            Log.d(TAG, "dismiss", e)
        }
    }

    companion object {
        private val TAG = OptionsDialog::class.java.simpleName
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}

