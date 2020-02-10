package com.example.myasynctask

import android.app.Dialog
import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.nfc.Tag
import android.os.AsyncTask
import android.util.Log
import kotlin.properties.Delegates

public class MyAsyncTask : AsyncTask<String, Int, Long>, SearchManager.OnCancelListener,SearchManager.OnDismissListener{
    //フィールド
    var Tag: String = "MyAsyncTask"
    var dialog : ProgressDialog by Delegates.notNull<ProgressDialog>()
    var context : Context by Delegates.notNull<Context>()

    constructor(context: Context) {
        this.context = context
    }

    override fun onPreExecute() {
        super.onPreExecute()

        Log.d(Tag,"onPreExecute")
        dialog = ProgressDialog(context)
        dialog.setTitle("Please wait")
        dialog.setMessage("Loding data...")

        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        dialog.setCancelable(true)

        dialog.max = 100
        dialog.setProgress(0)
        dialog.show()
    }



    override fun doInBackground(vararg params: String?): Long {
        Log.d(Tag,"doInBackground - " + params[0])
        try {
            Log.d(Tag,"Try - " + params[0])
            for (i in 0..9){
                Log.d(Tag,"forEach - " + i)
                if (isCancelled){
                    Log.d(Tag,"Cancelled")
                    break
                }
                Thread.sleep(1000)
                publishProgress((i + 1) * 10)
            }
        }catch (e : InterruptedException){
            Log.d(Tag,"InterruptedException in doInBackground")
        }
        return  123L
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d(Tag,"onProgressUpdate - "  + values[0])
        dialog.setProgress(values[0]!!.toInt())
    }

    override fun onCancelled() {
        Log.d(Tag,"onCancelled")
        dialog.dismiss()
    }

    override fun onPostExecute(result: Long) {
        Log.d(Tag,"onPostExecute - " + result)
        dialog.dismiss()
    }

    override fun onCancel() {

    }

    override fun onDismiss() {
        Log.d(Tag,"Dialog onCancell...calling cancel(true)")
        this.cancel(true)
    }

}