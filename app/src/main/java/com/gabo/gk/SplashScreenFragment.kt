package com.gabo.gk

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gabo.gk.databinding.FragmentSplashscreenBinding

class SplashScreenFragment : Fragment() {
    private var binding: FragmentSplashscreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashscreenBinding.inflate(inflater, container, false)
        return binding!!.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToRegisterFragment())
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}