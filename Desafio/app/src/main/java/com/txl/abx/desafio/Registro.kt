package com.txl.abx.desafio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.txl.abx.desafio.dataclass.Errors
import com.txl.abx.desafio.extra.estaEnLinea
import com.txl.abx.desafio.extra.iniciarSesion
import com.txl.abx.desafio.extra.mensajeEmergente
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class Registro : AppCompatActivity() {
    
    private val TAG = Registro::class.qualifiedName
    private lateinit var til_registro_nombre: TextInputLayout
    private lateinit var tiet_registro_nombre: TextInputEditText
    private lateinit var til_registro_correo: TextInputLayout
    private lateinit var tiet_registro_correo: TextInputEditText
    private lateinit var til_registro_contrasena: TextInputLayout
    private lateinit var tiet_registro_contrasena: TextInputEditText
    private lateinit var til_registro_contrasena_dos: TextInputLayout
    private lateinit var tiet_registro_contrasena_dos: TextInputEditText
    private lateinit var btn_registrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        init()
    }

    fun init(){
        til_registro_nombre = findViewById(R.id.til_registro_nombre)
        tiet_registro_nombre = findViewById(R.id.tiet_registro_nombre)
        til_registro_correo = findViewById(R.id.til_registro_correo)
        tiet_registro_correo = findViewById(R.id.tiet_registro_correo)
        til_registro_contrasena = findViewById(R.id.til_registro_contrasena)
        tiet_registro_contrasena = findViewById(R.id.tiet_registro_contrasena)
        til_registro_contrasena_dos = findViewById(R.id.til_registro_contrasena_dos)
        tiet_registro_contrasena_dos = findViewById(R.id.tiet_registro_contrasena_dos)
        btn_registrar = findViewById(R.id.btn_registrar)
        btn_registrar.setOnClickListener{
            realizarPeticion()
        }
    }

    fun realizarPeticion(){
        if(estaEnLinea(applicationContext)){
            val json = JSONObject()
            json.put("name",tiet_registro_nombre.text)
            json.put("email",tiet_registro_correo.text)
            json.put("password",tiet_registro_contrasena.text)
            json.put("password_confirmation",tiet_registro_contrasena_dos.text)
            //json.put("device_name","User's phone")
            val cola = Volley.newRequestQueue(applicationContext)
            val peticion = object: JsonObjectRequest(Request.Method.POST,getString(R.string.url_servidor)+getString(R.string.api_registro),json,{
                response ->
                Log.d(TAG,response.toString())
                val jsonObject = JSONObject(response.toString())
                iniciarSesion(applicationContext,jsonObject)
                val intent = Intent(this,MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            },{
                error ->
                val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                val errors = Json.decodeFromString<Errors>(json.toString())
                for (error in errors.errors){
                    mensajeEmergente(this,error.detail)
                }
                Log.e(TAG,error.toString())
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Accept"] = "application/json"
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            cola.add(peticion)
        }
        else{
            mensajeEmergente(this,getString(R.string.error_internet))
        }
    }
}