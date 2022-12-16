package tech.noope.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.noope.common.domain.RawgUrl
import tech.noope.common.domain.RawgApi
import tech.noope.common.domain.RawgRepository
import tech.noope.common.domain.database.DatabaseConst
import tech.noope.common.domain.database.GameDao
import tech.noope.common.domain.database.RawgDataBase
import tech.noope.common.domain.remote.RawgDataStore
import tech.noope.network.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient<RawgApi> {
        return ApiClient(RawgUrl.BASE_RAWG_URL, RawgApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRawgDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RawgDataBase::class.java,
        DatabaseConst.Table.GAME_TABLE
    ).build()

    @Provides
    @Singleton
    fun provideGameDao(
        rawgDataBase: RawgDataBase
    ) = rawgDataBase.gameDao()

    @Provides
    @Singleton
    fun provideRepository(
        service: ApiClient<RawgApi>,
        gameDao: GameDao
    ): RawgRepository {
        return RawgDataStore(service, gameDao)
    }
}