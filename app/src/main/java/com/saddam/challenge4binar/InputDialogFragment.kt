package com.saddam.challenge4binar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_input_dialog.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class InputDialogFragment : DialogFragment() {
    private var dbNote : NoteDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbNote = NoteDatabase.getInstance(requireContext())

        //action for button "tambah data"
        tambah_button_input.setOnClickListener {
            GlobalScope.async {
                //get judul and catatan value
                val judul = tambah_input_judul.text.toString()
                val catatan = tambah_input_catatan.text.toString()
                //command for room database
                val command = dbNote?.noteDao()?.insertNote(Note(null, judul, catatan))
                activity?.runOnUiThread {
                    if(command != 0.toLong()){
                        Toast.makeText(requireContext(), "Catatan berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Catatan gagal ditambahkan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dismiss()
        }

    }

    override fun onDetach() {
        super.onDetach()
        activity?.recreate()
    }
}