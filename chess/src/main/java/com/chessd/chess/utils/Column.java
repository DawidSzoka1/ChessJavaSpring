package com.chessd.chess.utils;

import java.util.Optional;

public enum Column {
    a(0), b(1), c(2), d(3), e(4), f(5), g(6), h(7);

    private final int index;

    Column(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static Optional<Column> fromName(String s) {
        for (Column column : Column.values()) {
            if (column.name().equals(s)) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }

    public static Optional<Column> fromIndex(int index) {
        for (Column column : Column.values()) {
            if (column.getIndex() == index) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }
}
