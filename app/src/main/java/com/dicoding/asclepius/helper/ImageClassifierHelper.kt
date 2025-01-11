package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import com.dicoding.asclepius.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?,
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 2,
    private val modelName: String = "cancer_classification.tflite"
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
            .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
            .build()

        imageClassifier = try {
            ImageClassifier.createFromFileAndOptions(context, modelName, options)
        } catch (e: Exception) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, "Failed to create image classifier: ${e.localizedMessage}")
            null
        }
    }

    fun classifyStaticImage(imageUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = uriToBitmap(imageUri) ?: return@launch
            val tensorImage = TensorImage.fromBitmap(bitmap)
            val processor = ImageProcessor.Builder()
                .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(CastOp(DataType.FLOAT32))
                .build()
            val processedImage = processor.process(tensorImage)

            val startTime = SystemClock.uptimeMillis()
            val results = imageClassifier?.classify(processedImage)
            val inferenceTime = SystemClock.uptimeMillis() - startTime

            withContext(Dispatchers.Main) {
                classifierListener?.onResults(results, inferenceTime)
            }
        }
    }

    private suspend fun uriToBitmap(uri: Uri): Bitmap? = withContext(Dispatchers.IO) {
        return@withContext try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error converting URI to Bitmap: ${e.localizedMessage}")
            null
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>?, inferenceTime: Long)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
