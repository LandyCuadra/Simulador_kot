package com.cam.e.simulador_kot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*

class AnaliticaActivity : AppCompatActivity() {
    private lateinit var dbrefence: DatabaseReference
    private lateinit var tvP1: TextView
    private lateinit var imEnun:ImageView
    private lateinit var rgR1: RadioGroup
    private lateinit var rbR1: RadioButton
    private lateinit var rbR2: RadioButton
    private lateinit var rbR3: RadioButton
    private lateinit var rbR4: RadioButton
    private lateinit var imR1:ImageView
    private lateinit var imR2:ImageView
    private lateinit var imR3:ImageView
    private lateinit var imR4:ImageView
    private lateinit var progBar: ProgressBar
    private lateinit var database: FirebaseDatabase
    private var i:Int=0
    private var correctas:Int=0
    private var malas:Int=0
    private var Lista:ArrayList<Preguntas> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analitica)
        tvP1=findViewById(R.id.tvQuestionanalit)
        imEnun=findViewById(R.id.enunciadoimagen)
        rgR1=findViewById(R.id.rgRespuestasanalit)
        rbR1=findViewById(R.id.r1analit)
        rbR2=findViewById(R.id.r2analit)
        rbR3=findViewById(R.id.r3analit)
        rbR4=findViewById(R.id.r4analit)
        imR1=findViewById(R.id.imvR1)
        imR2=findViewById(R.id.imvR2)
        imR3=findViewById(R.id.imvR3)
        imR4=findViewById(R.id.imvR4)
        progBar=findViewById(R.id.progressQanalit)
        database = FirebaseDatabase.getInstance()
        dbrefence = database.getReference(intent.extras!!.getString("extra")!!)
        dbrefence.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(baseContext, "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {

                for(i in p0.children){
                    progBar.visibility= View.VISIBLE
                    val temp = i.getValue(Preguntas::class.java)
                    Lista.add(temp!!)

                }


                Toast.makeText(baseContext, Lista[1].Enunciado, Toast.LENGTH_SHORT).show()
                visualizarPreguntas()
                visualizarcontroles()
            }
        })

    }
    private fun visualizarcontroles(){
        tvP1.visibility=View.VISIBLE
        imEnun.visibility=View.VISIBLE
        rgR1.visibility=View.VISIBLE
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
        //Toast.makeText(this, rbR1.isChecked.toString(), Toast.LENGTH_SHORT).show()
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
        i++
        if(Lista.size>i) {
            visualizarPreguntas()

        }
        else
        {
            var inten = Intent(this,ResultadosActivity::class.java)
            inten.putExtra("buenas",correctas)
            inten.putExtra("todas",i)
            startActivity(inten)

        }
    }

    fun onPrev(v: View){


        if(i>0){
            if(correctas > 0)
                correctas--
            if(malas > 0)
                malas --

            i--
            visualizarPreguntas()
        }else if(i==0){

        }
    }

}
