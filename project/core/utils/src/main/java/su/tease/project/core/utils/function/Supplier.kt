package su.tease.project.core.utils.function

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

fun interface Supplier<T> {
    operator fun invoke(): T
}

fun <T> Supplier<T>.pipe(value: Transformer<T>): Supplier<T> = Supplier {
    value.invoke(invoke())
}

fun <T> T.pipe(value: Transformer<T>): Supplier<T> = Supplier {
    value.invoke(this)
}

@Composable
fun <T> State<Supplier<T>>.pipe(value: State<Transformer<T>>): State<Supplier<T>> =
    remember {
        derivedStateOf {
            this.value.pipe(value.value)
        }
    }
