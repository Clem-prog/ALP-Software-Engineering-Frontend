package com.example.alp_software_engineering_frontend.viewModels

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
import com.example.alp_software_engineering_frontend.models.GeneralResponseModel
import com.example.alp_software_engineering_frontend.models.GetAllPaymentsResponse
import com.example.alp_software_engineering_frontend.models.GetPaymentResponse
import com.example.alp_software_engineering_frontend.repositories.PaymentRepository
import com.example.alp_software_engineering_frontend.uiStates.PaymentDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PaymentViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {
    var dataStatus: PaymentDataStatusUIState by mutableStateOf(PaymentDataStatusUIState.Start)
        private set

    fun getAllPayments(
        token: String,
        roomId: Int
    ) {
        viewModelScope.launch {
            dataStatus = PaymentDataStatusUIState.Loading

            try {
                val call = paymentRepository.getAllPayments(token, roomId)

                call.enqueue(object : Callback<GetAllPaymentsResponse> {
                    override fun onResponse(
                        call: Call<GetAllPaymentsResponse>,
                        res: Response<GetAllPaymentsResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = PaymentDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = PaymentDataStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllPaymentsResponse>, t: Throwable) {
                        dataStatus = PaymentDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = PaymentDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getLatestPayment(
        token: String,
        roomId: Int
    ) {
        viewModelScope.launch {
            dataStatus = PaymentDataStatusUIState.Loading

            try {
                val call = paymentRepository.getLatestPayment(token, roomId)

                call.enqueue(object : Callback<GetPaymentResponse> {
                    override fun onResponse(call: Call<GetPaymentResponse>, res: Response<GetPaymentResponse>) {
                        if (res.isSuccessful) {
                            dataStatus = PaymentDataStatusUIState.Success(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = PaymentDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetPaymentResponse>, t: Throwable) {
                        dataStatus = PaymentDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = PaymentDataStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun deletePayment(token: String, paymentId: Int) {
        viewModelScope.launch {
            dataStatus = PaymentDataStatusUIState.Loading

            try {
                val call = paymentRepository.deletePayment(token, paymentId)

                call.enqueue(object: Callback<GeneralResponseModel> {
                    override fun onResponse(
                        call: Call<GeneralResponseModel>,
                        res: Response<GeneralResponseModel>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = PaymentDataStatusUIState.Deleted(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = PaymentDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GeneralResponseModel>, t: Throwable) {
                        dataStatus = PaymentDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = PaymentDataStatusUIState.Failed(error.localizedMessage)
            }
        }
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