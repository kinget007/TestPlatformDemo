package com.king.yyl.domain;

public enum Scheme {
    HTTP("http"),
    HTTPS("https"),
    WS("ws"),
    WSS("wss");

    private final String value;

    private Scheme(String value) {
        this.value = value;
    }

    public static Scheme forValue(String value) {
        for (Scheme item : Scheme.values()) {
            if (item.toValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    public String toValue() {
        return value;
    }
}
