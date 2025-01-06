package com.chessd.chess.utils;

import java.util.Optional;

/**
 * Represents the columns of a chessboard, from 'a' to 'h'.
 * Each column is associated with a zero-based index.
 */
public enum Column {
    a(0), b(1), c(2), d(3), e(4), f(5), g(6), h(7);

    private final int index;

    Column(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Finds a {@code Column} based on its name.
     *
     * @param s the name of the column (e.g., "a", "b", etc.).
     * @return an {@link Optional} containing the corresponding {@code Column},
     * or an empty {@link Optional} if no match is found.
     */
    public static Optional<Column> fromName(String s) {
        for (Column column : Column.values()) {
            if (column.name().equals(s)) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds a {@code Column} based on its index.
     *
     * @param index the zero-based index of the column.
     * @return an {@link Optional} containing the corresponding {@code Column},
     * or an empty {@link Optional} if no match is found.
     */
    public static Optional<Column> fromIndex(int index) {
        for (Column column : Column.values()) {
            if (column.getIndex() == index) {
                return Optional.of(column);
            }
        }
        return Optional.empty();
    }
}
