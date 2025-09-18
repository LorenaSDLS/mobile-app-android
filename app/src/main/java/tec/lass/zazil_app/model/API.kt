package mx.sphr.zazil.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthAPI {
    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}
