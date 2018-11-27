package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
    private var Lista:ArrayList<Preguntas> = ArrayList()
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
        dbrefence = database.getReference(intent.extras!!.getString("extra")!!)
        dbrefence.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                for(i in p0.children){

                    val temp = i.getValue(Preguntas::class.java)
                    Lista.add(temp!!)

                }


                Toast.makeText(baseContext, Lista[1].Enunciado, Toast.LENGTH_SHORT).show()
                visualizarPreguntas()

            }
        })

    }

    private fun visualizarPreguntas() {
        tvP1.text= Lista[i].Enunciado
        rbR1.text= Lista[i].rA
        rbR2.text= Lista[i].rB
        rbR3.text= Lista[i].rC
        rbR4.text= Lista[i].rD
        i++
       }
    fun onNext(v:View){
        //Toast.makeText(this, rbR1.isChecked.toString(), Toast.LENGTH_SHORT).show()

        if (rbR1.isChecked and  Lista[i-1].rCorrecta.contentEquals("Respuesta A")){
             correctas++
        }
        else if (rbR2.isChecked and Lista[i-1].rCorrecta.contentEquals("Respuesta B")){
            correctas++
        }
        else if (rbR3.isChecked and Lista[i-1].rCorrecta.contentEquals("Respuesta C")){
            correctas++
        }
        else if (rbR4.isChecked and Lista[i-1].rCorrecta.contentEquals("Respuesta D")){
            correctas++
        }
        else {
            malas++
        }
        if(Lista.size>i) {
            visualizarPreguntas()
        }
        else
        {
            var inten:Intent=Intent(this,ResultadosActivity::class.java)
            inten.putExtra("buenas",correctas)
            inten.putExtra("todas",i)
            startActivity(inten)

        }
    }
}

