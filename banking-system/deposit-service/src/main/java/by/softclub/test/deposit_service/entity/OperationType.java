package by.softclub.test.deposit_service.entity;

public enum OperationType {
    DEPOSIT,       // Пополнение
    WITHDRAWAL,          // Частичное снятие
    INTEREST_PAYMENT,    // Выплата процентов
    CLOSURE              // Закрытие
}