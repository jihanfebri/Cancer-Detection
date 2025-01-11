package com.dicoding.asclepius.view.result

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.RecordEntity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ResultAdapter(
    private val deleteRecord: (RecordEntity) -> Unit,
    private val onItemClick: (RecordEntity) -> Unit
) : ListAdapter<RecordEntity, ResultAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecordEntity>() {
            override fun areItemsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ViewHolder(view, deleteRecord, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = getItem(position)
        holder.bind(record)
    }

    class ViewHolder(
        itemView: View,
        private val deleteRecord: (RecordEntity) -> Unit,
        private val onItemClick: (RecordEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val tvLabel: TextView = itemView.findViewById(R.id.tvLabel)
        private val tvScore: TextView = itemView.findViewById(R.id.tvScore)
        private val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        private val btnDelete: Button = itemView.findViewById(R.id.button_delete)

        fun bind(record: RecordEntity) {
            imgItem.setImageURI(Uri.parse(record.imageUri))
            tvLabel.text = record.label
            tvScore.text = NumberFormat.getPercentInstance().format(record.confidence)
            tvTimestamp.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(record.timestamp))

            btnDelete.setOnClickListener {
                deleteRecord(record)
            }

            itemView.setOnClickListener {
                onItemClick(record)
            }
        }
    }
}
