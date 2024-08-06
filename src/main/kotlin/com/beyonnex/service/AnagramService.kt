package com.beyonnex.service

import jakarta.enterprise.context.ApplicationScoped
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@ApplicationScoped
class AnagramService {

    private val anagramMap = ConcurrentHashMap<Long, Set<String>>()

    private val alphabetPrimeMap = generateAlphabetPrimeMap()

    fun validateAnagramAndSave(firstAnagramArgument: String?, secondAnagramArgument: String?): Boolean {
        return if (isAnagram(firstAnagramArgument, secondAnagramArgument)) {
            val id = resolveAnagramHash(firstAnagramArgument?.toCharArray())
            saveAnagram(id, firstAnagramArgument, secondAnagramArgument)
        } else {
            false
        }
    }

    fun getCorrespondentAnagrams(anagram: String): Set<String>? {
        val id = resolveAnagramHash(anagram.toCharArray())
        return anagramMap[id]?.minusElement(anagram)
    }

    private fun saveAnagram(id: Long, firstAnagramArgument: String?, secondAnagramArgument: String?): Boolean {
        val anagramSet = anagramMap.getOrPut(id) { mutableSetOf() }
        anagramMap[id] = anagramSet + listOfNotNull(firstAnagramArgument, secondAnagramArgument)
        return true

    }

    private fun resolveAnagramHash(anagramCharArray: CharArray?): Long {
        var hash = 1L
        anagramCharArray?.forEach { char ->
            hash *= alphabetPrimeMap[char] ?: 1
        }
        return hash
    }

    private fun isAnagram(firstAnagramArgument: String?, secondAnagramArgument: String?): Boolean {
        if (firstAnagramArgument.isNullOrBlank() || secondAnagramArgument.isNullOrBlank()) {
            return false
        }
        val firstAnagram = normalizeString(firstAnagramArgument)
        val secondAnagram = normalizeString(secondAnagramArgument)
        return firstAnagram.length == secondAnagram.length && firstAnagram.toSortedSet() == secondAnagram.toSortedSet()
    }

    private fun normalizeString(input: String): String {
        return input.lowercase(Locale.getDefault()).replace("\\s".toRegex(), "")
    }

    companion object {
        fun generateAlphabetPrimeMap(): Map<Char, Int> {
            val primes = listOf(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
            )
            val alphabet = ('a'..'z').toList()
            return alphabet.zip(primes).toMap()
        }
    }
}