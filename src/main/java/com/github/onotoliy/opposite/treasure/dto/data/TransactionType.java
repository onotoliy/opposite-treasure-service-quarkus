package com.github.onotoliy.opposite.treasure.dto.data;

public enum TransactionType {

    NONE("Не выбрано"),
    COST("Расход"),
    CONTRIBUTION("Взнос"),
    WRITE_OFF("Списание с депозита"),
    PAID("Платеж"),
    EARNED("Заработано");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
