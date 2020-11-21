package com.lidapinchuk.task01.constants;

public final class ExceptionConstants {

    private ExceptionConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Index %d is out of list bounds";
    public static final String INVALID_INDEXES_MESSAGE = "fromIndex %d is bigger than toIndex %d";
    public static final String NO_SUCH_ELEMENT_MESSAGE = "No list element for iterator on position %d";
    public static final String ITERATOR_ILLEGAL_STATE_MESSAGE = "'next' or 'previous' operation should be called before that";

}
