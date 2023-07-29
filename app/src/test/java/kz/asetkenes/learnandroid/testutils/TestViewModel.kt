package kz.asetkenes.learnandroid.testutils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.junit4.MockKRule
import org.junit.Rule

open class TestViewModel {

    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockKRule = MockKRule(this)
}