package com.example.nappcompose.remote

import junit.framework.TestCase
import org.junit.Test

class DummyWebServiceTest : TestCase() {

    lateinit var dummyWebService: DummyWebService
    lateinit var dummyData: DummyData

    override fun setUp() {
        super.setUp()

        dummyData = DummyData()
        dummyWebService = DummyWebService(dummyData)
    }

    @Test
    fun testGetCryptoWallets() {
        assertEquals(dummyWebService.getCryptoWallets().size, 4)

        assertEquals(dummyWebService.getCryptoWallets().filter { !it.deleted }.size, 3)
    }

    @Test
    fun testGetAllWallets() {
        val allWallets = dummyWebService.getAllWallets()

        assertEquals(allWallets.isEmpty(), false)

        assertNotNull(allWallets.find { it is CryptocoinWallet })
        assertNotNull(allWallets.find { it is FiatWallet })
        assertNotNull(allWallets.find { it is MetalWallet })
    }

    @Test
    fun testGetAllCurrencies() {
        val allWallets = dummyWebService.getAllCurrencies()

        assertEquals(allWallets.isEmpty(), false)

        assertNotNull(allWallets.find { it is Fiat })
        assertNotNull(allWallets.find { it is Metal })
        assertNotNull(allWallets.find { it is Cryptocoin })
    }

    @Test
    fun testGetCurrencyWallets() {
        assertEquals(dummyWebService.getCurrencyWallets().isEmpty(), false)
    }
}
