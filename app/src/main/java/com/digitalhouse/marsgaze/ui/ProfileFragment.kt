package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentProfileBinding
import com.digitalhouse.marsgaze.helper.OkAndErrorSnack
import com.digitalhouse.marsgaze.helper.SnackCreator
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.viewmodels.profile.ProfileViewModel
import com.digitalhouse.marsgaze.viewmodels.profile.ProfileViewModelFactory
import com.google.android.material.textfield.TextInputLayout


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private val editableTexts: Array<TextInputLayout> by lazy {
        arrayOf(
            binding.tiName,
            binding.tiPassword
        )
    }

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(
            Session.getInstance(
                MarsGazeDB.getDatabase(requireContext()),
                AfterFavoriteAction(requireContext())
            )
        )
    }

    private val snackCreator: SnackCreator by lazy {
        OkAndErrorSnack(
            binding.root,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Precisamos customizar o tema desse fragmento para que os campos fiquem conforme o
        // desejado.
        // FIXME os campos não modificaveis não estão distintos dos modificaveis
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.MaterialTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        val view: View = localInflater.inflate(R.layout.fragment_profile, container, false)

        _binding = FragmentProfileBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atualiza os dados na tela
        setUserData(viewModel.getUser())
        binding.btnEditar.setOnClickListener(EditOrSaveManager()::action)

        viewModel.userUpdateStatus.observe(viewLifecycleOwner, snackCreator::showSnack)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUserData(user: User) {
        binding.tiDataAtrs.editText!!.text = Editable.Factory().newEditable(user.createdOn)
        binding.tiPassword.editText!!.text = Editable.Factory().newEditable(user.password)
        binding.tiName.editText!!.text = Editable.Factory().newEditable(user.name)
        binding.tiEmail.editText!!.text = Editable.Factory().newEditable(user.email)
    }

    private fun getUserFromTextInputs(): User = User(
        binding.tiEmail.editText!!.text.toString(),
        binding.tiName.editText!!.text.toString(),
        binding.tiPassword.editText!!.text.toString(),
        binding.tiDataAtrs.editText!!.text.toString()
    )

    inner class EditOrSaveManager {
        private var editable = false

        fun action(view: View) {
            if (editable) {
                viewModel.updateUser(getUserFromTextInputs())
            }

            enableOrDisableEdit(view)
        }

        private fun enableOrDisableEdit(viewButton: View) {
            for(textInput in editableTexts) {
                val edit = !textInput.isEnabled
                textInput.isEnabled = edit
            }

            editable = !editable

            val btn = viewButton as Button
            if (editable) {
                btn.text = getString(R.string.btnEditarSavar)
            } else {
                btn.text = getString(R.string.btnEditar)
            }
        }
    }
}
