package com.example.nappcompose.domain.mappers

import com.example.nappcompose.mocks.mockUserDetailsModel
import com.example.nappcompose.mocks.mockUserDetailsResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertSame

import org.junit.Before
import org.junit.Test

class UserDetailsMapperTest {

    private val responseMock = mockUserDetailsResponse()
    private val modelMock = mockUserDetailsModel()

    @Test
    fun map() {
        val model = UserDetailsMapper().map(responseMock)
        assertSame(modelMock, model)
    }
}
