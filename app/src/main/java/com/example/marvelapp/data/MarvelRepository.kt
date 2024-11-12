package com.example.marvelapp.data

import java.math.BigInteger
import java.security.MessageDigest

class MarvelRepository(private val api: MarvelApi) {
    private val publicKey = "YOUR_PUBLIC_KEY"
    private val privateKey = "YOUR_PRIVATE_KEY"

    private fun generateHash(timestamp: String): String {
        val input = timestamp + privateKey + publicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }

    suspend fun getCharacters(offset: Int = 0): List<Character> {
        val timestamp = System.currentTimeMillis().toString()
        val hash = generateHash(timestamp)

        return try {
            val response = api.getCharacters(
                apiKey = publicKey,
                timestamp = timestamp,
                hash = hash,
                offset = offset
            )
            response.data.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}