package com.monka.splashzone.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum UggVariant {
    BANANA(0),
    CHARCOAL(1),
    LICHEN(2),
    RUDDY(3),
    UMBER(3);


    private static final UggVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(UggVariant::getId)).toArray(UggVariant[]::new);
    private final int id;

    UggVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static UggVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
