package com.example.edenspa

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_facturacion_clientes.*

class FacturacionClientes : AppCompatActivity() {

    //Variables Globales y/o Constantes
    private var archivoXML = ""
    private val BDSpa = "ServiciosSpa"          //Nombre de la Base de Datos
    private val TBServ = "servicios"            //Nombre de la tabla servicios
    var premier = 0.30f
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
        var bin = 0

        // Crear conexión a la Base de Datos
        val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
        val bd = admin.writableDatabase
        val rg = bd.rawQuery("SELECT codigoid, description, precio, genero " +
                "FROM $TBServ " +
                "ORDER BY codigoid", null)

        //Recorrer articulos recuperados
        while (rg.moveToNext()){
            sDesc += rg.getString(1).padEnd(35) + " " +
                    rg.getInt(0).toString().padStart(3) + " " +
                    rg.getFloat(2).toString().padStart(10) + "\n"
            suma += rg.getFloat(2)
            total = suma


        }

        // Asignar la cadena de texto al EditText
        sDesc += "\n" + "Total a Pagar:".repeat(1) + total.toString().padStart(7)
        edtLista.setText(sDesc)
        var Eli = sDesc
        sDesc += "\n" + "Comision Premier:".repeat(1) + "30%".padStart(5)
        sDesc += "\n" + "Importe Total:".repeat(1) + "  " + (total + (total * premier))
        var Ag = sDesc

        //Recuperar el nombre del archivo .xml
        val bundle : Bundle? = intent.extras

        if (bundle != null){
            archivoXML = bundle.getString("facturacionCli", "")
        }

        // Implementar evento clic de botones

        switch1.setOnClickListener {
            if(bin == 0){
                edtLista.setText(Ag)
                bin = 1
            }else{
                edtLista.setText(Eli)
                bin = 0
            }
        }

        btnRegres.setOnClickListener {
            if (editTextTextPersonName.text.isNotEmpty() and (rbtnMujer.isChecked or rbtnHombre.isChecked)){
                AlertDialog.Builder(this).apply {
                setTitle("Cita Apartada Exitosamente")
                setMessage("¡Te esperamos en EDEN SPA!")
                setNegativeButton("Ok"){ _: DialogInterface, i: Int ->
                    finish()
                }.show()
            }

            }else
                Toast.makeText(this, "¡Nombre y Genero Obligatorio!", Toast.LENGTH_LONG).show()
        }

        btnR.setOnClickListener { finish() }

    }
}