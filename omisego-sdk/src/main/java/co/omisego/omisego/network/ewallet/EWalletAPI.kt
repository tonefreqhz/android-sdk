package co.omisego.omisego.network.ewallet

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 5/3/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import co.omisego.omisego.constant.Endpoints.APPROVE_TRANSACTION
import co.omisego.omisego.constant.Endpoints.CONSUME_TRANSACTION_REQUEST
import co.omisego.omisego.constant.Endpoints.CREATE_TRANSACTION_REQUEST
import co.omisego.omisego.constant.Endpoints.GET_CURRENT_USER
import co.omisego.omisego.constant.Endpoints.GET_SETTINGS
import co.omisego.omisego.constant.Endpoints.GET_TRANSACTIONS
import co.omisego.omisego.constant.Endpoints.GET_WALLETS
import co.omisego.omisego.constant.Endpoints.LOGOUT
import co.omisego.omisego.constant.Endpoints.REJECT_TRANSACTION
import co.omisego.omisego.constant.Endpoints.RETRIEVE_TRANSACTION_REQUEST
import co.omisego.omisego.constant.Endpoints.TRANSFER
import co.omisego.omisego.custom.retrofit2.adapter.OMGCall
import co.omisego.omisego.model.Logout
import co.omisego.omisego.model.Setting
import co.omisego.omisego.model.User
import co.omisego.omisego.model.WalletList
import co.omisego.omisego.model.pagination.PaginationList
import co.omisego.omisego.model.transaction.Transaction
import co.omisego.omisego.model.transaction.consumption.TransactionConsumption
import co.omisego.omisego.model.transaction.consumption.TransactionConsumptionActionParams
import co.omisego.omisego.model.transaction.consumption.TransactionConsumptionParams
import co.omisego.omisego.model.transaction.list.TransactionListParams
import co.omisego.omisego.model.transaction.request.TransactionRequest
import co.omisego.omisego.model.transaction.request.TransactionRequestCreateParams
import co.omisego.omisego.model.transaction.request.TransactionRequestParams
import co.omisego.omisego.model.transaction.send.TransactionCreateParams
import retrofit2.http.Body
import retrofit2.http.POST

interface EWalletAPI {
    @POST(GET_CURRENT_USER)
    fun getCurrentUser(): OMGCall<User>

    @POST(LOGOUT)
    fun logout(): OMGCall<Logout>

    @POST(GET_WALLETS)
    fun getWallets(): OMGCall<WalletList>

    @POST(GET_SETTINGS)
    fun getSettings(): OMGCall<Setting>

    @POST(GET_TRANSACTIONS)
    fun getTransactions(@Body request: TransactionListParams): OMGCall<PaginationList<Transaction>>

    @POST(CREATE_TRANSACTION_REQUEST)
    fun createTransactionRequest(@Body request: TransactionRequestCreateParams): OMGCall<TransactionRequest>

    @POST(RETRIEVE_TRANSACTION_REQUEST)
    fun retrieveTransactionRequest(@Body request: TransactionRequestParams): OMGCall<TransactionRequest>

    @POST(CONSUME_TRANSACTION_REQUEST)
    fun consumeTransactionRequest(
        @Body request: TransactionConsumptionParams
    ): OMGCall<TransactionConsumption>

    @POST(APPROVE_TRANSACTION)
    fun approveTransactionConsumption(
        @Body request: TransactionConsumptionActionParams
    ): OMGCall<TransactionConsumption>

    @POST(REJECT_TRANSACTION)
    fun rejectTransactionConsumption(
        @Body request: TransactionConsumptionActionParams
    ): OMGCall<TransactionConsumption>

    @POST(TRANSFER)
    fun transfer(
        @Body request: TransactionCreateParams
    ): OMGCall<Transaction>
}
