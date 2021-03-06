package co.omisego.omisego.model.transaction.consumption

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 25/4/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import android.accounts.Account
import android.os.Parcelable
import co.omisego.omisego.OMGAPIClient
import co.omisego.omisego.constant.enums.OMGEnum
import co.omisego.omisego.custom.retrofit2.adapter.OMGCall
import co.omisego.omisego.model.Token
import co.omisego.omisego.model.User
import co.omisego.omisego.model.socket.SocketReceive
import co.omisego.omisego.model.socket.SocketTopic
import co.omisego.omisego.model.transaction.Transaction
import co.omisego.omisego.model.transaction.request.TransactionRequest
import co.omisego.omisego.operation.Listenable
import co.omisego.omisego.websocket.SocketCustomEventListener
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.math.BigDecimal
import java.util.Date

/**
 * Represents transaction consumption statuses.
 *
 * - pending: The transaction consumption is pending validation
 * - confirmed: The transaction was consumed
 * - failed: The transaction failed to be consumed
 * - approved: The transaction was approved
 * - rejected: The transaction was rejected
 * - unknown: The transaction status is unknown (e.g. the sdk is out of sync with the eWallet API)
 */
enum class TransactionConsumptionStatus constructor(override val value: String) : OMGEnum {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    APPROVED("approved"),
    REJECTED("rejected"),
    FAILED("failed"),
    UNKNOWN("unknown");

    override fun toString(): String = value
}

/**
 * Represents a transaction consumption
 *
 * @param id The unique identifier of the consumption
 * @param status The status of the consumption (pending, confirmed or failed)
 * @param amount The amount of token to transfer (down to subunit to unit)
 * @param estimatedRequestAmount The estimated amount in the request currency
 * @param estimatedConsumptionAmount The estimated amount in the consumption currency
 * @param finalizedRequestAmount The final amount to be transferred after exchange in the request currency
 * @param finalizedConsumptionAmount The final amount to be transferred after exchange in the consumption currency
 * @param token The token for the request
 * In the case of a type "send", this will be the token that the consumer will receive
 * In the case of a type "receive" this will be the token that the consumer will send
 * @param correlationId An id that can uniquely identify a transaction. Typically an order id from a provider.
 * @param idempotencyToken The idempotency token of the consumption
 * @param transaction The transaction generated by this consumption (this will be null until the consumption is confirmed)
 * @param address The address used for the consumption
 * @param user The user that initiated the consumption
 * @param account The account that initiated the consumption
 * @param transactionRequest The transaction request to be consumed
 * @param socketTopic The topic which can be listened in order to receive events regarding this consumption
 * @param createdAt The creation date of the consumption
 * @param expirationDate The date when the consumption will expire
 * @param approvedAt The date when the consumption got approved
 * @param rejectedAt The date when the consumption got rejected
 * @param confirmedAt The date when the consumption got confirmed
 * @param failedAt The date when the consumption failed
 * @param expiredAt The date when the consumption expired
 * @param metadata Additional metadata for the consumption
 * @param encryptedMetadata Additional encrypted metadata for the consumption
 */

@Parcelize
data class TransactionConsumption(
    val id: String,
    val status: TransactionConsumptionStatus,
    val amount: BigDecimal?,
    val estimatedRequestAmount: BigDecimal,
    val estimatedConsumptionAmount: BigDecimal,
    val finalizedRequestAmount: BigDecimal?,
    val finalizedConsumptionAmount: BigDecimal?,
    val token: Token,
    val correlationId: String?,
    val idempotencyToken: String?,
    val transaction: Transaction?,
    val address: String,
    val user: User?,
    val account: Account?,
    val transactionRequest: TransactionRequest,
    override val socketTopic: SocketTopic<SocketCustomEventListener.TransactionConsumptionListener>,
    val createdAt: Date?,
    val expirationDate: Date?,
    val approvedAt: Date?,
    val rejectedAt: Date?,
    val confirmedAt: Date?,
    val failedAt: Date?,
    val expiredAt: Date?,
    val metadata: @RawValue Map<String, Any>,
    val encryptedMetadata: @RawValue Map<String, Any>
) : Parcelable, Listenable<SocketCustomEventListener.TransactionConsumptionListener>, SocketReceive.SocketData {
    override fun equals(other: Any?): Boolean {
        return other is TransactionConsumption && other.id == id
    }
}

/**
 * An extension function that uses the id from `TransactionConsumption` object to approve the transaction
 *
 * @param omgAPIClient the [OMGAPIClient] object in your application to be used to approve the transaction
 * @return The [OMGCall<TransactionConsumption>] object that you need to call enqueue method on to actually perform the request to the API
 */
fun TransactionConsumption.approve(omgAPIClient: OMGAPIClient): OMGCall<TransactionConsumption> =
    omgAPIClient.approveTransactionConsumption(TransactionConsumptionActionParams(this.id))

/**
 * An extension function that uses the id from `TransactionConsumption` object to reject the transaction
 *
 * @param omgAPIClient the [OMGAPIClient] object in your application to be used to reject the transaction
 * @return The [OMGCall<TransactionConsumption>] object that you need to call enqueue method on to actually perform the request to the API
 */
fun TransactionConsumption.reject(omgAPIClient: OMGAPIClient) =
    omgAPIClient.rejectTransactionConsumption(TransactionConsumptionActionParams(this.id))
