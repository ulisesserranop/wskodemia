package com.txl.abx.desafio.extra

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.material.snackbar.Snackbar
import com.txl.abx.desafio.R
import org.json.JSONObject

fun obtenerPreferencias(context: Context): SharedPreferences{
    return EncryptedSharedPreferences.create(context.getString(
        R.string.name_file_preferences),
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun iniciarSesion(context: Context, jsonObject: JSONObject){
    val sharedPreferences = obtenerPreferencias(context)
    //Se puede obtener de la siguiente manera
    // jsonObject.getString("token")
    // jsonObject.getString(context.getString(R.string.key_token))
    // jsonObject["token"].toString()
    // jsonObject[context.getString(R.string.key_token)].toString()
    with(sharedPreferences.edit()){
        putString("token",jsonObject.getString(context.getString(R.string.key_token)))
        apply()
    }
}

fun validarSesion(context: Context): Boolean{
    val sharedPreferences = obtenerPreferencias(context)
    val token = sharedPreferences.getString("token","")
    return !token.isNullOrEmpty()
}

fun obtenerDeSesion(context: Context,clave: String): String{
    val sharedPreferences = obtenerPreferencias(context)
    return sharedPreferences.getString(clave,"").toString()
}

fun eliminarSesion(context: Context){
    val sharedPreferences = obtenerPreferencias(context)
    with(sharedPreferences.edit()){
        clear()
        apply()
    }
}

fun mensajeEmergente(
    activity: Activity,
    mensaje: String
){
    val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),mensaje, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    snackBar.show()
}//end of function mensajeEmergente

fun estaEnLinea(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}//end of function estaEnLinea