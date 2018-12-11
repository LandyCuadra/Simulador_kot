package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var dbrefence:DatabaseReference
    private lateinit var tvP1:TextView
    private lateinit var rgR1:RadioGroup
    private lateinit var rbR1:RadioButton
    private lateinit var rbR2:RadioButton
    private lateinit var rbR3:RadioButton
    private lateinit var rbR4:RadioButton
    private lateinit var progBar:ProgressBar
    private lateinit var database:FirebaseDatabase
    private var i:Int=0
    private var correctas:Int=0
    private var malas:Int=0
    private lateinit var resp:IntArray
    private var Lista:ArrayList<Preguntas> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        resp = IntArray(6)
        tvP1=findViewById(R.id.tvQuestion)
        rgR1=findViewById(R.id.rgRespuestas)
        rbR1=findViewById(R.id.r1)
        rbR2=findViewById(R.id.r2)
        rbR3=findViewById(R.id.r3)
        rbR4=findViewById(R.id.r4)
        progBar=findViewById(R.id.progressQ)
        database = FirebaseDatabase.getInstance()
        dbrefence = database.getReference(intent.extras!!.getString("extra")!!)
        dbrefence.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(baseContext, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for(i in p0.children){
                   progBar.visibility=View.VISIBLE
                    val temp = i.getValue(Preguntas::class.java)
                    Lista.add(temp!!)

                }

                visualizarPreguntas()
                 visualizarcontroles()
            }
        })

    }
    private fun visualizarcontroles(){
        tvP1.visibility=View.VISIBLE
        rgR1.visibility=View.VISIBLE
        next.visibility=View.VISIBLE
        prev.visibility=View.VISIBLE
        progBar.visibility=View.INVISIBLE
    }
    private fun visualizarPreguntas() {
        tvP1.text= Lista[i].Enunciado
        rbR1.text= Lista[i].rA
        rbR2.text= Lista[i].rB
        rbR3.text= Lista[i].rC
        rbR4.text= Lista[i].rD
       }

    fun onNext(v:View){

        if(Lista.size>i && i>=0) {
            i++

            if (rbR1.isChecked and  Lista[i].rCorrecta.contentEquals("Respuesta A")){
                correctas++
            }
            else if (rbR2.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta B")){
                correctas++
            }
            else if (rbR3.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta C")){
                correctas++
            }
            else if (rbR4.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta D")){
                correctas++
            }
            else {
                malas++
            }

            resp.set(i, rgR1.checkedRadioButtonId)
            Toast.makeText(this, resp[i].toString(),Toast.LENGTH_SHORT).show()

            if(resp[i]==0){
                rgR1.clearCheck()
            }else{
                rgR1.check(resp[i])
            }

            visualizarPreguntas()

        }
        else
        {
            var inten =Intent(this,ResultadosActivity::class.java)
            inten.putExtra("buenas",correctas)
            inten.putExtra("todas",i)
            startActivity(inten)
            finish()
        }

    }

    fun onPrev(v: View){

        if(i>=0){
            i--

            if(resp[i]==-1 || resp[i]==0){
                rgR1.clearCheck()
            }else{
                rgR1.check(resp[i])
            }

        if (rbR1.isChecked and  Lista[i].rCorrecta.contentEquals("Respuesta A")){
            correctas--
        }
        else if (rbR2.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta B")){
            correctas--
        }
        else if (rbR3.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta C")){
            correctas--
        }
        else if (rbR4.isChecked and Lista[i].rCorrecta.contentEquals("Respuesta D")){
            correctas--
        }
        else {
            malas--
        }







            visualizarPreguntas()
        }else if(i==0){

        }
    }
}

