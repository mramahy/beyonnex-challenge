package com.beyonnex.rest

import com.beyonnex.service.AnagramService
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Path("/anagram")
class AnagramResource (private val service: AnagramService) {


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/check")
    fun checkAnagram(@QueryParam("firstAnagramArgument") firstAnagramArgument: String?,
                 @QueryParam("secondAnagramArgument") secondAnagramArgument: String?): String {
        return service.validateAnagramAndSave(firstAnagramArgument, secondAnagramArgument).toString()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/check/{anagram}")
    fun getAnagrams(@PathParam("anagram") anagram: String): Response {
        val anagrams = service.getCorrespondentAnagrams(anagram) ?: emptySet()
        return Response.ok(anagrams).build()
    }
}