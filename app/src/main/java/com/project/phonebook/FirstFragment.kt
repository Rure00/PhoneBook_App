package com.project.phonebook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class FirstFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<Button>(R.id.toThirdBtn).setOnClickListener{
            Log.d("FIRST", "Clicked Button")
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.main_fcv, ThirdFragment(), "THIRD").commitNow()
            //parentFragmentManager.beginTransaction().add(R.id.main_fcv, ThirdFragment(), "THIRD").commitNow()
            //childFragmentManager.beginTransaction().add(ThirdFragment(), "THIRD").commitNow()
        }
    }


}