package hu.gyuriczaadam.sprintformteszt.util

sealed class TransactionOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): TransactionOrder(orderType)
    class Date(orderType: OrderType): TransactionOrder(orderType)

    fun copy(orderType: OrderType): TransactionOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}
