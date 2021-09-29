package com.example.hora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hora.adapter.AdapterRecycler
import com.example.hora.model.MessageData
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var btnSave:Button
    private lateinit var edtMessage : EditText
    private lateinit var txtGson : TextView
    private lateinit var recycler:RecyclerView
    private lateinit var listMessage:ArrayList<MessageData>
    private lateinit var adapterRecycler: AdapterRecycler
    private lateinit var messageData: MessageData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        btnSave = findViewById(R.id.btnSave)
        recycler = findViewById(R.id.recycler)
        edtMessage = findViewById(R.id.edtMeessage)
       // txtGson = findViewById(R.id.txtGson)
        initializeRecycler()

    }

    private fun initializeRecycler() {
        listMessage = ArrayList()
        adapterRecycler = AdapterRecycler(this,listMessage)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapterRecycler
    }

    fun addMessage(view: View) {
        val message = edtMessage
        val msg = message.text.toString().trim()

        if(msg.trim().length>0) {
            listMessage.add(MessageData("$msg",""))
            adapterRecycler.notifyDataSetChanged()
            message.setText("")
            showMessage("Mensaje guardado")
        }
        else {
            showMessage("El campo no debe estar vacío")
        }
    }

/*    fun syncMessages(view: View) {
        var datos = ""
        val message = edtMessage
        val msg = message.text.toString().trim()
        val gson = Gson()
        val msgData = gson.toJson(datos, MessageData::class.java)

        if (listMessage.size > 0) {
            for (msg in listMessage) {
                datos = msgData
                datos += gson.toJson(msg.messageT + msg.time)
            }
            adapterRecycler.notifyDataSetChanged()
        }
    }*/

    private fun showMessage(msj : String = "") {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }
}

