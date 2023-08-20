package kz.asetkenes.learnandroid.core

import kz.asetkenes.learnandroid.common.core.Container.Error
import kz.asetkenes.learnandroid.common.core.Container.Pending
import kz.asetkenes.learnandroid.common.core.Container.Success
import kz.asetkenes.learnandroid.testutils.wellDone
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class ContainerTest {

    @Test(expected = Exception::class)
    fun getOrThrowExceptionThrowsExceptionForErrorContainer() {
        val error = Error(Exception())

        error.getOrThrowException()

        wellDone()
    }

    @Test(expected = IllegalStateException::class)
    fun getOrThrowExceptionThrowsNothingForPendingContainer() {
        val pending = Pending

        pending.getOrThrowException()

        wellDone()
    }

    @Test
    fun getOrThrowExceptionReturnValueForSuccessContainer() {
        val success = Success("value")

        val successValue = success.getOrThrowException()

        assertEquals("value", successValue)
    }

    @Test
    fun getOrNullReturnsNullForNonSuccessContainer() {
        val error = Error(Exception())
        val pending = Pending

        val errorValue = error.getOrNull()
        val pendingValue = pending.getOrNull()

        assertNull(errorValue)
        assertNull(pendingValue)
    }

    @Test
    fun getOrNullReturnsValueForSuccessContainer() {
        val success = Success("value")

        val successValue = success.getOrNull()

        assertEquals("value", successValue)
    }

    @Test
    fun mapNonSuccessContainerMapping() {
        val exception = Exception()
        val error = Error(exception)
        val pending = Pending

        val mappedErrorResult = error.map<Int>()
        val mappedPendingResult = pending.map<Int>()

        assertTrue(mappedErrorResult is Error)
        assertTrue(mappedPendingResult is Pending)
        assertSame(exception, (mappedErrorResult as Error).exception)
    }

    @Test(expected = IllegalStateException::class)
    fun mapWithoutMapperCantConvertSuccessContainer() {
        val container = Success("value")

        container.map<Int>()

        wellDone()
    }

    @Test
    fun mapWithMapperConvertSuccessToSuccess() {
        val container = Success(321)

        val mappedContainer = container.map { it.toLong() }

        assertTrue(mappedContainer is Success)
        assertEquals(321L, (mappedContainer as Success).value)
    }

}