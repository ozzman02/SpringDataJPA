package com.ossant.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class OrderHeaderTest {

    @Test
    void equalsMethodTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setId(1L);
        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(1L);
        assert orderHeader.equals(orderHeader2);
    }

    @Test
    void notEqualsTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setId(1L);
        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(3L);
        assertFalse(orderHeader.equals(orderHeader2));
    }

}