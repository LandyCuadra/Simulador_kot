package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre:EditText
    private lateinit var txtApellido:EditText
    private lateinit var txtCorreo:EditText
    private lateinit var txtContraseña:EditText
    private lateinit var progressbar:ProgressBar
    private lateinit var dbreference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombre=findViewById(R.id.txtNombre)
        txtApellido=findViewById(R.id.txtApellido)
        txtCorreo=findViewById(R.id.txtCorreo)
        txtContraseña=findViewById(R.id.txtContraseña)

        progressbar= findViewById(R.id.progressBar)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbreference= database.reference.child("Usuario")

    }

    fun registrar(view:View){
        createNewAccount()
    }

    private fun createNewAccount(){
        val nombre:String= txtNombre.text.toString()
        val apellido:String= txtApellido.text.toString()
        val correo:String= txtCorreo.text.toString()
        val contraseña:String= txtContraseña.text.toString()

        if (!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contraseña)){
            progressbar.visibility=View.VISIBLE;

            auth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this) {
                        task ->

                        if (task.isComplete){
                            val usuario:FirebaseUser?=auth.currentUser
                            verificarCorreo(usuario)

                            val usuarioBD = usuario?.uid?.let { dbreference.child(it) }

                            usuarioBD?.child("Nombre")?.setValue(nombre)
                            usuarioBD?.child("Apellido")?.setValue(apellido)
                            Login()
                        }

                    }
        }
    }

    private fun verificarCorreo(user:FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                    task ->

                    if(task.isComplete){
                        Toast.makeText(this, "Correo Enviado", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Error al enviar", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun Login(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
