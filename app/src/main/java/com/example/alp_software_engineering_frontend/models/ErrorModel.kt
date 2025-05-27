package com.example.alp_software_engineering_frontend.models

import com.google.gson.annotations.SerializedName

data class ErrorModel (
    @SerializedName("errorMessage")
    val errors: String
)