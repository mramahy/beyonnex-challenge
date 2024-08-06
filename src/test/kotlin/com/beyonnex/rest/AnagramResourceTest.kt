package com.beyonnex.rest

import com.beyonnex.service.AnagramService
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.inject.Inject
import jakarta.ws.rs.core.MediaType
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@QuarkusTest
class AnagramResourceTest {

    @Inject
    lateinit var anagramService: AnagramService

    @Test
    fun `checkAnagram with valid anagrams`() {
        given()
            .queryParam("firstAnagramArgument", "listen")
            .queryParam("secondAnagramArgument", "silent")
            .`when`()
            .post("/anagram/check")
            .then()
            .statusCode(200)
            .contentType(MediaType.TEXT_PLAIN)
            .body(org.hamcrest.Matchers.equalTo("true"))
    }

    @Test
    fun `checkAnagram with invalid anagrams`() {
        given()
            .queryParam("firstAnagramArgument", "hello")
            .queryParam("secondAnagramArgument", "world")
            .`when`()
            .post("/anagram/check")
            .then()
            .statusCode(200)
            .contentType(MediaType.TEXT_PLAIN)
            .body(org.hamcrest.Matchers.equalTo("false"))
    }

    @ParameterizedTest
    @MethodSource("com.beyonnex.AnagramTestDataProvider#provideEmptyOrNullInputTestData")
    fun `checkAnagram with null or empty inputs`(firstAnagramArgument: String?, secondAnagramArgument: String?) {
        given()
            .queryParam("firstAnagramArgument", firstAnagramArgument)
            .queryParam("secondAnagramArgument", secondAnagramArgument)
            .`when`()
            .post("/anagram/check")
            .then()
            .statusCode(200)
            .contentType(MediaType.TEXT_PLAIN)
            .body(org.hamcrest.Matchers.equalTo("false"))
    }

    @Test
    fun `getAnagrams with existing anagram`() {
        anagramService.validateAnagramAndSave("listen", "silent")
        val response = given()
            .pathParam("anagram", "listen")
            .`when`()
            .get("/anagram/check/{anagram}")
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .extract().response()
        assertTrue(response.body.asString().equals("[silent]"))
    }

    @Test
    fun `getAnagrams with non-existing anagram`() {
        val response = given()
            .pathParam("anagram", "hello")
            .`when`()
            .get("/anagram/check/{anagram}")
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .extract().response()

        assertTrue(response.body.asString().equals("[]"))
    }

    @Test
    fun `getAnagrams with empty string`() {
        given()
            .pathParam("anagram", "")
            .`when`()
            .get("/anagram/check/{anagram}")
            .then()
            .statusCode(405)
    }
}