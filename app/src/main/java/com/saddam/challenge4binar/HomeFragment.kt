package com.saddam.challenge4binar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    private var dbNote : NoteDatabase? = null
    lateinit var repository: NoteRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = NoteRepository(requireContext())

        //set text username di home memakai data dari shared preference yang tersimpan
        val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "")
        home_username_text.text = "Halo, $username"

        //action button untuk tambah data
        fab_tambah.setOnClickListener {
            InputDialogFragment().show(childFragmentManager, "InputDialogFragment")
        }

        //setting view pada recycler view di home fragment
        dbNote = NoteDatabase.getInstance(requireContext())
        getDataNote()

        //action untuk logout
        logout.setOnClickListener {
            logout()
        }
    }

    //function untuk mengambil data dari noteDatabase
    private fun getDataNote() {
        //define layout manager
        rv_note.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //command for room database that return all of data
        val listNote = dbNote?.noteDao()?.getAllNote()

        GlobalScope.launch {
            activity?.runOnUiThread{
                listNote.let {
                    //set adapter
                    rv_note.adapter = NoteAdapter(it!!)
                }
            }
        }
    }

    //function for logout action
    private fun logout(){
        AlertDialog.Builder(requireContext())
            .setTitle("LOGOUT")
            .setMessage("Apakah anda yakin ingin logout?")
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                //clear shared preference, so the user must login again to access home after logging out
                val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
                val sf = sharedPreferences.edit()
                sf.clear()
                sf.apply()

                //reload activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }
}