package su.tease.project.feature.cashback.module

import org.koin.dsl.module
import su.tease.core.mvi.component.component.provider.feature
import su.tease.core.mvi.component.component.provider.page
import su.tease.project.feature.cashback.data.repository.BankAccountRepositoryImpl
import su.tease.project.feature.cashback.domain.interceptor.BankAccountInterceptor
import su.tease.project.feature.cashback.domain.interceptor.impl.BankAccountInterceptorImpl
import su.tease.project.feature.cashback.domain.repository.BankAccountRepository
import su.tease.project.feature.cashback.presentation.CashBackFeature
import su.tease.project.feature.cashback.presentation.OtherFeature
import su.tease.project.feature.cashback.presentation.ShopFeature
import su.tease.project.feature.cashback.presentation.list.ListCashBackPage
import su.tease.project.feature.cashback.presentation.list.action.LoadBankAccountListAction
import su.tease.project.feature.cashback.presentation.list.action.impl.LoadBankAccountListActionImpl
import su.tease.project.feature.cashback.presentation.save.SaveCashBackFeature
import su.tease.project.feature.cashback.presentation.save.bank.save.SaveBankAccountPage
import su.tease.project.feature.cashback.presentation.save.bank.save.action.SaveBankAccountAction
import su.tease.project.feature.cashback.presentation.save.bank.save.action.impl.SaveBankAccountActionImpl
import su.tease.project.feature.cashback.presentation.save.bank.select.SelectBankAccountPage
import su.tease.project.feature.cashback.presentation.save.cashback.SaveCashBackPage
import su.tease.project.feature.cashback.presentation.save.cashback.action.SaveCashBackAction
import su.tease.project.feature.cashback.presentation.save.cashback.action.impl.SaveCashBackActionImpl

val cashBackModule = module {
    factory<BankAccountRepository> { BankAccountRepositoryImpl(get(), get(), get()) }

    factory<BankAccountInterceptor> { BankAccountInterceptorImpl(get()) }

    factory<LoadBankAccountListAction> { LoadBankAccountListActionImpl(get(), get()) }
    factory<SaveCashBackAction> { SaveCashBackActionImpl(get(), get(), get(), get()) }
    factory<SaveBankAccountAction> { SaveBankAccountActionImpl(get(), get(), get()) }

    feature { CashBackFeature(get()) }
    feature { ShopFeature(get()) }
    feature { OtherFeature(get()) }
    feature { SaveCashBackFeature(get()) }

    page { ListCashBackPage(get(), get(), get(), get()) }
    page { SaveCashBackPage(get(), get(), get(), get()) }
    page { SelectBankAccountPage(get(), get(), get()) }
    page { SaveBankAccountPage(get(), get(), get()) }
}
