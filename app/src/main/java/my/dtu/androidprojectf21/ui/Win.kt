package my.dtu.androidprojectf21.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import my.dtu.androidprojectf21.R
import my.dtu.androidprojectf21.databinding.FragmentWinBinding


class Win : Fragment() {

    private var _binding: FragmentWinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.playAgain.setOnClickListener{
            findNavController().navigate(R.id.action_win_to_game)
        }
    }


}