package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
    fun onClick(v: View){
      var intent:Intent = Intent(this,QuestionActivity::class.java)
        var extra:String=""
        if (v.id==R.id.btnMatematica){
          extra="Numericas"
        }
        if (v.id==R.id.btnEspañol){
            extra="Verbales"
        }
        if (v.id==R.id.btnAnalitica){
            extra="Analitica"
            intent= Intent(this,AnaliticaActivity::class.java)
        }
          intent.putExtra("extra",extra)
        startActivity(intent)
    }
}
