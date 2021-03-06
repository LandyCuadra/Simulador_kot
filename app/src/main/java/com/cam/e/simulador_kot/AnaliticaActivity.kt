package com.cam.e.simulador_kot

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
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
    private var Lista:ArrayList<PreguntasAnaliticas> = ArrayList()
    private lateinit var resp:IntArray
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
                    val temp = i.getValue(PreguntasAnaliticas::class.java)
                    Lista.add(temp!!)

                }


                Toast.makeText(baseContext, Lista[1].Enunciado, Toast.LENGTH_SHORT).show()
                resp = IntArray(Lista.size)
                visualizarPreguntas()
                visualizarcontroles()
            }
        })

    }
    private fun visualizarcontroles(){
        tvP1.visibility=View.VISIBLE
        if (Lista[i].Ei!= ""){
        imEnun.visibility=View.VISIBLE
        }
        rgR1.visibility=View.VISIBLE
        progBar.visibility=View.INVISIBLE
    }
    private fun visualizarPreguntas() {
        tvP1.text= Lista[i].Enunciado
        if (Lista[i].Ei== ""){
            imEnun.visibility=View.INVISIBLE
        }
        if (Lista[i].Ei!= "")
            Glide.with(this).load(Lista[i].Ei).into(imEnun)
        Glide.with(this).load(Lista[i].rA).into(imR1)
        Glide.with(this).load(Lista[i].rB).into(imR2)
        Glide.with(this).load(Lista[i].rC).into(imR3)
        Glide.with(this).load(Lista[i].rD).into(imR4)
    }

    fun onNext(v:View){
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

        i++

        if(Lista.size>i) {
            if(resp[i]==0 ){
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
            if (i>0)
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
        }
    }
}
