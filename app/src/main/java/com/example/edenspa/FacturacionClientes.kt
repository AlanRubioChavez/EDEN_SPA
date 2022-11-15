package com.example.edenspa

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_facturacion_clientes.*

class FacturacionClientes : AppCompatActivity() {

    //Variables Globales y/o Constantes
    private var archivoXML = ""
    private var aCods = arrayListOf<Int>()
    private var aDesc = arrayListOf<String>()
    private val BDSpa = "ServiciosSpa"          //Nombre de la Base de Datos
    private val TBServ = "servicios"            //Nombre de la tabla servicios
    var edtComPremium: EditText?=null
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
        var p = arrayListOf<Int>()
        var k = 0
        var j = 0

        // Crear conexión a la Base de Datos
        val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
        val bd = admin.writableDatabase
        val rg = bd.rawQuery("SELECT codigoid, description, precio, genero " +
                "FROM $TBServ " +
                "ORDER BY codigoid", null)


        //Recuperar el nombre del archivo .xml
        val bundle : Bundle? = intent.extras

        if (bundle != null){
            archivoXML = bundle.getString("facturacionCli", "")
        }

        // Implementar evento clic de botones

        switch1.setOnClickListener {
            if(bin == 0){
                bin = 1
            }else{
                bin = 0
            }
        }

        btnRegres.setOnClickListener {

            if (editTextTextPersonName.text.isNotEmpty() and (rbtnMujer.isChecked or rbtnHombre.isChecked) and edtLista.text.toString().isNotEmpty() and (bin == 0)){
                AlertDialog.Builder(this).apply {
                setTitle("¿Desea apartar cita?")
                setMessage("Total a Pagar:" + "$".padStart(5) + " " + total.toString())
                    setNegativeButton("Cancelar", null)
                setPositiveButton("Aceptar"){ _: DialogInterface, i: Int ->
                    setTitle("Cita Apartada Exitosamente")
                    setMessage("¡Te Esperamos en EDEN SPA!")
                    setNegativeButton("", null)
                    setPositiveButton("Ok"){ _: DialogInterface, i: Int ->
                        finish()
                    }.show()
                }
            }.show()

            }else
                if(((bin==1) or (bin==0)) and (editTextTextPersonName.text.isEmpty() or (!(rbtnMujer.isChecked) and !(rbtnHombre.isChecked)) or edtLista.text.toString().isEmpty()))
                    Toast.makeText(this, "¡Nombre, Genero y Servicio Obligatorio!", Toast.LENGTH_LONG).show()

            if (editTextTextPersonName.text.isNotEmpty() and (rbtnMujer.isChecked or rbtnHombre.isChecked) and edtLista.text.toString().isNotEmpty() and (bin == 1)){
                edtComPremium=findViewById(R.id.edtComPremium)
                var pref=getSharedPreferences("premier", Context.MODE_PRIVATE)
                var comision=pref.getString("comision",0f.toString())
                AlertDialog.Builder(this).apply {
                    setTitle("¿Desea apartar cita?")
                    setMessage("Total a Pagar:" + "$".padStart(5) + " " + total.toString() + "\n" + "Comision Premier:" + "    " + comision.toString().toFloat() + "%" + "\n" + "Importe Total:" + "$".padStart(5) + " " + (total + (total * (comision.toString().toFloat()/100))))
                    setNegativeButton("Cancelar", null)
                    setPositiveButton("Aceptar"){ _: DialogInterface, i: Int ->
                        setTitle("Cita Apartada Exitosamente")
                        setMessage("¡Te Esperamos en EDEN SPA!")
                        setNegativeButton("", null)
                        setPositiveButton("Ok"){ _: DialogInterface, i: Int ->
                            finish()
                        }.show()
                    }
                }.show()

            }
        }

        btnR.setOnClickListener { finish() }

        btnAgregar2.setOnClickListener {

            if (rg.moveToPosition(spnServi.selectedItemPosition)){

                if (p.isEmpty()){
                    sDesc += rg.getInt(0).toString().padEnd(5) +
                            rg.getString(1) +
                            "$".padStart(5) + " " + rg.getFloat(2).toString() + "\n"
                    suma += rg.getFloat(2)
                    total = suma
                    edtLista.setText(sDesc)
                    p.add(rg.getInt(0))
                } else {
                    for (i in p) {
                        if(p[j]==rg.getInt(0)){
                            k=1
                            break
                        }
                        j+=1
                    }

                    if (k==0){
                        sDesc += rg.getInt(0).toString().padEnd(5) +
                                rg.getString(1) +
                                "$".padStart(5) + " " + rg.getFloat(2).toString() + "\n"
                        suma += rg.getFloat(2)
                        total = suma
                        edtLista.setText(sDesc)
                        p.add(rg.getInt(0))
                        j=0
                    } else {
                        k=0
                        j=0
                        Toast.makeText(this, "¡Ya Agregaste ese Servicio!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        btnBorrar2.setOnClickListener{
            suma=0f
            sDesc=""
            edtLista.setText(sDesc)
            p.clear()
        }

        cargarServicios()

    }

    private fun cargarServicios(){
        aCods = arrayListOf<Int>()
        aDesc = arrayListOf<String>()


        // Creando la conexión a la Base de Datos
        val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
        val bd = admin.writableDatabase
        val qr = bd.rawQuery("SELECT codigoid, description, genero "+
                "FROM $TBServ " +
                "ORDER BY codigoid", null)

        // Recorrer los registros recuperados
        while (qr.moveToNext()){
            aCods.add(qr.getInt(0))
            aDesc.add(qr.getString(1))

        }

        // Crear el adapter para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, aDesc)
        spnServi.adapter = adapter

    }
}