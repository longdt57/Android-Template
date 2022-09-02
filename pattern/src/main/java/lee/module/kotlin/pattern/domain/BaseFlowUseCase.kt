package lee.module.kotlin.pattern.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

abstract class BaseFlowUseCase<in I, O> {

    suspend fun invokeRemoteOnly(param: I): Flow<O> = invoke(param, Strategy.REMOTE_ONLY)
    suspend fun invokeLocalOnly(param: I): Flow<O> = invoke(param, Strategy.LOCAL_ONLY)

    suspend operator fun invoke(param: I, strategy: Strategy = Strategy.NORMAL): Flow<O> = when (strategy) {
        Strategy.REMOTE_ONLY -> fetchRemoteAndStore(param)
        Strategy.LOCAL_ONLY -> getLocal(param)
        Strategy.NORMAL -> {
            runCatching { fetchRemoteAndStore(param) } // Fetch Remote, don't emit value
            getLocal(param)
        }
    }.flowOn(Dispatchers.IO)

    protected abstract suspend fun fetchRemote(param: I): Flow<O>
    protected abstract suspend fun storeLocal(param: I, data: O)
    protected abstract suspend fun getLocal(param: I): Flow<O>

    private suspend fun fetchRemoteAndStore(param: I): Flow<O> {
        return fetchRemote(param).onEach {
            storeLocal(param, it)
        }
    }

    enum class Strategy {
        NORMAL, REMOTE_ONLY, LOCAL_ONLY;
    }
}
