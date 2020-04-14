package com.sfeatherstone.starlingroundup.useCase


import arrow.core.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<out Type, in Params> where Type : Any {
    var enableTesting: Boolean = false
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    abstract suspend fun run(params: Params): Either<Exception, Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Exception, Type>) -> Unit = {}
    ) {
        scope.launch {
            val job =
                if (enableTesting) withContext(scope.coroutineContext) { run(params) }
                else withContext(ioDispatcher) { run(params) }
            withContext(scope.coroutineContext) { onResult(job) }
        }
    }
}
