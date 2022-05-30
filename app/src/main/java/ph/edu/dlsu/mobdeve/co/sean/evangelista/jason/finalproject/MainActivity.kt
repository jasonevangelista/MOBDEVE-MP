package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnLogin.setOnClickListener { view: View? ->
            // home page
//            var goToHome = Intent(this, PlayerListActivity::class.java)
//            startActivity(goToHome)

            // user profile
//            var goToUserProfile = Intent(this, UserProfileActivity::class.java)
//            startActivity(goToUserProfile)
//            finish()

            loginUser()
        }

        binding.btnToRegister.setOnClickListener { view: View? ->
            var goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
//            reload();
            var goToHome = Intent(this, PlayerListActivity::class.java)
            startActivity(goToHome)

        }
    }

    private fun loginUser(){
        var email: String = binding.etUsername.text.toString() //change to email
        var password: String = binding.etPassword.text.toString()

        if(TextUtils.isEmpty(email)){
            binding.etUsername.setError("Username cannot be empty!")
            binding.etUsername.requestFocus()
        }
        else if(TextUtils.isEmpty(password)){
            binding.etPassword.setError("Password cannot be empty!")
            binding.etPassword.requestFocus()
        }
        else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
//                        val user = auth.currentUser
//                        updateUI(user)
                        Toast.makeText(this, "User logged in successfully", Toast.LENGTH_SHORT).show()
                        var goToHome = Intent(this, PlayerListActivity::class.java)
                        startActivity(goToHome)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                        updateUI(null)
                        Toast.makeText(this, "Login Error: " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}