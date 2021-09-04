package com.example.solicitarpermisoskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioActivity : AppCompatActivity() {

    private lateinit var edtBuscarUsuario : EditText
    private lateinit var btnBuscarUsuario : Button
    private lateinit var btnMostrarLista : Button
    private lateinit var txtId : TextView
    private lateinit var txtName : TextView
    private lateinit var txtUserName : TextView
    private lateinit var txtEmail : TextView
    private lateinit var txtStreet : TextView
    private lateinit var txtSuite : TextView
    private lateinit var txtCity : TextView
    private lateinit var txtZipCode : TextView
    private lateinit var txtLat : TextView
    private lateinit var txtLng : TextView
    private lateinit var txtPhone : TextView
    private lateinit var txtWebSite : TextView
    private lateinit var txtNameCompany : TextView
    private lateinit var txtCatchPharse : TextView
    private lateinit var txtBs : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        initialize()
    }

    private fun  initialize() {
        edtBuscarUsuario = findViewById(R.id.edtBuscarUsuario)
        btnBuscarUsuario = findViewById(R.id.btnBuscarUsuario)
        btnMostrarLista = findViewById(R.id.btnMostrarLista)
        txtId = findViewById(R.id.txtId)
        txtName = findViewById(R.id.txtName)
        txtUserName = findViewById(R.id.txtUserName)
        txtEmail = findViewById(R.id.txtEmail)
        txtStreet = findViewById(R.id.txtStreet)
        txtSuite = findViewById(R.id.txtSuite)
        txtCity = findViewById(R.id.txtCity)
        txtZipCode = findViewById(R.id.txtZipCode)
        txtLat = findViewById(R.id.txtLat)
        txtLng = findViewById(R.id.txtLng)
        txtPhone = findViewById(R.id.txtPhone)
        txtWebSite = findViewById(R.id.txtWebsite)
        txtNameCompany = findViewById(R.id.txtNameCompany)
        txtCatchPharse = findViewById(R.id.txtCatchPharse)
        txtBs = findViewById(R.id.txtBs)
        setContent(UserResponse())
    }

    fun getUsers(view: View) {
        btnBuscarUsuario.isEnabled = false
        setContent(UserResponse())
        usuario()
    }

    fun viewList(view: View) {
        val intent = Intent(this, ListaUsuariosActivity::class.java).apply {
        }
        startActivity(intent)
    }

    private fun usuario() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getUser(edtBuscarUsuario.text.toString())

        responseCall?.enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    val user = response.body() as UserResponse
                    updateUI(user)
                    // response.body() as ArrayList<MessageResponse>
                    // var messageResponse : MessageResponse()
                }
                btnBuscarUsuario.isEnabled = true
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                println(t)
                btnBuscarUsuario.isEnabled = true
            }
        })
    }

    private fun updateUI(userResponse: UserResponse) {
        if(userResponse != null) {
            setContent(userResponse)
        }
    }

    private fun setContent(userResponse: UserResponse) {
        if(userResponse != null) {
            txtId.text = userResponse.id.toString()
            txtName.text = userResponse.name
            txtUserName.text = userResponse.username
            txtEmail.text = userResponse.email
            txtStreet.text = userResponse.address.street
            txtSuite.text = userResponse.address.suite
            txtCity.text = userResponse.address.city
            txtZipCode.text = userResponse.address.zipcode
            txtLat.text = userResponse.address.geo.lat.toString()
            txtLng.text = userResponse.address.geo.lng.toString()
            txtPhone.text = userResponse.phone
            txtWebSite.text = userResponse.website
            txtNameCompany.text = userResponse.company.name
            txtCatchPharse.text = userResponse.company.catchPhrase
            txtBs.text = userResponse.company.bs


        } else {
            txtId.text = ""
            txtName.text = ""
            txtUserName.text = ""
            txtEmail.text = ""
            txtStreet.text = ""
            txtSuite.text = ""
            txtCity.text = ""
            txtZipCode.text = ""
            txtLat.text = ""
            txtLng.text = ""
            txtPhone.text = ""
            txtWebSite.text = ""
            txtNameCompany.text = ""
            txtZipCode.text = ""
            txtCatchPharse.text = ""
            txtBs.text = ""
        }
    }


}
