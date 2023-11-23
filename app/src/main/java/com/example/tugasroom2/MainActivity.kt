package com.example.tugasroom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugasroom2.databinding.ActivityMainBinding
import com.example.tugasroom2.databinding.ActivitySecondBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        executorService = Executors.newSingleThreadExecutor()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            buttonCreate.setOnClickListener{
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun getAllNotes(){
        mNotesDao.allNotes.observe(this){
            notes ->
            val adapter = NoteAdapter(notes){
                note ->
                delete(note)
            }

            with(binding){
                rvRoom.apply {
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    override fun onResume(){
        super.onResume()
        getAllNotes()
    }

    private fun delete(note: Note){
        executorService.execute{ mNotesDao.delete(note)}
    }


}