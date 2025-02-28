package com.hidden.client.ui.viewmodels.injection.module

import com.hidden.client.apis.*
import com.hidden.client.helpers.*
import com.hidden.client.helpers.nullable.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */

    /*-------------------------------------------------------------------
    Login
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    /*-------------------------------------------------------------------
    Candidate List
    ------------------------------------------------------------------- */

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideCandidateApi(retrofit: Retrofit): CandidateApi {
        return retrofit.create(CandidateApi::class.java)
    }

    /*-------------------------------------------------------------------
    Dashboard
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideDashboardApi(retrofit: Retrofit): DashboardApi {
        return retrofit.create(DashboardApi::class.java)
    }

    /*-------------------------------------------------------------------
    Shortlists
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideShortlistApi(retrofit: Retrofit): ShortlistApi {
        return retrofit.create(ShortlistApi::class.java)
    }
    /*-------------------------------------------------------------------
    Job Setting
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideJobApi(retrofit: Retrofit): JobApi {
        return retrofit.create(JobApi::class.java)
    }

    /*-------------------------------------------------------------------
    Process
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideProcessApi(retrofit: Retrofit): ProcessApi {
        return retrofit.create(ProcessApi::class.java)
    }

    /*-------------------------------------------------------------------
    Message
    ------------------------------------------------------------------- */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideMessageApi(retrofit: Retrofit): ConversationApi {
        return retrofit.create(ConversationApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {

        val baseUrl: String = when (APP.environment) {
            Environment.Production.env -> {
                API.production
            }
            Environment.Development.env -> {
                API.development
            }
            else -> {
                API.development
            }
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(NullToEmptyStringAdapter())
            .add(NullToZeroAdapter())
            .add(NullToFalseAdapter())
            .add(NullToEmptyBrandList())
            .add(NullToEmptyProjectList())
            .add(NullToEmptyProjectAssetsList())
            .add(NullToEmptySkillList())
            .add(NullToEmptyWorkExperienceList())
            .add(NullToEmptyFeedbackList())
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}