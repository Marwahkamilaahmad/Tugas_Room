package com.example.tugasroom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasroom2.databinding.ActivityMainBinding
import com.example.tugasroom2.databinding.ActivitySecondBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySecondBinding
    private lateinit var mNotesDao: NoteDao
    private var updateId: Int=0
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        executorService = Executors.newSingleThreadExecutor()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data = intent.extras
        updateId = data?.getInt("extraid") ?: 0

        with(binding) {
                editDeskripsi.setText(data?.getString("extradeskripsi"))
                editJudul.setText(data?.getString("extratitle"))
                editJumlah.setText(data?.getString("extrajumlah"))
                editTanggal.setText(data?.getString("extratanggal"))


            btnSave.setOnClickListener {
                insert(Note(
                    title = editJudul.text.toString(),
                    jumlah = editJumlah.text.toString(),
                    description = editDeskripsi.text.toString(),
                    tanggal = editTanggal.text.toString()
                ))
                val intentToHome = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(intentToHome)
                finish()
            }

            buttonEdit.setOnClickListener {
                update(Note(
                    id = updateId,
                    title = editJudul.getText().toString(),
                    jumlah = editJumlah.getText().toString(),
                    description = editDeskripsi.getText().toString(),
                    tanggal = editTanggal.getText().toString()
                )
                )
                val intentToHomeA = Intent(this@SecondActivity, MainActivity::class.java)
                startActivity(intentToHomeA)
                finish()
            }

        }
    }


    private fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    private fun update(note : Note){
        executorService.execute{ mNotesDao.update(note)}
    }

    private fun delete(note: Note){
        executorService.execute{ mNotesDao.delete(note)}
    }

}