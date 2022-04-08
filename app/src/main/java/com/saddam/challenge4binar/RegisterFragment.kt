package com.saddam.challenge4binar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class RegisterFragment : Fragment() {
    private var dbUser : UserDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //action for button register
        register_button_daftar.setOnClickListener {
            //checking semua field, semua field required
            if(register_input_username.text.isNotEmpty() &&
                register_input_email.text.isNotEmpty() &&
                register_input_password.text.isNotEmpty() &&
                register_input_konfirmasi_password.text.isNotEmpty()){

                //check the similarity between the password field and confirm password
                if(register_input_password.text.toString() != register_input_konfirmasi_password.text.toString()){
                    Toast.makeText(requireContext(), "Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
                }else{
                    //jika sama, maka input data user ke userdatabase
                    inputUserData()
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Ada data yang belum terisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //function untuk input data ke user database
    private fun inputUserData(){
        dbUser = UserDatabase.getInstance(requireContext())
        GlobalScope.async {
            //get all data from edit text
            val dataUsername = register_input_username.text.toString()
            val dataEmail = register_input_email.text.toString()
            val dataPassword = register_input_password.text.toString()
            val dataKonfirmasiPassword = register_input_konfirmasi_password.text.toString()

            //command for the room database to insert new user
            val command = dbUser?.userDaoInterface()?.insertUser(User(null, dataUsername, dataEmail, dataPassword, dataKonfirmasiPassword))

            //cek jika command bekerja atau tidak
            activity?.runOnUiThread{
                if(command != 0.toLong()){
                    Toast.makeText(requireContext(), "Proses register berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Proses register gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}