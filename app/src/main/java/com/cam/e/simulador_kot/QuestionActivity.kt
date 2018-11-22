package com.cam.e.simulador_kot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.firebase.database.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var dbrefence:DatabaseReference
    private lateinit var tvP1:TextView
    private lateinit var rgR1:RadioGroup
    private lateinit var rbR1:RadioButton
    private lateinit var rbR2:RadioButton
    private lateinit var rbR3:RadioButton
    private lateinit var rbR4:RadioButton
    private lateinit var database:FirebaseDatabase
    private var i:Int=0
    private var correctas:Int=0
    private var malas:Int=0
      private  var Lista:MutableList<Preguntas> = mutableListOf<Preguntas>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        tvP1=findViewById(R.id.tvQuestion)
        rgR1=findViewById(R.id.rgRespuestas)
        rbR1=findViewById(R.id.r1)
        rbR2=findViewById(R.id.r2)
        rbR3=findViewById(R.id.r3)
        rbR4=findViewById(R.id.r4)

        database = FirebaseDatabase.getInstance()
        dbrefence = database.getReference("/"+intent.extras["extra"])
        dbrefence.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              //var temp: Preguntas =p0.getValue<Preguntas>(Preguntas::class.java)!!
                //Lista.add(temp)

            }
        })
        visualizarPreguntas()
    }

    private fun visualizarPreguntas() {
        tvP1.text=Lista[i].Enunciado
        rbR1.text= Lista[i].R1
        rbR2.text= Lista[i].R2
        rbR3.text= Lista[i].R3
        rbR4.text= Lista[i].R4
        i++;
       }
    private fun onNext(v:View){
        if (rbR1.isSelected and Lista[i].RC.equals("Respuesta A")){
             correctas++
        }
        else if (rbR2.isSelected and Lista[i].RC.equals("Respuesta B")){
            correctas++
        }
        else if (rbR3.isSelected and Lista[i].RC.equals("Respuesta C")){
            correctas++
        }
        else if (rbR4.isSelected and Lista[i].RC.equals("Respuesta D")){
            correctas++
        }
        else
            malas++
        if(Lista.size>i)
        visualizarPreguntas()
        else
        {
            
        }
    }
}

