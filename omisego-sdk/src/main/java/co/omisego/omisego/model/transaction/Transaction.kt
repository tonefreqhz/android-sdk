package co.omisego.omisego.model.transaction

/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 14/3/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */

import co.omisego.omisego.model.MintedToken
import co.omisego.omisego.model.pagination.Paginable
import java.util.*

data class TransactionExchange(val rate: Double)

data class TransactionSource(
        val address: String,
        val amount: Double,
        val mintedToken: MintedToken
)

data class Transaction(
        val id: String,
        val status: TransactionStatus,
        val from: TransactionSource,
        val to: TransactionSource,
        val exchange: TransactionExchange,
        val metadata: Map<String, Any>,
        val createdAt: Date,
        val updatedAt: Date
) : Paginable.Transaction()
