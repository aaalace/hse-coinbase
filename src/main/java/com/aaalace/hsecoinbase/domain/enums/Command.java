package com.aaalace.hsecoinbase.domain.enums;

public enum Command {
    BANK_ACCOUNT_MENU("1"),
    CATEGORY_MENU("2"),
    OPERATION_MENU("3"),
    EXIT("4"),

    GET_COMMAND("5"),
    ADD_COMMAND("6"),
    UPD_COMMAND("7"),
    DEL_COMMAND("8"),

    COUNT("9"),
    PNL("10");

    public final String id;

    Command(String id) {
        this.id = id;
    }
}
