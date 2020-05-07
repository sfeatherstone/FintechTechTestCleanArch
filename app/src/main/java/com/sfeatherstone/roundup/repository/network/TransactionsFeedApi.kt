package com.sfeatherstone.roundup.repository.network

import com.sfeatherstone.roundup.repository.network.model.AccountFeed
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsFeedApi {
    @GET("/api/v2/feed/account/{accountUid}/category/{categoryUid}")
    suspend fun getAccountFeed(
        @Path("accountUid") accountUid: String,
        @Path("categoryUid") categoryUid: String,
        @Query("changesSince") changesSince: String
    ): AccountFeed
}
