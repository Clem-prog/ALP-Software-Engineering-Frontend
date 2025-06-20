package com.example.alp_software_engineering_frontend.models

data class GetAllUsersResponse (
    val data : List<UserModel>
)

data class GetUserResponse (
    val data : UserModel
)

data class UserModel (
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phone_number: String,
    val age: String,
    val gender: String,
    val isAdmin: Boolean,
    val token: String?
)

