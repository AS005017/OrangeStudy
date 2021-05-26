package com.labs.orangestudy.data.paging

import androidx.annotation.UiThread
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.model.TvList
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.kotlin.addChangeListener
import io.realm.kotlin.createObject
import io.realm.kotlin.isValid
import io.realm.kotlin.where
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class TvDataStorageSource {
    interface TvDataStorage {
        fun putTv(Tv: Tv)
        fun getTv(id: Int): Tv?
        suspend fun getTvAsync(id: Int): Tv?
        fun putTvs(Tvs: Collection<Tv>)
        fun putTvsNextPage(Tvs: Collection<Tv>)
        fun getTvs(): TvList

        suspend fun getTvsAsync(): TvList
    }

    class LocalTvStorage : TvDataStorage {
        private var realm: Realm? = null

        private fun ensureRealmCreated(): Realm {
            if (realm == null || realm!!.isClosed) {
                realm = Realm.getDefaultInstance()
            }
            return realm!!
        }

        override fun putTv(Tv: Tv) {
            withAsyncTransaction {
                insertOrUpdate(Tv)
            }
        }

        override fun getTv(id: Int): Tv? = with(ensureRealmCreated()) {
            where<Tv>().equalTo("id",id).findFirst()
        }

        @UiThread
        override suspend fun getTvAsync(id: Int): Tv? = suspendCancellableCoroutine { continuation ->
            with(ensureRealmCreated()) {
                val tvWrapper = where<Tv>().equalTo("id", id).findFirstAsync()
                tvWrapper.addChangeListener(RealmChangeListener { tv ->
                    if (tv.isValid()) {
                        continuation.resume(tv)
                    }
                })
            }
        }

        override fun putTvs(Tvs: Collection<Tv>) {
            withAsyncTransaction {
                var cached = where<TvList>().findFirst()
                if (cached != null) {
                    cached.tvs.clear()
                    cached.tvs.addAll(Tvs)
                } else {
                    cached = createObject()
                    cached.tvs.addAll(Tvs)
                }
                cached.lastUpdateTimestamp = System.currentTimeMillis()
            }
        }

        override fun putTvsNextPage(Tvs: Collection<Tv>) {
            withAsyncTransaction {
                var cached = where<TvList>().findFirst()
                if (cached != null) {
                    cached.tvs.addAll(Tvs)
                } else {
                    cached = createObject()
                    cached.tvs.addAll(Tvs)
                }
                cached.lastUpdateTimestamp = System.currentTimeMillis()
            }
        }

        override fun getTvs(): TvList = with(ensureRealmCreated()) {
            var Tvs = where<TvList>().findFirst()
            if (Tvs == null) {
                beginTransaction()
                Tvs = createObject()
                commitTransaction()
            }
            Tvs
        }

        override suspend fun getTvsAsync(): TvList = suspendCancellableCoroutine { continuation ->
            with(ensureRealmCreated()) {
                val tvsWrapper = where<TvList>().findFirstAsync()
                tvsWrapper.addChangeListener(RealmChangeListener { tvs ->
                    if (tvs != null) {
                        if (tvs.isValid()) {
                            continuation.resume(tvs)
                        }
                    } else {
                        continuation.resume(createObject())
                    }
                })
            }
        }

        private fun withAsyncTransaction(block: Realm.() -> Unit) {
            Realm.getDefaultInstance().use { r ->
                r.executeTransactionAsync { realm ->
                    realm.block()
                }
            }
        }
    }
}