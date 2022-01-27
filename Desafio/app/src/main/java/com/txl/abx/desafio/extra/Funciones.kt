package com.txl.abx.desafio.extra

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
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