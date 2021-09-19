package com.slicedwork.tmdbmovies.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.slicedwork.tmdbmovies.R
import com.slicedwork.tmdbmovies.databinding.FragmentLoginBinding
import com.slicedwork.tmdbmovies.util.TmdbApplication

class LoginFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

        return fragmentLoginBinding.root
    }

    override fun onResume() {
        super.onResume()

        fragmentLoginBinding.incEditSpinner.spLanguages.onItemSelectedListener = this
        fragmentLoginBinding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        fragmentLoginBinding.apply {
            if (view == btnLogin) {
                setApiKey(incEditSpinner.editApiKey.text.toString())
                goToMovies(view)
            }
        }
    }

    private fun goToMovies(view: View) {
        view.findNavController().navigate(
            LoginFragmentDirections
                .actionLoginFragmentToMoviesFragment()
        )
    }

    private fun setApiKey(apiKey: String) {
        TmdbApplication.apiKey = apiKey
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        TmdbApplication.language = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}