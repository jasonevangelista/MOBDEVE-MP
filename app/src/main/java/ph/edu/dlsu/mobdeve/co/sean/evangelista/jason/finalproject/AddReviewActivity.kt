package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddReviewBinding


class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { view: View? ->
            var goToPlayerProfile = Intent(this, MainActivity::class.java)
            startActivity(goToPlayerProfile)
            finish()
        }

        binding.btnCancelReview.setOnClickListener { view: View? ->
            var goToPlayerProfile = Intent(this, MainActivity::class.java)
            startActivity(goToPlayerProfile)
            Snackbar.make(binding.root,
                "Cancelled Review",
                Snackbar.LENGTH_LONG).show()
            finish()
        }

        binding.btnAddReview.setOnClickListener { view: View? ->
            var goToPlayerProfile = Intent(this, MainActivity::class.java)
            startActivity(goToPlayerProfile)
            Snackbar.make(binding.root,
                "Added Review Successfully",
                Snackbar.LENGTH_LONG).show()
            finish()
        }

    }
}