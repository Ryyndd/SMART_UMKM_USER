package dev.kelompokceria.smartumkm.user.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.kelompokceria.smart_umkm.ui.AboutUsFragment
import dev.kelompokceria.smart_umkm.ui.FaqFragment
import dev.kelompokceria.smart_umkm.ui.MoreInfoFragment
import dev.kelompokceria.smartumkm.user.data.helper.Constant
import dev.kelompokceria.smartumkm.user.data.helper.PreferenceHelper
import dev.kelompokceria.smart_umkm.viewmodel.UserViewModel
import dev.kelompokceria.smartumkm.user.R
import dev.kelompokceria.smartumkm.user.data.helper.NetworkStatusViewModel
import dev.kelompokceria.smartumkm.user.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    private lateinit var sharedPref: PreferenceHelper
    private lateinit var networkStatusViewModel: NetworkStatusViewModel

    private var myData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        networkStatusViewModel = ViewModelProvider(this)[NetworkStatusViewModel::class.java]
        sharedPref = PreferenceHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val username = sharedPref.getString(Constant.PREF_USER_NAME)
        if (username == null) {
            Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
        } else {

            networkStatusViewModel.networkStatus.observe(viewLifecycleOwner) { isConnected ->
                if (isConnected) {
                    userViewModel.getUserByUsername(username)
                } else {
                    userViewModel.getUserByNameRoom(username)
                }

            }

            userViewModel.loggedInUser.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                     user.image.let {
                            Glide.with(binding.ivProfile.context)
                                .load(it)
                                .placeholder(R.drawable.picture) // Placeholder if image is unavailable
                                .into(binding.ivProfile)
                        }
                    binding.tvName.text = user.username
                }
            }
        }


        binding.btnLogout.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Apakah Anda yakin ingin logout ?")
                .setCancelable(false)
                .setPositiveButton("Ya") { _, _ ->
                    sharedPref.clear()
                    Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }

            val alert = dialogBuilder.create()
            alert.setTitle("Logout")
            alert.show()

        }

        binding.btnMI.setOnClickListener{
            val fragment = MoreInfoFragment()
            val pindah = parentFragmentManager.beginTransaction()
            pindah.replace(R.id.navigation_fragment, fragment)
            pindah.addToBackStack(null)
            pindah.commit()
        }

        binding.btnFAQ.setOnClickListener{
            val fragment = FaqFragment()
            val pindah = parentFragmentManager.beginTransaction()
            pindah.replace(R.id.navigation_fragment, fragment)
            pindah.addToBackStack(null)
            pindah.commit()
        }

        binding.btnAbout.setOnClickListener{
            val fragment = AboutUsFragment()
            val pindah = parentFragmentManager.beginTransaction()
            pindah.replace(R.id.navigation_fragment, fragment)
            pindah.addToBackStack(null)
            pindah.commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
