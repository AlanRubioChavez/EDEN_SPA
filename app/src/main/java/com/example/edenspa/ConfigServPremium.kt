package com.example.edenspa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_a_b_c_servicios.*
import kotlinx.android.synthetic.main.activity_config_serv_premium.*

class ConfigServPremium : AppCompatActivity() {

    private var archivoXML = ""
    private var premium = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_serv_premium)

        iniciar1()
    }

    /*
     *Configuración inicial
     */
    private fun iniciar1(){
        title = "Configuración Servicio Premium"
        //Recuperar el nombre del archivo .xml
        if (edtComPremium.text.isNotEmpty()){
            premium = edtComPremium.text.toString().toFloat()
        }
        val bundle : Bundle? = intent.extras

        if (bundle != null){
            archivoXML = bundle.getString("configPremium", "")
        }

        // Implemantar evento click de botones
        btnRegresar.setOnClickListener { finish() }
    }

}