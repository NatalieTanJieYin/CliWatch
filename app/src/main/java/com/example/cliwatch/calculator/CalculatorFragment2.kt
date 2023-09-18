package com.example.cliwatch.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cliwatch.calculator.data.CalculatorDatabase
import com.example.cliwatch.calculator.data.User
import com.example.cliwatch.databinding.FragmentCalculator2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalculatorFragment2 : Fragment() {
    private lateinit var calDb : CalculatorDatabase
    lateinit var binding : FragmentCalculator2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculator2Binding.inflate(inflater, container, false)
        calDb = CalculatorDatabase.getDatabase(requireContext())

        binding.btnWriteData.setOnClickListener {
            writeData()
        }

        binding.btnReadData.setOnClickListener {
            readData()
        }

        binding.btnDeleteAll.setOnClickListener {

            lifecycleScope.launch {
                deleteAllData()
            }

        }
        return binding.root
    }

    private fun writeData(){

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if(firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()     ) {
            val user = User(
                null, firstName, lastName, rollNo.toInt()
            )
            lifecycleScope.launch(Dispatchers.IO) {
                calDb.userDao().insert(user)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(
                requireContext(),
                "Successfully written",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(requireContext(), "Please Enter Data", Toast.LENGTH_SHORT).show()
        }

    }

    private fun readData(){

        val rollNo = binding.etRollNoRead.text.toString()

        if (rollNo.isNotEmpty()){

            lifecycleScope.launch(Dispatchers.IO) {
                val user = calDb.userDao().findByRoll(rollNo.toInt())
                withContext(Dispatchers.Main) {
                    displayData(user)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please enter the data", Toast.LENGTH_SHORT).show()
        }

    }

    private suspend fun displayData(user: User){

        withContext(Dispatchers.Main){

            binding.tvFirstName.text = user.firstName
            binding.tvLastName.text = user.lastName
            binding.tvRollNo.text = user.rollNo.toString()

        }

    }

    private fun deleteAllData() {
        lifecycleScope.launch(Dispatchers.IO) {
            calDb.userDao().deleteAll()
        }
    }
}