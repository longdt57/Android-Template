package lee.module.kotlin.core.domain.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll

abstract class BaseFlowUseCase<in I, O> {

    operator fun invoke(param: I, strategy: Strategy = Strategy.REMOTE_ONLY): Flow<O> = when (strategy) {
        Strategy.REMOTE_ONLY -> fetchRemote(param)
        Strategy.LOCAL_ONLY -> getLocal(param)
        Strategy.REMOTE_FALLBACK_LOCAL -> fetchRemoteFallbackLocal(param)
        Strategy.LOCAL_FALLBACK_REMOTE -> fetchLocalFallbackRemote(param)
    }

    fun fetchRemoteFallbackLocal(param: I): Flow<O> {
        return fetchRemote(param).catch {
            emitAll(getLocal(param))
        }
    }

    fun fetchLocalFallbackRemote(param: I): Flow<O> {
        return getLocal(param).catch {
            emitAll(fetchRemote(param))
        }
    }

    abstract fun fetchRemote(param: I): Flow<O>
    abstract fun getLocal(param: I): Flow<O>

    enum class Strategy {
        REMOTE_ONLY, LOCAL_ONLY, REMOTE_FALLBACK_LOCAL, LOCAL_FALLBACK_REMOTE;
    }
}
