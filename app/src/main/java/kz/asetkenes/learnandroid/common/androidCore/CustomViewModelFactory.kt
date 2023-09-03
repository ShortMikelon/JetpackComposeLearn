package kz.asetkenes.learnandroid.common.androidCore

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.asetkenes.learnandroid.ApplicationScope
import java.lang.reflect.Constructor

class CustomViewModelFactory(
    private val dependencies: List<Any>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val constructors = modelClass.constructors
        val constructor = constructors.maxByOrNull { it.typeParameters.size }!!

        // generating the list of arguments to be passed into the view-model's constructor
        val arguments = findDependencies(constructor, dependencies)

        // creating view-model
        return constructor.newInstance(*arguments.toTypedArray()) as T
    }

    private fun findDependencies(constructor: Constructor<*>, dependencies: List<Any>): List<Any> {
        val args = mutableListOf<Any>()
        // here we iterate through view-model's constructor arguments and for each
        // argument we search dependency that can be assigned to the argument
        constructor.parameterTypes.forEach { parameterClass ->
            val dependency = dependencies.first { parameterClass.isAssignableFrom(it.javaClass) }
            args.add(dependency)
        }
        return args
    }
}

@Composable
inline fun <reified VM: ViewModel> viewModelCreator(): VM = viewModel(factory = CustomViewModelFactory(ApplicationScope.dependencies))