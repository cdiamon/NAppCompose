package com.example.nappcompose.util

import android.util.Base64
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`

class EncryptKtTest {

    private lateinit var mockBase64: MockedStatic<Base64>

    @Before
    fun setUp() {
        mockBase64 = mockStatic(Base64::class.java)
        `when`(Base64.encode(any(), anyInt())).thenAnswer { invocation ->
            java.util.Base64.getMimeEncoder().encode(invocation.arguments[0] as ByteArray)
        }
        `when`(Base64.decode(anyString(), anyInt())).thenAnswer { invocation ->
            java.util.Base64.getMimeDecoder().decode(invocation.arguments[0] as String)
        }
    }

    @Test
    fun `test encrypted string is different`() {
        val testString = "testString"
        val encryptedString = testString.encryptCBC()
        assertNotEquals(testString, encryptedString)
    }

    @Test
    fun `test encrypted and decrypted string is the same`() {
        val testString = "testString"
        val encryptedString = testString.encryptCBC()
        val decryptedString = encryptedString.decryptCBC()
        assertEquals(testString, decryptedString)
    }

    @After
    fun tearDown() {
        mockBase64.close()
    }
}
