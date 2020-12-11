package com.digitalhouse.marsgaze.models.insight

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//Classe criada apenas para o uso do typeToken para a conversão
class GenericWeather {
    companion object {
        inline fun <reified T> fromJson(json: String): T {
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}

data class NasaWeather(
    var First_UTC: String,
    var Last_UTC: String,
    var PRE: PreClass,
    var Season: String,
    var WD: WdClass
)

data class PreClass(
    var av: String,
    var ct: String,
    var mn: String,
    var mx: String,
)

data class WdClass(
    var most_common: String
)