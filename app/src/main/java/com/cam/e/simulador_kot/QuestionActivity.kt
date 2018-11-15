package com.cam.e.simulador_kot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QuestionActivity : AppCompatActivity() {

    private lateinit var dbrefence:DatabaseReference
    private lateinit var tvP1:TextView
    private lateinit var rgR1:RadioGroup
    private lateinit var database:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        tvP1=findViewById(R.id.tvQuestion)
        rgR1=findViewById(R.id.rgRespuestas)
        database = FirebaseDatabase.getInstance()
        dbrefence = database.reference.child("Pregunta")
        //visualizarPreguntas()
    }

    /*private fun visualizarPreguntas(){
       dbrefence.child("1")
    }*/

}
