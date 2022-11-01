package com.gabo.gk.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.gabo.gk.R
import com.gabo.gk.base.BaseFragment
import com.gabo.gk.databinding.FragmentSplashscreenBinding

class SplashScreenFragment :
    BaseFragment<FragmentSplashscreenBinding>(FragmentSplashscreenBinding::inflate) {

    override fun setupView() {
        Handler(Looper.myLooper()!!).postDelayed({
            with(findNavController()) {
                popBackStack(R.id.splashScreenFragment, true)
                navigate(R.id.loginFragment)
            }
        }, 1500)
    }
}