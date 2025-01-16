package com.openclassrooms.p12_joiefull.data.repository

import com.openclassrooms.p12_joiefull.data.network.ClothingApi
import com.openclassrooms.p12_joiefull.data.response.ResponseApiClothingItem
import com.openclassrooms.p12_joiefull.data.response.toClothing
import com.openclassrooms.p12_joiefull.domain.util.PossibleError
import com.openclassrooms.p12_joiefull.domain.util.Result
import com.squareup.moshi.JsonDataException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException


class ClothingRepositoryTest {
    private lateinit var cut: ClothingRepository //Class Under Test
    private lateinit var dataService: ClothingApi

    @Before
    fun setup() {
        dataService = mockk()
        cut = ClothingRepository(dataService)
    }

    @Test
    fun `assert when callClothingApi is requested then clean data is provided`() = runTest {
        //given
        val clothingApiResponse: List<ResponseApiClothingItem> = listOf(
            ResponseApiClothingItem(
                "T-shirt",
                1,
                10,
                "T-shirt",
                10.0,
                ResponseApiClothingItem.Picture(
                    "T-shirt",
                    "https://www.google.com"
                ),
                10.0
            )
        )
        coEvery {
            dataService.getClothes()
        } returns Response.success(clothingApiResponse)

        //when
        val values = run {
            cut.callClothingApi().toList()
        }

        //then
        coVerify { dataService.getClothes() }
        assertEquals(2, values.size)
        assertEquals(Result.Loading, values[0])
        assertEquals(Result.Success(clothingApiResponse.map { it.toClothing() }), values[1])
    }

    @Test
    fun `assert when callClothingApi fail with unknown error then unknown error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } returns Response.error(404, "Not Found".toResponseBody("text/plain".toMediaType()))

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.UNKNOWN), values[1])
        }

    @Test
    fun `assert when callClothingApi fail with server error then server error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } returns Response.error(
                500,
                "Internal Server Error".toResponseBody("text/plain".toMediaType())
            )

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.SERVER_ERROR), values[1])
        }

    @Test
    fun `assert when callClothingApi fail with request timeout then request timeout error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } returns Response.error(
                408,
                "Request Timeout".toResponseBody("text/plain".toMediaType())
            )

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.REQUEST_TIMEOUT), values[1])
        }

    @Test
    fun `assert when callClothingApi fail with too many requests then too many requests error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } returns Response.error(
                429,
                "Too Many Requests".toResponseBody("text/plain".toMediaType())
            )

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.TOO_MANY_REQUESTS), values[1])
        }

    @Test
    fun `assert when callClothingApi fail with serialization error then serialization error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } throws JsonDataException("Failed to parse JSON")

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.SERIALIZATION), values[1])
        }

    @Test
    fun `assert when callClothingApi fail with no internet error then no internet error is provided`() =
        runTest {
            //given
            coEvery {
                dataService.getClothes()
            } throws UnresolvedAddressException()

            //when
            val values = run {
                cut.callClothingApi().toList()
            }

            //then
            coVerify { dataService.getClothes() }
            assertEquals(2, values.size)
            assertEquals(Result.Loading, values[0])
            assertEquals(Result.Error(PossibleError.NO_INTERNET), values[1])
        }


}