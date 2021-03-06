package co.omisego.omisego.model.transaction.consumption

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 29/4/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

/**
 * Approve or reject transaction request params
 */
data class TransactionConsumptionActionParams(
    /**
     * An id of consumed transaction
     */
    val id: String
)
