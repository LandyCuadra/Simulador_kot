package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ResultadosActivity : AppCompatActivity() {
 private lateinit var  tvResultado:TextView
    private lateinit var  tvConsejo:TextView
 private lateinit var bund:Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)
        tvResultado = findViewById(R.id.tvResultados)
        tvConsejo = findViewById(R.id.tvconsejos)
        bund=intent.extras!!
        val resultado:String = ""+bund.getInt("buenas")+"/"+bund.getInt("todas")
        tvResultado.text= resultado
    }
    fun onAceptarResultados(v:View){
        val intento = Intent(this,MenuActivity::class.java)
        startActivity(intento)
    }
    private fun aconsejar(){

        if(bund.getInt("buenas")==5)
            tvConsejo.text="Excelente sigue asi"
        else if (bund.getInt("buenas") >= 3)
            tvConsejo.text="Muy bien, pero aun puedes mejorar"
        else
            tvConsejo.text="Deberias esforzarte mas....."
    }
}
