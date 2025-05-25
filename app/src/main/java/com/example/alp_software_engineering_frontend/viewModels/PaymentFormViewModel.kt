package com.example.alp_software_engineering_frontend.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.alp_software_engineering_frontend.D4C
import com.example.alp_software_engineering_frontend.models.ErrorModel
import com.example.alp_software_engineering_frontend.models.GetPaymentResponse
import com.example.alp_software_engineering_frontend.repositories.PaymentRepository
import com.example.alp_software_engineering_frontend.uiStates.AuthenticationStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.PaymentDataStatusUIState
import com.example.alp_software_engineering_frontend.uiStates.PaymentUIState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Date

class PaymentFormViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    private val _paymentUIState = MutableStateFlow(PaymentUIState())
    val paymentUIState: StateFlow<PaymentUIState>
        get() {
            return _paymentUIState.asStateFlow()
        }

    var dataStatus: PaymentDataStatusUIState by mutableStateOf(PaymentDataStatusUIState.Start)
        private set

    var transferInput by mutableStateOf("")
        private set

    fun changeTransferInput(payment: String) {
        transferInput = payment
    }



    fun createPayment(navController: NavHostController, token: String, roomId: Int, date: Date) {
        viewModelScope.launch {
            dataStatus = PaymentDataStatusUIState.Loading

            Log.d("token-event-list-form", "TOKEN: ${token}")

            try {
                val call = paymentRepository.createPayment(
                    token,
                    transferInput,
                    date,
                    roomId
                )

                call.enqueue(object : Callback<GetPaymentResponse> {
                    override fun onResponse(
                        call: Call<GetPaymentResponse>,
                        res: Response<GetPaymentResponse>
                    ) {
                        if (res.isSuccessful) {
                            Log.d("json", "JSON RESPONSE: ${res.body()!!.data}")
                            dataStatus = PaymentDataStatusUIState.Success(res.body()!!.data)

                            resetViewModel()

                            /*navController.navigate(PagesEnum.Home.name) {
                                popUpTo(PagesEnum.CreateEvent.name) {
                                    inclusive = true
                                }
                            }*/ //TODO: get this done after pagesenum done
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            Log.d("event-create", "Error Response: $errorMessage")
                            dataStatus = PaymentDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetPaymentResponse>, t: Throwable) {
                        dataStatus = PaymentDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = PaymentDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun uploadImage(uri: Uri, context: Context): Boolean {
        var uploadSuccess = false
        runBlocking { // Block the current thread until the upload completes
            try {
                val url = uploadPosterToCloudinary(uri, context)
                if (url.isNotEmpty()) {
                    transferInput = url // Store the URL after uploading
                    Log.d("EventFormViewModel", "Poster uploaded successfully: $transferInput")
                    uploadSuccess = true
                } else {
                    Log.d("EventFormViewModel", "Poster upload failed, URL is empty.")
                }
            } catch (e: Exception) {
                Log.e("EventFormViewModel", "Upload failed: ${e.localizedMessage}")
            }
        }
        return uploadSuccess
    }


    private suspend fun uploadPosterToCloudinary(posterUri: Uri, context: Context): String {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(posterUri)
                    ?: throw IOException("Failed to open InputStream for URI: $posterUri")

                val mediaType = "image/*".toMediaTypeOrNull()

                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "file",
                        "image.jpg",
                        inputStream.readBytes().toRequestBody(mediaType)
                    )
                    .addFormDataPart("upload_preset", "event_posters")
                    .build()

                val request = Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/dggn8axkq/image/upload")
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (!responseBody.isNullOrEmpty()) {
                        val jsonResponse = JSONObject(responseBody)
                        val secureUrl = jsonResponse.getString("secure_url")
                        Log.d("Cloudinary Upload", "Image uploaded to: $secureUrl")
                        return@withContext secureUrl
                    }
                } else {
                    Log.e("Cloudinary Upload", "Upload failed: ${response.message}")
                    throw IOException("Failed to upload image to Cloudinary: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("Cloudinary Upload", "Exception: ${e.localizedMessage}")
                throw IOException("Error uploading image: ${e.localizedMessage}")
            }
            return@withContext ""
        }
    }

    fun checkForm() {
        if (
            transferInput.isNotEmpty()
        ) {
            _paymentUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = true
                )
            }
        } else {
            _paymentUIState.update { currentState ->
                currentState.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    fun resetViewModel() {
        changeTransferInput("")
        _paymentUIState.update { currentState ->
            currentState.copy(
                buttonEnabled = false
            )
        }

        dataStatus = PaymentDataStatusUIState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as D4C)
                val paymentRepository = application.container.paymentRepository
                PaymentViewModel(paymentRepository)
            }
        }
    }
}