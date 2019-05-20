package com.example.recipemaster

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import com.example.recipemaster.View.ShoppingListActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null
    private var userName: String = ""
    private var isLog: Boolean = false

    private val RecipeFragment : RecipeFragment = RecipeFragment()
    private val mainPageFragment : mainPageFragment = mainPageFragment()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.frag,mainPageFragment)
        transaction.addToBackStack(null)
        transaction.commit()



        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    isLog = true

                    val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                        try {
                            if (`object`.has("id")) {
                                userName = `object`["name"].toString()

                            } else {
                                Log.e("FBLOGIN_FAILD", `object`.toString())
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "name")
                    request.parameters = parameters
                    request.executeAsync()

                    menu_fb.labelText = "Log out"



                }

                override fun onCancel() {


                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@MainActivity, "Something went wrong :( Try again!", Toast.LENGTH_LONG).show()

                }
            })



        menu_fb.setOnClickListener{
            fbButtonClick()
        }

        menu_getData.setOnClickListener{
            getDataFromApi()
        }

        menu_list.setOnClickListener{
            val intent = Intent(this, ShoppingListActivity::class.java)
            startActivity(intent)
        }

    }

    fun setIsLog(b: Boolean){
        isLog = b
    }

    fun setUser(userName: String){
        this.userName = userName
    }

    fun fbButtonClick(){
        if(isLog){
            LogOutDialog()
        }
        else
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
    }

    fun LogOutDialog(){
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Logged in as: " + userName)

        builder.setPositiveButton("Log out"){dialog, which->
            LoginManager.getInstance().logOut()
            isLog = false
            menu_fb.labelText = "Login with facebook"
            Toast.makeText(this@MainActivity, "You have been logged out.", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("Cancel"){dialog, which->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    fun getDataFromApi(){
        val retrofit = Retrofit.Builder().baseUrl("https://moodup.team/test/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val service = retrofit.create(RecipeApi::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<Model>{
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.code() == 200){

                    val recipeResponse = response.body()!!

                    val args = Bundle()
                    args.putSerializable("recipe", recipeResponse)
                    args.putString("userName", userName)
                    args.putBoolean("isLog", isLog)


                    menu.visibility = View.GONE
                    menu.close(false)
                    setTitle("Pizza Recipe!")

                    val actionB = supportActionBar!!
                    actionB.setDisplayHomeAsUpEnabled(true)

                    RecipeFragment.arguments = args

                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frag,RecipeFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()

                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Connection problem :( Try again!",Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == android.R.id.home){

            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frag,mainPageFragment)
            transaction.addToBackStack(null)
            transaction.commit()

            menu.visibility = View.VISIBLE
            setTitle("RecipeMaster")

            val actionB = supportActionBar!!
            actionB.setDisplayHomeAsUpEnabled(false)

            return true
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {

    }


}
