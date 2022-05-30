package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener { view: View? ->
//            var goToLogin = Intent(this, MainActivity::class.java)
//            startActivity(goToLogin)
//            finish()
            createUser()
        }

        binding.btnToLogin.setOnClickListener { view: View? ->
            var goToLogin = Intent(this, MainActivity::class.java)
            startActivity(goToLogin)
            finish()
        }
    }

    private fun createUser(){
        var username: String = binding.etUsername.text.toString()
        var email: String = binding.etEmail.text.toString()
        var password: String = binding.etPassword.text.toString()
        var confirmPassword: String = binding.etPassword2.text.toString()

        // initial input error handling
        if(TextUtils.isEmpty(username)){
            binding.etUsername.setError("Username cannot be empty!")
            binding.etUsername.requestFocus()
        }
        else if(TextUtils.isEmpty(email)){
            binding.etEmail.setError("Email cannot be empty!")
            binding.etEmail.requestFocus()
        }
        else if(TextUtils.isEmpty(password)){
            binding.etPassword.setError("Password cannot be empty!")
            binding.etPassword.requestFocus()
        }
        else if(TextUtils.isEmpty(confirmPassword)){
            binding.etPassword2.setError("Confirm Password cannot be empty!")
            binding.etPassword2.requestFocus()
        }
        else if(password != confirmPassword){
            binding.etPassword2.setError("Passwords do not match!")
            binding.etPassword2.requestFocus()
        }
        else{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = auth.currentUser
//                        updateUI(user)

                        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                        var goToLogin = Intent(this, MainActivity::class.java)
                        startActivity(goToLogin)
                        finish()


                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                        try {
                            throw task.exception!!
                        } // if user enters wrong email.
//                        catch (weakPassword: FirebaseAuthWeakPasswordException) {
//                            Log.d(TAG, "onComplete: weak_password")
//
//                        } // if user enters wrong password.
//                        catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
//                            Log.d(TAG, "onComplete: malformed_email")
//
//                        }
                        catch (existEmail: FirebaseAuthUserCollisionException) {
//                            Log.d(TAG, "onComplete: exist_email")
                           Toast.makeText(this, "Email already exists!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
//                            Log.d(TAG, "onComplete: " + e.message)
                            Toast.makeText(this, "Registration Error: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

    }
}