package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void checkTest() {
        var ann = new Student<Integer>("Anna");
        assertEquals("Anna", ann.getName());
    }


}