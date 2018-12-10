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

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario:EditText
    private lateinit var txtContraseña:EditText
    private lateinit var progressbar:ProgressBar
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario=findViewById(R.id.txtCorreo)
        txtContraseña=findViewById(R.id.txtContraseña)

        progressbar=findViewById(R.id.progressBar2)

        auth= FirebaseAuth.getInstance()

    }

    fun olvidada(view: View){
        startActivity(Intent(this, OlvidadaActivity::class.java))
    }

    fun registrar(view:View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun Login(view:View){
        LoginUsuario()
    }

    private fun LoginUsuario(){
        val usuario:String=txtUsuario.text.toString()
        val contraseña:String=txtContraseña.text.toString()

        if(!TextUtils.isEmpty(usuario) && !TextUtils.isEmpty(contraseña)){
            progressbar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(usuario, contraseña)
                    .addOnCompleteListener(this){
                        task ->

                        if(task.isSuccessful){
                            ingresar()
                        }
                        else{
                            Toast.makeText(this, "Error en la autenticacion", Toast.LENGTH_LONG).show()
                            progressbar.visibility=View.GONE
                        }
                    }
        }
    }

    private fun ingresar(){
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}
