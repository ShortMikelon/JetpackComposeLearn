package kz.asetkenes.learnandroid.ui.viewutils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SideEffectTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun getReturnsValueOnlyOnce() {
        val sideEffect = SideEffect("some value")

        val value1 = sideEffect.get()
        val value2 = sideEffect.get()

        assertEquals("some value", value1)
        assertNull(value2)
    }

    @Test
    fun publishSideEffectSendsSideEffectToStateFlow() {
        val mutableStateFlow = MutableStateFlow(SideEffect(""))
        val stateFlow = mutableStateFlow.asStateFlow()

        mutableStateFlow.publishSideEffect("new side effect")

        val sideEffect = stateFlow.value
        assertEquals("new side effect", sideEffect.get())
    }
}