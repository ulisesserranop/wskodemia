package com.txl.abx.desafio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.txl.abx.desafio.books.Books
import com.txl.abx.desafio.dataclass.Empleado
import com.txl.abx.desafio.dataclass.Errors
import com.txl.abx.desafio.extra.*
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.nio.charset.Charset

class Login : AppCompatActivity() {
    private val TAG = Login::class.qualifiedName
    private lateinit var til_correo: TextInputLayout
    private lateinit var tiet_correo: TextInputEditText
    private lateinit var til_contrasena: TextInputLayout
    private lateinit var tiet_contrasena: TextInputEditText
    private lateinit var btn_ingresar: Button
    private lateinit var pb_login: ProgressBar
    private lateinit var tv_registrate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        if(validarSesion(applicationContext)){
            lanzarActivity()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        //pruebaJsonObject()
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

        val cadenaCinco = """
            {
              "data": [
                {
                  "type": "books",
                  "id": "venganza-nuestra",
                  "attributes": {
                    "title": "Vengaza Nuestra",
                    "slug": "venganza-nuestra",
                    "content": "Narrativa sobre la postconquista de México",
                    "created-at": "2021-11-06T18:41:14+00:00",
                    "updated-at": "2021-11-06T18:41:14+00:00"
                  },
                  "relationships": {
                    "authors": {
                      "links": {
                        "self": "https://bookstore.test/api/v1/books/venganza-nuestra/relationships/authors",
                        "related": "https://bookstore.test/api/v1/books/venganza-nuestra/authors"
                      }
                    },
                    "categories": {
                      "links": {
                        "self": "https://bookstore.test/api/v1/books/venganza-nuestra/relationships/categories",
                        "related": "https://bookstore.test/api/v1/books/venganza-nuestra/categories"
                      }
                    }
                  },
                  "links": {
                    "self": "https://bookstore.test/api/v1/books/venganza-nuestra"
                  }
                },
                {
                  "type": "books",
                  "id": "venganza-nuestra",
                  "attributes": {
                    "title": "Vengaza Nuestra",
                    "slug": "venganza-nuestra",
                    "content": "Narrativa sobre la postconquista de México",
                    "created-at": "2021-11-06T18:41:14+00:00",
                    "updated-at": "2021-11-06T18:41:14+00:00"
                  },
                  "relationships": {
                    "authors": {
                      "links": {
                        "self": "https://bookstore.test/api/v1/books/venganza-nuestra/relationships/authors",
                        "related": "https://bookstore.test/api/v1/books/venganza-nuestra/authors"
                      }
                    },
                    "categories": {
                      "links": {
                        "self": "https://bookstore.test/api/v1/books/venganza-nuestra/relationships/categories",
                        "related": "https://bookstore.test/api/v1/books/venganza-nuestra/categories"
                      }
                    }
                  },
                  "links": {
                    "self": "https://bookstore.test/api/v1/books/venganza-nuestra"
                  }
                }
              ]
            }
        """.trimIndent()
        Log.d(TAG,"AQUI")
        val books = Json.decodeFromString<Books>(cadenaCinco)
        for (book in books.data){
            Log.d(TAG,book.attributes.title)
        }

    }

    fun init(){
        til_correo = findViewById(R.id.til_correo)
        tiet_correo = findViewById(R.id.tiet_correo)
        til_contrasena = findViewById(R.id.til_contrasena)
        tiet_contrasena = findViewById(R.id.tiet_contrasena)
        btn_ingresar = findViewById(R.id.btn_ingresar)
        pb_login = findViewById(R.id.pb_login)
        btn_ingresar.setOnClickListener{
            if(validarCorreo() && validarContrasena()){
                realizarPeticion()
            }
        }
        tv_registrate = findViewById(R.id.tv_registrate)
        tv_registrate.setOnClickListener{
            startActivity(Intent(this,Registro::class.java))
        }
    }

    fun realizarPeticion(){
        VolleyLog.DEBUG = true
        if(estaEnLinea(applicationContext)){
            btn_ingresar.visibility = View.GONE
            pb_login.visibility = View.VISIBLE
            val cola = Volley.newRequestQueue(applicationContext)
            val json = JSONObject()
            json.put("email",tiet_correo.text.toString())
            json.put("password",tiet_contrasena.text.toString())
            json.put("device_name","User's phone")
            val peticion = object: JsonObjectRequest(Request.Method.POST,getString(R.string.url_servidor)+getString(R.string.api_login),json,
                {
                        response ->
                    val jsonObject = JSONObject(response.toString())
                    iniciarSesion(applicationContext,jsonObject)
                    if(validarSesion(applicationContext)){
                        lanzarActivity()
                    }
                },
                { error ->
                    btn_ingresar.visibility = View.VISIBLE
                    pb_login.visibility = View.GONE
                    val json = JSONObject(String(error.networkResponse.data, Charsets.UTF_8))
                    val errors = Json.decodeFromString<Errors>(json.toString())
                    for (error in errors.errors){
                        mensajeEmergente(this,error.detail)
                    }
                    Log.e(TAG,error.networkResponse.toString())
                    Log.e(TAG,error.toString())
                }
            ){
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

    fun lanzarActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validarCorreo(): Boolean{
        return if(tiet_correo.text.toString().isEmpty()){
            til_correo.error = getString(R.string.campo_vacio)
            false
        }
        else{
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(tiet_correo.text.toString()).matches()){
                til_correo.isErrorEnabled = false
                true
            }
            else{
                til_correo.error = getString(R.string.error_correo)
                false
            }
        }
    }

    private fun validarContrasena(): Boolean{
        return if(tiet_contrasena.text.toString().isEmpty()){
            til_contrasena.error = getString(R.string.campo_vacio)
            false
        }
        else{
            til_contrasena.isErrorEnabled = false
            true
        }
    }
}