package com.example.edenspa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Variables Globales y/o Constantes
    private val archivoXML = "EdenSpa"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciar()
    }

    /*
     * Configuración Inicial
     */
    private fun iniciar(){
        title = "              Bienvenid@ a EDEN SPA"

        //Implementar eventos clic de botones
        btnServDisp.setOnClickListener { irFacturaciónCli() }
        btnConfigServ.setOnClickListener { irABCServicios() }
        btnConfigPremi.setOnClickListener { irConfigPremium() }
        btnSalir.setOnClickListener { finish() }
    }

    /*
     *Lanzar activitys de configuración
     */

    private fun irFacturaciónCli(){
        val intent = Intent(this, FacturacionClientes::class.java)
        intent.putExtra("facturacionCli", archivoXML)

        startActivity(intent)
    }
    private fun irABCServicios() {
        val intenti = Intent(this, ABCServicios::class.java)
        intenti.putExtra("MainServicio", archivoXML)

        startActivity(intenti)
    }
    private fun irConfigPremium(){
        val i = Intent(this, ConfigServPremium::class.java)
        i.putExtra("configPremium", archivoXML)

        startActivity(i)
    }


}

