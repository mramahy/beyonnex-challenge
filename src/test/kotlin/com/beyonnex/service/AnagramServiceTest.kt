package com.beyonnex.service

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@QuarkusTest
class AnagramServiceTest {

    private val anagramService = AnagramService()

    @Test
    fun `validateAnagramAndSave with valid anagrams`() {
        assertTrue(anagramService.validateAnagramAndSave("listen", "silent"))
    }

    @Test
    fun `validateAnagramAndSave with invalid anagrams`() {
        assertFalse(anagramService.validateAnagramAndSave("hello", "world"))
    }

    @ParameterizedTest
    @MethodSource("com.beyonnex.AnagramTestDataProvider#provideEmptyOrNullInputTestData")
    fun `validateAnagramAndSave with null and empty inputs`(firstAnagramArgument: String?, secondAnagramArgument: String?) {
        assertFalse(anagramService.validateAnagramAndSave(firstAnagramArgument, secondAnagramArgument))
    }

    @Test
    fun `getCorrespondentAnagrams with existing anagram`() {
        anagramService.validateAnagramAndSave("listen", "silent")
        assertEquals(setOf("silent"), anagramService.getCorrespondentAnagrams("listen"))
    }

    @Test
    fun `getCorrespondentAnagrams with non-existing anagram`() {
        assertNull(anagramService.getCorrespondentAnagrams("hello"))
    }


    @Test
    fun `getCorrespondentAnagrams with empty string`() {
        assertNull(anagramService.getCorrespondentAnagrams(""))
    }
}