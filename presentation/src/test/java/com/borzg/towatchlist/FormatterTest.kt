package com.borzg.towatchlist

import com.borzg.towatchlist.utils.minutesToTimeFormat
import com.borzg.towatchlist.utils.numberByDigits
import org.junit.Assert.*
import org.junit.Test

class FormatterTest {

    @Test
    fun numberFormatter_numberByDigitsIsCorrect() {
        assertEquals("1 000 000 000", numberByDigits(1000000000))
        assertEquals("220", numberByDigits(220))
        assertEquals("-999 999", numberByDigits(-999999))
        assertEquals("-1 999 999", numberByDigits(-1999999))
        assertEquals("0", numberByDigits(-0))
    }

    @Test
    fun dateFormatter_minutesToTimeFormatIsCorrect() {
        assertEquals("10:31", 631.minutesToTimeFormat())
        assertEquals("10:00", 600.minutesToTimeFormat())
        assertEquals("4:00", 4.minutesToTimeFormat())
        assertEquals("2:01:30", 2970.minutesToTimeFormat())
        assertEquals("0:00", 0.minutesToTimeFormat())
    }

}