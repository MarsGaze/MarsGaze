package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentLoginBinding
import com.digitalhouse.marsgaze.helper.OkAndErrorSnack
import com.digitalhouse.marsgaze.helper.SnackCreator
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.ui.NavigationActivity
import com.digitalhouse.marsgaze.viewmodels.session.SessionViewModelFactory
import com.digitalhouse.marsgaze.viewmodels.session.login.LoginViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*

private const val ID_TOKEN =
    "936580898759-0ellmnvapnbc8nviicbng44pmjo7qrmm.apps.googleusercontent.com"
private const val RC_SIGN_IN = 9001

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val session: Session by lazy {
        Session.getInstance(
            MarsGazeDB.getDatabase(
                requireContext()
            ),
            AfterFavoriteAction(
                requireContext()
            )
        )
    }

    private val viewModel: LoginViewModel by viewModels {
        SessionViewModelFactory(
            session
        )
    }

    private val snackCreator: SnackCreator by lazy {
        OkAndErrorSnack(
            requireView(),
            requireContext()
        )
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager // Facebook

    // Firebase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        // Facebook CallbackManager
        callbackManager = CallbackManager.Factory.create()

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(ID_TOKEN)
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
    }

    // Firebase
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            viewModel.loginUser(
                User(
                    currentUser.email.toString(),
                    currentUser.displayName.toString(),
                    null
                ),
                false
            )
            return
        }

        viewModel.userInSession()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCadastro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email: String = binding.tfEmail.editText?.text.toString()
            val password: String = binding.tfSenha.editText?.text.toString()
            val user = User(email = email, password = password, name = "")

            viewModel.loginUser(user)
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { result ->
            if (!result.first) {
                snackCreator.showSnack(result)
            } else {
                // Já logamos o usuário aqui :P
                val intent = Intent(requireActivity(), NavigationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        viewModel.registerStatus.observe(viewLifecycleOwner) { result ->
            if (!result.first) {
                snackCreator.showSnack(result)
            } else {
                viewModel.loginUser(
                    User(
                        mAuth.currentUser!!.email.toString(),
                        mAuth.currentUser!!.displayName.toString(),
                        null
                    ),
                    false
                )
            }
        }

        // Firebase google sign in
        binding.googleSignInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Facebook's proprietary button's visibility is set to GONE so we must click it with our
        // custom button
        binding.fbCustomButton.setOnClickListener {
            binding.fbSignInButton.performClick()
        }

        // Facebook sign in callback
        val fbLoginButton = binding.fbSignInButton
        fbLoginButton.fragment = this // 30 minutos foram perdidos por causa dessa linha
        fbLoginButton.setPermissions("email", "public_profile")
        fbLoginButton.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("Facebook", "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d("Facebook", "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d("Facebook", "facebook:onError", error)
                }
            }
        )

    } // Ends onViewCreated

    // Firebase only
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("Google", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Google", "Google sign in failed", e)
            }
        }
    }

    // Firebase only
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Google", "signInWithCredential:success")
                    handleAuth(mAuth)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Google", "signInWithCredential:failure", task.exception)
                    snackCreator.showSnack(Pair(false, R.string.userLoginInvalid))
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("Facebook", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Facebook", "signInWithCredential:success")
                    handleAuth(mAuth)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Facebook", "signInWithCredential:failure", task.exception)
                    snackCreator.showSnack(Pair(false, R.string.userLoginInvalid))
                }
            }
    }

    private fun handleAuth(auth: FirebaseAuth) {
        val userFirebase = mAuth.currentUser

        // val isNew = task.result!!.additionalUserInfo!!.isNewUser
        // Log.d("MyTAG", "onComplete: " + if (isNew) "new user" else "old user")

        val user = User(
            userFirebase?.email.toString(),
            userFirebase?.displayName.toString(),
            null
        )

        // Devemos coincidir com o cadastro no console do Firebase
        val timeEpoch = userFirebase?.metadata?.creationTimestamp
        val date = Date(timeEpoch!!)

        viewModel.registerUser(user, date)
        viewModel.loginUser(user, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}