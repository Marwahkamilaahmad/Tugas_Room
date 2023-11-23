package com.example.tugasroom2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasroom2.databinding.ItemListBinding
import java.util.concurrent.ExecutorService

typealias onClickDelete = (Note) -> Unit

class NoteAdapter(private val listItem : List<Note>, private val onClickDelete: onClickDelete) : RecyclerView.Adapter<NoteAdapter.ItemNotesViewHolder>() {

    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService

    inner class ItemNotesViewHolder(private val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Note){
            with(binding){
                txtDeskripsi.text = data.description
                txtJudul.text = data.title
                txtJumlah.text = data.jumlah
                txtTanggal.text = data.tanggal

                buttonUpdate.setOnClickListener{
                        val intent = Intent(binding.root.context, SecondActivity::class.java)
                            .apply {
                                putExtra("extraid", data.id)
                                putExtra("extrajumlah", data.jumlah)
                                putExtra("extratanggal", data.tanggal)
                                putExtra("extradeskripsi", data.description)
                                putExtra("extratitle", data.title)
                            }
                            binding.root.context.startActivity(intent)
                    }

                buttonDelete.setOnClickListener {
                    onClickDelete(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemNotesViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemNotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ItemNotesViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    private fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    private fun update(note : Note){
        executorService.execute{ mNotesDao.update(note)}
    }



}