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
import com.example.alp_software_engineering_frontend.enums.PaymentEnum
import com.example.alp_software_engineering_frontend.models.ErrorModel
import com.example.alp_software_engineering_frontend.models.GetAllRoomsResponse
import com.example.alp_software_engineering_frontend.models.GetRoomResponse
import com.example.alp_software_engineering_frontend.models.RoomUpdateRequest
import com.example.alp_software_engineering_frontend.repositories.RoomRepository
import com.example.alp_software_engineering_frontend.uiStates.RoomDataStatusUIState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RoomViewModel(
    private var roomRepository: RoomRepository
) : ViewModel() {
    var dataStatus: RoomDataStatusUIState by mutableStateOf(RoomDataStatusUIState.Start)
        private set

    var statusInput by mutableStateOf(PaymentEnum.unpaid)
        private set

    fun changeStatus(status: PaymentEnum) {
        statusInput = status
    }

    fun getAllRooms(
        token: String
    ) {
        viewModelScope.launch {
            dataStatus = RoomDataStatusUIState.Loading

            try {
                val call = roomRepository.getAllRooms(token)

                call.enqueue(object : Callback<GetAllRoomsResponse> {
                    override fun onResponse(
                        call: Call<GetAllRoomsResponse>,
                        res: Response<GetAllRoomsResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = RoomDataStatusUIState.GetAllSuccess(res.body()!!.data)
                        } else {
                            val errorBody = res.errorBody()?.charStream()
                            val errorMessage = if (errorBody != null) {
                                Gson().fromJson(errorBody, ErrorModel::class.java)
                            } else {
                                null
                            }

                            val errorText = errorMessage?.errors ?: "Unknown error occurred"
                            dataStatus = RoomDataStatusUIState.Failed(errorText)
                        }
                    }

                    override fun onFailure(call: Call<GetAllRoomsResponse>, t: Throwable) {
                        dataStatus = RoomDataStatusUIState.Failed(t.localizedMessage)
                    }
                })
            } catch (error: IOException) {
                dataStatus = RoomDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    fun getRoomById(
        token: String,
        roomId: Int
    ) {
        viewModelScope.launch {
            dataStatus = RoomDataStatusUIState.Loading

            try {
                val call = roomRepository.getRoomById(token, roomId)

                call.enqueue(object : Callback<GetRoomResponse> {
                    override fun onResponse(
                        call: Call<GetRoomResponse>, 
                        res: Response<GetRoomResponse>
                    ) {
                        if (res.isSuccessful) {
                            dataStatus = RoomDataStatusUIState.Success(res.body()!!.data)
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )
                            Log.d("error-data", "ERROR DATA: ${errorMessage.errors}")
                            dataStatus = RoomDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetRoomResponse>, t: Throwable) {
                        dataStatus = RoomDataStatusUIState.Failed(t.localizedMessage)
                        Log.d("get-user-error", "ERROR DATA: ${t.localizedMessage}")
                    }
                })
            } catch (error: IOException) {
                dataStatus = RoomDataStatusUIState.Failed(error.localizedMessage)
                Log.d("get-user-error", "GET USER ERROR: ${error.localizedMessage}")
            }
        }
    }

    fun updateRoomStatus(token: String, eventId: Int, statusInput: PaymentEnum) {
        viewModelScope.launch {
            dataStatus = RoomDataStatusUIState.Loading

            try {
                val call = roomRepository.updateRoomStatus(token, eventId, statusInput.toString())

                call.enqueue(object: Callback<GetRoomResponse>  {
                    override fun onResponse(
                        call: Call<GetRoomResponse>,
                        res: Response<GetRoomResponse>
                    )  {
                        if (res.isSuccessful) {
                            dataStatus = RoomDataStatusUIState.Success(res.body()!!.data)

                            /*navController.navigate(PagesEnum.YourEvents.name) {
                                popUpTo(PagesEnum.CreateEvent.name) {
                                    inclusive = true
                                }
                            }*/ //TODO: GET THIS THING MANAGED AFTER ALL PAGESENUM ARE DONE FROM VIEW, GET IT TO COME BACK TO ADMIN'S HOME PAGE AFTER APPROVING/DISAPPROVING
                        } else {
                            val errorMessage = Gson().fromJson(
                                res.errorBody()!!.charStream(),
                                ErrorModel::class.java
                            )

                            dataStatus = RoomDataStatusUIState.Failed(errorMessage.errors)
                        }
                    }

                    override fun onFailure(call: Call<GetRoomResponse>, t: Throwable) {
                        dataStatus = RoomDataStatusUIState.Failed(t.localizedMessage)
                    }

                })
            } catch (error: IOException) {
                dataStatus = RoomDataStatusUIState.Failed(error.localizedMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as D4C)
                val roomRepository = application.container.roomRepository
                RoomViewModel(roomRepository)
            }
        }
    }
}