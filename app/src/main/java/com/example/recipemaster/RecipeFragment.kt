package com.example.recipemaster

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.custom_snackbar.view.*
import kotlinx.android.synthetic.main.fragment_recipe.*
import java.io.File
import java.io.FileOutputStream
import com.facebook.FacebookSdk.getApplicationContext




class RecipeFragment : Fragment(), SaveFileInterface {


    private var listener: OnFragmentInteractionListener? = null
    private var data: Model? = null

    private var userName: String = ""
    private var isLog: Boolean = false

    private var snackBar: Snackbar? = null

    var listPermissions = listOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var imageToSave: ImageView? = null
    val PERMISSIONS_REQUEST_WTIRE_EXTERNAL = 123


    fun makeSnackBar(){

        if(isLog) {
            snackBar = Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                "", Snackbar.LENGTH_INDEFINITE
            )

            snackBar!!.view.setBackgroundColor(Color.parseColor("#e1e1e1"))

            val view : View = LayoutInflater.from(context!!).inflate(R.layout.custom_snackbar,null, false)
            view.user_info.text = "Logged as " + userName

            val sl : Snackbar.SnackbarLayout = snackBar!!.view as Snackbar.SnackbarLayout
            sl.findViewById<TextView>(android.support.design.R.id.snackbar_text).setVisibility(View.INVISIBLE)

            sl.addView(view,0)

            snackBar!!.show()

        }
    }

    fun getParams(){
        data = arguments!!.getSerializable("recipe") as Model

        userName = arguments!!.getString("userName")
        isLog = arguments!!.getBoolean("isLog")
    }


    fun setDataOnListview(){
        val al = ArrayList<Model>()
        al.add(data as Model)


        val adapter = RecipeAdapter(context!!, al,this)
        recipes_list.adapter = adapter

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setDataOnListview()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onStop() {
        super.onStop()
        if(snackBar != null)
                snackBar!!.dismiss()
    }


    override fun onResume() {
        super.onResume()
        getParams()
        setDataOnListview()
        makeSnackBar()
    }


    interface OnFragmentInteractionListener {

        fun onFragmentInteraction(uri: Uri)
    }

    fun saveImg(){

        try {
            val draw = imageToSave!!.drawable as BitmapDrawable
            val bitmap = draw.bitmap

            var outStream: FileOutputStream? = null


            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/" + fileName
            )

            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream!!.flush()
            outStream.close()

           Toast.makeText(context!!,"Image saved!", Toast.LENGTH_LONG).show()


        }
        catch (e: Throwable){
            Toast.makeText(context!!,"Something went worng :( Try again!", Toast.LENGTH_LONG).show()
        }


    }

    override fun saveImageOnDevice(iv: ImageView) {

        imageToSave = iv

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                listPermissions.toTypedArray(),
                PERMISSIONS_REQUEST_WTIRE_EXTERNAL
            )
        } else {
            saveImg()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_WTIRE_EXTERNAL -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {

                saveImg()
            } else {
                Toast.makeText(getApplicationContext(), "Permission is not granted!", Toast.LENGTH_SHORT).show()
            }

        }

    }



}
