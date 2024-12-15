package dev.kelompokceria.smart_umkm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import dev.kelompokceria.smartumkm.user.R
import dev.kelompokceria.smartumkm.user.databinding.FragmentFaqBinding

class FaqFragment : Fragment() {

    private lateinit var binding: FragmentFaqBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFaqBinding.inflate(layoutInflater)

        binding.backButton.setOnClickListener {
            // Navigate back to the previous screen
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.question1.setOnClickListener{
            if (binding.answer1.visibility == View.GONE){
                binding.answer1.visibility = View.VISIBLE
            } else {
                binding.answer1.visibility = View.GONE
            }
        }

        binding.question2.setOnClickListener{
            if (binding.answer2.visibility == View.GONE){
                binding.answer2.visibility = View.VISIBLE
            } else {
                binding.answer2.visibility = View.GONE
            }
        }

        binding.question3.setOnClickListener{
            if (binding.answer3.visibility == View.GONE){
                binding.answer3.visibility = View.VISIBLE
            } else {
                binding.answer3.visibility = View.GONE
            }
        }

        binding.question4.setOnClickListener{
            if (binding.answer4.visibility == View.GONE){
                binding.answer4.visibility = View.VISIBLE
            } else {
                binding.answer4.visibility = View.GONE
            }
        }

        hideBottomNavigationView()

        return binding.root
    }

     override fun onDestroy() {
        super.onDestroy()
        showBottomNavigationView()
    }

    private fun hideBottomNavigationView() {
        val bottomNavigationView = activity?.findViewById<MaterialCardView>(R.id.layoutNavUser)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun showBottomNavigationView() {
        val bottomNavigationView = activity?.findViewById<MaterialCardView>(R.id.layoutNavUser)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}