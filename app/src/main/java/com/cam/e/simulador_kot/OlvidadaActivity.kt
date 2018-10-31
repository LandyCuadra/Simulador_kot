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

class OlvidadaActivity : AppCompatActivity() {

    private lateinit var txtCorreo:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var progressbar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidada)

        txtCorreo=findViewById(R.id.txtCorreo)

        auth= FirebaseAuth.getInstance()

        progressbar= findViewById(R.id.progressBar3)
    }

    fun enviar(view: View){
        val correo= txtCorreo.text.toString()
        if (!TextUtils.isEmpty(correo)){
            progressbar.visibility=View.VISIBLE
            auth.sendPasswordResetEmail(correo)
                    .addOnCompleteListener(this){
                        task ->

                        if(task.isSuccessful){
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                        else{
                            Toast.makeText(this, "Error al enviar la contraseña", Toast.LENGTH_LONG).show()
                            progressbar.visibility=View.GONE
                        }
                    }
        }

    }
}
