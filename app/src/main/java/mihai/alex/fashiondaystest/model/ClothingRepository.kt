package mihai.alex.fashiondaystest.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mihai.alex.fashiondaystest.request.Service

class ClothingRepository {

    suspend fun getClothing() = flow {
        emit(Service.apiService.getClothing())
    }.flowOn(Dispatchers.IO)

}