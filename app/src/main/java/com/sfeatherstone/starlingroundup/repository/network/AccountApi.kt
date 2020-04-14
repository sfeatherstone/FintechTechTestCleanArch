package com.sfeatherstone.starlingroundup.repository.network

import com.sfeatherstone.starlingroundup.repository.network.model.AccountBalanceNetwork
import com.sfeatherstone.starlingroundup.repository.network.model.AccountIdentifierNetwork
import com.sfeatherstone.starlingroundup.repository.network.model.AccountList
import retrofit2.http.GET
import retrofit2.http.Path


interface AccountApi {
    @GET("/api/v2/accounts")
    suspend fun getAccounts(
    ): AccountList

    @GET("/api/v2/accounts/{accountUid}/identifiers")
    suspend fun getAccountIdendifiers(@Path("accountUid") accountUid: String
    ): AccountIdentifierNetwork

    @GET("/api/v2/accounts/{accountUid}/balance")
    suspend fun getAccountBalance(@Path("accountUid") accountUid: String
    ): AccountBalanceNetwork

}
