package com.sfeatherstone.roundup.repository.network


import com.sfeatherstone.roundup.repository.network.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SavingsGoalsApi {
    @GET("/api/v2/account/{accountUid}/savings-goals")
    suspend fun getSavingsGoals(
        @Path("accountUid") accountUid: String
    ): SavingsGoalsListNetwork

    @PUT("/api/v2/account/{accountUid}/savings-goals")
    suspend fun putSavingsGoal(
        @Path("accountUid") accountUid: String,
        @Body body: NewGoalBodyRequest
    ): NewGoalBodyResponse

    @PUT("/api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}")
    suspend fun putAddToSavingsGoal(
        @Path("accountUid") accountUid: String,
        @Path("savingsGoalUid") savingsGoalUid: String,
        @Path("transferUid") transferUid: String,
        @Body body: AddToSavingsGoalRequest
    ): AddToSavingsResponse
}
