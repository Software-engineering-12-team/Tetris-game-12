package main.java.setting.controlkeysetting;

public enum ControlKeyType {
    TYPE_A(0),
    TYPE_B(1),
    TYPE_C(2),
    TYPE_D(3);

    private final int value;

    ControlKeyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

