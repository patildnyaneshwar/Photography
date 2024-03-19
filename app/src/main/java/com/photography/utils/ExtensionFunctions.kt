package com.photography.utils

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONObject

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

inline fun <reified T : Any> T.objectToString(): String = Gson().toJson(this, T::class.java)
inline fun <reified T : Any> String.stringToObject(): T = Gson().fromJson(this, T::class.java)
inline fun <reified  T : Any> T.listToString(): String = Gson().toJson(this, object : TypeToken<List<T>>() {}.type)
inline fun <reified  T : Any> String.stringToList(): List<T> = Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)

inline fun <reified T> ResponseBody.errorObject(): T = Gson().fromJson(JSONObject(charStream().readText()).toString(), T::class.java)