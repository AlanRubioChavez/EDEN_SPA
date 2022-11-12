package com.example.edenspa

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_config_serv_premium.*

class ConfigServPremium : AppCompatActivity() {

    var edtComPremium:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_serv_premium)

        edtComPremium=findViewById(R.id.edtComPremium)
        var pref=getSharedPreferences("premier",Context.MODE_PRIVATE)
        var comision=pref.getString("comision", "")
        edtComPremium?.setText(comision)

        iniciar1()
    }

    /*
     *Configuración inicial
     */
    fun guardar(view: View){
        var pref=getSharedPreferences("premier",Context.MODE_PRIVATE)
        var editor=pref.edit()
        editor.putString("comision", edtComPremium?.text.toString().toFloat().toString())
        editor.commit()
        Toast.makeText(this,"¡Se ha guardado exitosamente!", Toast.LENGTH_LONG).show()
    }

    private fun iniciar1(){
        title = "Configuración Servicio Premier"
        btnRegresar.setOnClickListener { finish() }
    }

}