package com.cam.e.simulador_kot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.database.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var dbrefence:DatabaseReference
    private lateinit var tvP1:TextView
    private lateinit var rgR1:RadioGroup
    private lateinit var database:FirebaseDatabase
      private lateinit var Lista:MutableList<Preguntas>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        tvP1=findViewById(R.id.tvQuestion)
        rgR1=findViewById(R.id.rgRespuestas)
        database = FirebaseDatabase.getInstance()
        dbrefence = database.getReference("/Preguntas")
        dbrefence.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              var temp: Preguntas =p0.getValue<Preguntas>(Preguntas::class.java)!!
                Lista.add(temp)

            }
        })

    }

    private fun visualizarPreguntas() {
       for (item in Lista){
           tvP1.setText(item.Enunciado)
       }
    }

}

