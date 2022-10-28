package com.example.edenspa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_config_serv_premium.*
import kotlinx.android.synthetic.main.activity_facturacion_clientes.*

class FacturacionClientes : AppCompatActivity() {

    //Variables Globales y/o Constantes
    private var archivoXML = ""
    private val BDSpa = "ServiciosSpa"          //Nombre de la Base de Datos
    private val TBServ = "servicios"            //Nombre de la tabla servicios
    private var premium = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facturacion_clientes)
        inicio ()
    }

    /*
     *Configuración inicial de listados
     */

    private fun inicio (){
        title = "Facturación Clientes"

        var sDesc = ""
        var suma = 0f
        var total = 0f

        // Crear conexión a la Base de Datos
        val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
        val bd = admin.writableDatabase
        val rg = bd.rawQuery("SELECT codigoid, description, precio, genero " +
                "FROM $TBServ " +
                "ORDER BY description", null)

        //Recorrer articulos recuperados
        while (rg.moveToNext()){
            sDesc += rg.getString(1).padEnd(35) + " " +
                    rg.getInt(0).toString().padStart(3) + " " +
                    rg.getFloat(2).toString().padStart(10) + "\n"
            suma += rg.getFloat(2)
            total = suma * premium


        }

        // Asignar la cadena de texto al EditText
        sDesc += "\n" + "Servicios por Pagar ->  ".repeat(35) + total.toString().padStart(10)
        edtLista.setText(sDesc)

        //Recuperar el nombre del archivo .xml
        val bundle : Bundle? = intent.extras

        if (bundle != null){
            archivoXML = bundle.getString("facturacionCli", "")
        }

        // Implemantar evento clic de botones

        btnRegres.setOnClickListener { finish() }


    }
}