package com.saddam.challenge4binar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_edit_data.view.*
import kotlinx.android.synthetic.main.dialog_delete_data.view.*
import kotlinx.android.synthetic.main.item_adapter_note.view.*
import com.saddam.challenge4binar.NoteRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class NoteAdapter(private val listNote : List<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var dbNote : NoteDatabase? = null
    //Mendefinisikan ViewHolder Class
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_note, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_judul.text = "Judul : \n${listNote[position].judul}"
        holder.itemView.tv_catatan.text = "Catatan : \n${listNote[position].catatan}"

        //Delete data
        holder.itemView.button_delete.setOnClickListener {
            dbNote = NoteDatabase.getInstance(it.context)

            //buat custom dialog untuk proses delete
            val customDialogDelete = LayoutInflater.from(it.context)
                .inflate(R.layout.dialog_delete_data, null, false)
            val deleteDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogDelete)
                .create()

            //cancel delete action
            customDialogDelete.delete_dialog_button_cancel.setOnClickListener {
                deleteDataDialog.dismiss()
            }

            //delete action button
            customDialogDelete.delete_dialog_button_delete.setOnClickListener {
                GlobalScope.async {

                    //command for room database
                    val command = dbNote?.noteDao()?.deleteDataNote(listNote[position])

                    //check if delete process worked or not
                    (customDialogDelete.context as MainActivity).runOnUiThread{
                        if(command != 0){
                            Toast.makeText(customDialogDelete.context, "Catatan ${listNote[position].judul} berhasil dihapus", Toast.LENGTH_SHORT).show()
                            //recreate activity after delete process
                            (customDialogDelete.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(customDialogDelete.context, "Catatan ${listNote[position].catatan} gagal dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            deleteDataDialog.show()
        }

        //edit data
        holder.itemView.button_edit.setOnClickListener {
            dbNote = NoteDatabase.getInstance(it.context)

            //create dialog for edit action
            val customDialogEdit = LayoutInflater.from(it.context)
                .inflate(R.layout.dialog_edit_data, null, false)
            val editDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogEdit)
                .create()

            //initialize edit text with previous "judul" and "catatan"
            customDialogEdit.edit_input_judul.setText(listNote[position].judul)
            customDialogEdit.edit_input_catatan.setText(listNote[position].catatan)

            //edit action button
            customDialogEdit.edit_button_update_data.setOnClickListener {
                //get new data
                val newJudul = customDialogEdit.edit_input_judul.text.toString()
                val newCatatan = customDialogEdit.edit_input_catatan.text.toString()

                //re-initialize data of listCatatan that in current position
                listNote[position].judul = newJudul
                listNote[position].catatan = newCatatan

                GlobalScope.async {
                    //command for room database
                    val command = dbNote?.noteDao()?.updateDataNote(listNote[position])

                    //check if edit process worked or not
                    (customDialogEdit.context as MainActivity).runOnUiThread{
                        if(command != 0){
                            Toast.makeText(it.context, "Catatan berhasil diupdate", Toast.LENGTH_SHORT).show()
                            //recreate activity
                            (customDialogEdit.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Catatan gagal diupdate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            editDataDialog.show()
        }


    }

    override fun getItemCount(): Int {
        return listNote.size
    }
}