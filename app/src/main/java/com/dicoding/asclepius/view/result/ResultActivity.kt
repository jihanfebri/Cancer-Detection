package com.dicoding.asclepius.view.result

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.RecordEntity
import com.dicoding.asclepius.data.remote.response.HealthArticles
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.util.ViewModelFactory
import com.dicoding.asclepius.view.news.NewsAdapter
import org.tensorflow.lite.task.vision.classifier.Classifications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private val newsAdapter = NewsAdapter(emptyList())
    private var result: RecordEntity? = null
    private val viewModel: ResultViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Result"
            setDisplayHomeAsUpEnabled(true)
        }

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)?.let { Uri.parse(it) }
        val resultEntity = intent.getParcelableExtra<RecordEntity>(EXTRA_RESULT)

        imageUri?.let {
            setupImageClassifier(it)
            binding.resultImage.setImageURI(it)
            imageClassifierHelper.classifyStaticImage(it)
        } ?: resultEntity?.let {
            updateView(Uri.parse(it.imageUri), it.label, it.confidence)
        }

        setupRecyclerView()

        binding.buttonSaveResult.setOnClickListener {
            result?.let {
                viewModel.saveRecord(it)
                Toast.makeText(this, "Result saved to history", Toast.LENGTH_SHORT).show()
            }
        }

        fetchNewsArticles()
    }

    private fun setupImageClassifier(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { classifications ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                                val topCategory = classifications[0].categories.maxByOrNull { it.score }
                                topCategory?.let {
                                    updateView(imageUri, it.label, it.score)
                                    result = RecordEntity(
                                        timestamp = System.currentTimeMillis(),
                                        imageUri = imageUri.toString(),
                                        label = it.label,
                                        confidence = it.score
                                    )
                                }
                            } else {
                                binding.resultText.text = getString(R.string.no_result)
                            }
                        }
                    }
                }
            }
        )
    }

    private fun updateView(imageUri: Uri, label: String, score: Float) {
        binding.resultImage.setImageURI(imageUri)
        binding.resultText.text = formatResultText(label, score)
        updateProgressBar(label, score)
    }

    private fun formatResultText(label: String, score: Float): String {
        return NumberFormat.getPercentInstance().format(score) + " " + label
    }

    private fun updateProgressBar(label: String, score: Float) {
        binding.progressBar.progress = if (label == "Cancer") {
            score.times(100).toInt()
        } else {
            100 - score.times(100).toInt()
        }
    }

    private fun fetchNewsArticles() {
        val apiKey = "388ae0e212684b53a654c3c73202643e"
        val call = ApiConfig.getApiService().getNews(apiKey)

        call.enqueue(object : Callback<HealthArticles> {
            override fun onResponse(call: Call<HealthArticles>, response: Response<HealthArticles>) {
                if (response.isSuccessful) {
                    response.body()?.articles?.let {
                        newsAdapter.updateArticles(it)
                    } ?: run {
                        Toast.makeText(this@ResultActivity, "No news articles found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ResultActivity, "Failed to fetch news articles: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HealthArticles>, t: Throwable) {
                Toast.makeText(this@ResultActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvArticles.layoutManager = LinearLayoutManager(this)
        binding.rvArticles.adapter = newsAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}
