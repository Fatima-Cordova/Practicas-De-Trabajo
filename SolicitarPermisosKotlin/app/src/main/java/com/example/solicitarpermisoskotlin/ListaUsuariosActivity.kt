package com.example.solicitarpermisoskotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solicitarpermisoskotlin.adapter.AdaptadorRecycler
import com.example.solicitarpermisoskotlin.adapter.UsersAdapter
import com.example.solicitarpermisoskotlin.retrofit.RetrofitInstance
import com.example.solicitarpermisoskotlin.retrofit.RetrofitServices
import com.example.solicitarpermisoskotlin.retrofit.model.MessageResponse
import com.example.solicitarpermisoskotlin.retrofit.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaUsuariosActivity : AppCompatActivity() {

    private lateinit var recycler : RecyclerView
    private lateinit var listaUsuarios: ArrayList<UserResponse>
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

        initialize()
        usersList()
    }

    private fun  initialize() {
        recycler = findViewById(R.id.recycler)
        initializeRecyclerView()

    }

    private fun  initializeRecyclerView() {
        val manager = LinearLayoutManager(this)
        listaUsuarios = ArrayList()
        usersAdapter = UsersAdapter(listaUsuarios)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = manager
        recycler.adapter = usersAdapter

        val dividerItemDecoration = DividerItemDecoration(recycler.context, manager.orientation)
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun usersList() {
        val retrofitServices = RetrofitInstance.getInstance()?.create(RetrofitServices::class.java)
        val responseCall = retrofitServices?.getUsers()

        responseCall?.enqueue(object: Callback<List<UserResponse>> {
            override fun onResponse(call: Call<List<UserResponse>>, response: Response<List<UserResponse>>) {
                if(response.body() != null) {
                    val users = response.body() as ArrayList<UserResponse>
                    updateRecycler(users)
                }
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                println(t)
            }
        })
    }

    private fun updateRecycler(updateUsers: ArrayList<UserResponse>) {
        if(updateUsers.size > 0) {
            listaUsuarios.clear()
            listaUsuarios.addAll(updateUsers)
            usersAdapter.notifyDataSetChanged()
        }
    }

    fun regresar(view: View) {
        val intent = Intent(this, UsuarioActivity::class.java).apply {

        }
        startActivity(intent)
    }
}