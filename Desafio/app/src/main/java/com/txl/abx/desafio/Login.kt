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
import com.google.gson.Gson
import com.txl.abx.desafio.dataclass.Empleado
import com.txl.abx.desafio.extra.eliminarSesion
import com.txl.abx.desafio.extra.iniciarSesion
import com.txl.abx.desafio.extra.validarSesion
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlinx.serialization.decodeFromString

class Login : AppCompatActivity() {
    private val TAG = Login::class.qualifiedName
    private lateinit var til_correo: TextInputLayout
    private lateinit var tiet_correo: TextInputEditText
    private lateinit var til_contrasena: TextInputLayout
    private lateinit var tiet_contrasena: TextInputEditText
    private lateinit var btn_ingresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        eliminarSesion(applicationContext)
        if(validarSesion(applicationContext)){
            lanzarActivity()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        pruebaJsonObject()
    }

    fun pruebaJsonObject(){
        val cadena = """
        {
            "plain-text-token": "3|eEy7eoWviCYveW7GFkzp0a31nvtf3ZGX4QfpXUwH"
        }
        """.trimIndent()
        val jsonObject = JSONObject(cadena)
        Log.e(TAG, jsonObject.getString("plain-text-token"))
        val jsonUsuario = JSONObject()
        jsonUsuario.put("nombre","Ulises")
        jsonUsuario.put("correo","ulises@kodemia.com")
        jsonUsuario.put("telefono","2461343023")
        Log.e(TAG,jsonUsuario.toString())

        val cadenaDos = """
            {
                "nombre":"Ulises",
                "telefono": 2461343023,
                "casado": false,
                "cuotaHora": 150.20,
                "diasDeSemanaTrabajo": [
                    "lunes",
                    "martes",
                    "miercoles",
                    "jueves"
                ],
                "observaciones":[
                    {"fecha":"25/01/2021","comentario":"Todo bien"},
                    {"fecha":"02/01/2021","comentario":"LLego tarde"},
                    {"fecha":"01/01/2021","comentario":"LLego temprano :v"}
                ]
            }
        """.trimIndent()
        try{
            val empleado = Gson().fromJson(cadenaDos, Empleado::class.java)
            Log.d(TAG,empleado.nombre)
            Log.d(TAG,empleado.diasDeSemanaTrabajo.size.toString())
            for (dia in empleado.diasDeSemanaTrabajo){
                Log.d(TAG,dia)
            }
            for (observacion in empleado.observaciones){
                Log.d(TAG,observacion.fecha)
                Log.d(TAG,observacion.comentario)
            }
        }
        catch(e: Exception){
            Log.e(TAG,e.toString())
        }

        val cadenaTres = """
            [
                {
                    "nombre":"Ulises",
                    "telefono": 2461343023,
                    "casado": false,
                    "cuotaHora": 150.20,
                    "diasDeSemanaTrabajo": [
                        "lunes",
                        "martes",
                        "miercoles",
                        "jueves"
                    ],
                    "observaciones":[
                        {"fecha":"25/01/2021","comentario":"Todo bien"},
                        {"fecha":"02/01/2021","comentario":"LLego tarde"},
                        {"fecha":"01/01/2021","comentario":"LLego temprano :v"}
                    ]
                },
                {
                    "nombre":"Fabian",
                    "telefono": 2461343023,
                    "casado": false,
                    "cuotaHora": 250.20,
                    "diasDeSemanaTrabajo": [
                        "lunes",
                        "martes",
                        "miercoles"
                    ],
                    "observaciones":[
                        {"fecha":"25/01/2021","comentario":"Todo bien"},
                        {"fecha":"01/01/2021","comentario":"LLego temprano :v"}
                    ]
                },
            ]
        """.trimIndent()
        val empleados = Gson().fromJson(cadenaTres,Array<Empleado>::class.java)
        /*for (empleado in empleados){
            Log.d(TAG,empleado.nombre)
        }*/

        val cadenaCuatro = """
            {
                "nombre":"Ulises",
                "telefono": 2461343023,
                "casado": false,
                "cuota_hora": 150.20,
                "dias-De-Semana-Trabajo": [
                    "lunes",
                    "martes",
                    "miercoles",
                    "jueves"
                ],
                "observaciones":[
                    {"fecha":"25/01/2021","comentario":"Todo bien"},
                    {"fecha":"02/01/2021","comentario":"LLego tarde"},
                    {"fecha":"01/01/2021","comentario":"LLego temprano :v"}
                ]
            }
        """.trimIndent()
        val empleadoSerialization = Json.decodeFromString<Empleado>(cadenaCuatro)
        Log.d(TAG,empleadoSerialization.nombre)
        Log.d(TAG,empleadoSerialization.cuotaHora.toString())
    }

    fun init(){
        til_correo = findViewById(R.id.til_correo)
        tiet_correo = findViewById(R.id.tiet_correo)
        til_contrasena = findViewById(R.id.til_contrasena)
        tiet_contrasena = findViewById(R.id.tiet_contrasena)
        btn_ingresar = findViewById(R.id.btn_ingresar)
        btn_ingresar.setOnClickListener{
            val cola = Volley.newRequestQueue(applicationContext)
            val json = JSONObject()
            json.put("email",tiet_correo.text)
            json.put("password",tiet_contrasena.text)
            json.put("device_name","User's phone")
            val peticion = JsonObjectRequest(
                Request.Method.POST,
                getString(R.string.url_servidor)+getString(R.string.api_login),
                json,
                {
                    response ->
                    val jsonObject = JSONObject(response.toString())
                    iniciarSesion(applicationContext,jsonObject)
                    if(validarSesion(applicationContext)){
                        lanzarActivity()
                    }
                },
                { error ->
                    Log.e(TAG,error.toString())
                }
            )
            cola.add(peticion)
        }
    }

    fun lanzarActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}