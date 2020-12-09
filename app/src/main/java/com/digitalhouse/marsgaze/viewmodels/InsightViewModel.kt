package com.digitalhouse.marsgaze.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.insight.GenericWeather
import com.digitalhouse.marsgaze.models.insight.NasaWeather
import com.digitalhouse.marsgaze.services.MarsInsightService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsightViewModel(val service: MarsInsightService) : ViewModel(){
    val weather = MutableLiveData<Map<String, NasaWeather> >()

    fun getWeather() {
        viewModelScope.launch {
            val call = service.getWeather()

            // Não tenho certeza do q esta acontecendo aqui em baixo mas esta funcionando
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    //aqui vem o retorno para validar se deu algum erro na requisição
                    if(response.isSuccessful) {
                        val JsonString = response.body()
                        if (JsonString != null) {
                            //limpa o json, aquela parte de baixo
                            val newJsonString = JsonString.substring(0, JsonString.indexOf("\"sol_keys\":")).trim().dropLast(1) + "}"

                            //converte para a classe desejava
                            weather.value = GenericWeather.fromJson(newJsonString)
                        }
                    }
                }

                //provavelmente esse erro que vai vir aqui em baixo é erro de autorização,etc
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", "Deu RUIM! ")
                }
            })

        }
    }
}