package com.example.edenspa

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a_b_c_servicios.*

class ABCServicios : AppCompatActivity() {

    //Variables Globales y/o Constantes

    private var archivoXML = ""
    private var aCods = arrayListOf<Int>()      //Arreglo spinner código servicio
    private var aDesc = arrayListOf<String>()   //Arreglo spinner descripción
    private var aGenero = 0
    private val BDSpa = "ServiciosSpa"          //Nombre de la Base de Datos
    private val TBServ = "servicios"            //Nombre de la table Servicios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_b_c_servicios)
        iniciar ()
    }

    /*
     *Configuración inicial
     */
    private fun iniciar(){
        title = "Configuración Servicios"

        //Recuperar el nombre del archivo .xml
        val bundle : Bundle? = intent.extras

        if (bundle != null) {
            archivoXML = bundle.getString("MainServicio","")


        }
        // Implemantando evento clic de botones
        btnAgregar.setOnClickListener { agregarServ() }
        btnRegresa.setOnClickListener { finish() }
        btnSave.setOnClickListener { guardarServ()}
        btnVer.setOnClickListener { verSer() }
        btnBorrar.setOnClickListener { borrarServ() }

        cargarServicios()

    }

    /*
     * Eliminar servicio
     */
    private fun borrarServ(){
        if (edtCodigo.text.isNotEmpty()){
            val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
            val bd = admin.writableDatabase

            bd.delete(TBServ, "codigoid = ${edtCodigo.text}" , null)

            Toast.makeText(this, "¡Servicio Eliminado!", Toast.LENGTH_LONG).show()

            cargarServicios()
        }
    }

    private fun limpiarDatos(){
        edtCodigo.setText("")
        edtDescrip.setText("")
        edtPreServ.setText("")

        // Quitar cualquier selección
        spServ.setSelection(-1)
    }
    /*
     * Preparar para agregar
     */
    private fun agregarServ(){
        limpiarDatos()
    }

    private fun guardarServ(){
        if (edtDescrip.text.isNotEmpty()){
            var codigo = 0
            var preSer = 0f



            //Validando que el código y el precio no sean nulos
            if (edtCodigo.text.isNotEmpty())
                codigo = edtCodigo.text.toString().toInt()
            if (edtPreServ.text.isNotEmpty())
                preSer = edtPreServ.text.toString().toFloat()

            // Conectando a la Base de Datos
            val admin = AdminSQLiteOpenHelper(this, BDSpa , null, 1)

            // Abrir la Base de Datos
            val bd = admin.writableDatabase
            val rg = ContentValues()             // Spoller, crear un regisrto o modificarlo
            rg.put("description", edtDescrip.text.toString())
            rg.put("precio", preSer)
            rg.put("genero", aGenero)

            if (codigo == 0){                      // Artículo Nuevo
                bd.insert(TBServ, null, rg)
                Toast.makeText(this, "¡Servicio Agregado con Exito!", Toast.LENGTH_LONG).show()
            }
            else {
                bd.update(TBServ, rg, "codigoid = $codigo", null)
                Toast.makeText(this, "¡Servicio Actualizado con Exito!", Toast.LENGTH_LONG).show()
            }

            bd.close()

            cargarServicios()


        }else
            Toast.makeText(this, "La Descripción es Obligatoria", Toast.LENGTH_LONG).show()
    }

    /*
     * Cargar tabla de servicios a los arreglos y al spinner
     */
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
        spServ.adapter = adapter

        verSer() // Desplegar la información del primer elemento del spinner

    }

    /*
     * Buscar el servicio que se está seleccionado en el spinner
     */
    private fun verSer(){

        if ( spServ.selectedItemPosition >= 0){
            val admin = AdminSQLiteOpenHelper(this, BDSpa, null, 1)
            val bd = admin.writableDatabase
            val rg = bd.rawQuery("SELECT * " +
                    "FROM $TBServ " +
                    "WHERE codigoid = ${aCods[spServ.selectedItemPosition]}", null)
            if (rg.moveToFirst()){
                edtCodigo.setText(rg.getInt(0).toString())
                edtDescrip.setText(rg.getString(1))
                edtPreServ.setText(rg.getFloat(2).toString())

            }
        }
    }


}