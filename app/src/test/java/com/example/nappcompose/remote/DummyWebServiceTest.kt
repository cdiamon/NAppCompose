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
    fun testGetUserDetails() {
        assertEquals(dummyWebService.getUsers().size, 4)

        assertEquals(dummyWebService.getUsers().filter { !it.deleted }.size, 3)
    }

    @Test
    fun testGetAllUsers() {
        val allUsers = dummyWebService.getAllUsers()

        assertEquals(allUsers.isEmpty(), false)

        assertNotNull(allUsers.find { it is FirstT })
        assertNotNull(allUsers.find { it is SecondT })
        assertNotNull(allUsers.find { it is ThirdT })
    }

    @Test
    fun testGetAllDetails() {
        val allUsers = dummyWebService.getAllUsers()

        assertEquals(allUsers.isEmpty(), false)

        assertNotNull(allUsers.find { it is FirstT })
        assertNotNull(allUsers.find { it is SecondT })
        assertNotNull(allUsers.find { it is ThirdT })
    }

    @Test
    fun testGetDetails() {
        assertEquals(dummyWebService.getAllUsers().isEmpty(), false)
    }
}
