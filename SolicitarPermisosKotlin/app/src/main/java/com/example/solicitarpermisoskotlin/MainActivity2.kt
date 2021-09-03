package com.example.solicitarpermisoskotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private lateinit var edtBuscar : EditText
    private lateinit var btnBuscar : Button
    private lateinit var txtMensaje : TextView
    private lateinit var txtUserId : TextView
    private lateinit var txtId : TextView
    private lateinit var switchBoolean : Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initialize()
    }

    private fun  initialize() {
        edtBuscar = findViewById(R.id.edtBuscarUsuario)
        btnBuscar = findViewById(R.id.btnBuscarMensajes)
        txtMensaje = findViewById(R.id.txtUserName)
        txtUserId = findViewById(R.id.txtId)
        txtId = findViewById(R.id.txtName)
        switchBoolean = findViewById(R.id.switchBoolean)
        setContent(MessageResponse())
    }

    fun getData(view: View) {
        btnBuscar.isEnabled = false
        setContent(MessageResponse())
        menssage()
    }

    private fun menssage() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getMensaje(edtBuscar.text.toString())

        responseCall?.enqueue(object: Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if(response.isSuccessful && response.body() != null) {
                    val message = response.body() as MessageResponse
                    updateUI(message)
                   // response.body() as ArrayList<MessageResponse>
                    // var messageResponse : MessageResponse()
                }
                btnBuscar.isEnabled = true
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                println(t)
                btnBuscar.isEnabled = true
            }
        })
    }

    private fun updateUI(messageResponse: MessageResponse) {
        if(messageResponse != null) {
            setContent(messageResponse)
        }
    }

    private fun setContent(messageResponse: MessageResponse) {
        if(messageResponse != null) {
            txtUserId.text = messageResponse.userId.toString()
            txtId.text = messageResponse.id.toString()
            txtMensaje.text = messageResponse.title
            switchBoolean.isChecked = messageResponse.completed
        } else {
            txtUserId.text = ""
            txtId.text = ""
            txtMensaje.text = ""
            switchBoolean.isChecked = false
        }
    }
}
