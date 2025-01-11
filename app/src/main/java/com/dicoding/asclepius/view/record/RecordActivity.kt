package com.dicoding.asclepius.view.record

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.RecordEntity
import com.dicoding.asclepius.databinding.ActivityRecordBinding
import com.dicoding.asclepius.util.ViewModelFactory
import com.dicoding.asclepius.view.result.ResultActivity
import com.dicoding.asclepius.view.result.ResultAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class RecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordBinding
    private val viewModel: RecordViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val adapter = ResultAdapter(
        deleteRecord = { record ->
            viewModel.deleteRecord(record)
        },
        onItemClick = { record ->
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra(ResultActivity.EXTRA_RESULT, record)
            }
            startActivity(intent)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        collectFlow()

        supportActionBar?.apply {
            title = "History"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.resultsFlow.collect { results ->
                updateResults(results)
            }
        }
    }

    private fun updateResults(results: List<RecordEntity>) {
        adapter.submitList(results)
    }

    private fun initRecyclerView() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@RecordActivity)
            adapter = this@RecordActivity.adapter
            addItemDecoration(DividerItemDecoration(this@RecordActivity, (layoutManager as LinearLayoutManager).orientation))
        }
    }
}
