package su.tease.project.feature.bank.integration.dependencies.presentation.view

import su.tease.project.feature.bank.integration.R
import su.tease.project.feature.cashback.presentation.dependencies.view.CashBackOwnerText

class CashBackOwnerTextImpl : CashBackOwnerText {
    override val cashBackInfo: Int
        get() = R.string.BankAccount_SaveCashBack_CashBackPresetSelect_Info

    override val cashBackOwnerTitle: Int
        get() = R.string.BankAccount_SaveCashBack_CashBackOwnerSelect_Title

    override val cashBackOwnerPlaceholder: Int
        get() = R.string.BankAccount_SaveCashBack_CashBackOwnerSelect_Placeholder
}
