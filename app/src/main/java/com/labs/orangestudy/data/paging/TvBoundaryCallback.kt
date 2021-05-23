package com.labs.orangestudy.data.paging

import android.util.Log
import androidx.paging.PagedList
import com.labs.orangestudy.data.model.Tv
import com.labs.orangestudy.data.repository.TvRepository
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TvBoundaryCallback(private val viewModelScope: CoroutineScope,private val repository: TvRepository) : PagedList.BoundaryCallback<Tv>() {

    private val realm = Realm.getDefaultInstance()

    override fun onZeroItemsLoaded() {
        Log.e("myLogs", "boundary")
        viewModelScope.launch {
           val request = repository.getTvList(4)
            if (request != null) {
                addTvToDB(request.results)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Tv) {
        Log.e("myLogs", "boundary")
        viewModelScope.launch {
            val request = repository.getTvList(3)
        }
    }

    private fun addTvToDB(list: ArrayList<Tv>) {
        try {
            realm.beginTransaction()

            realm.copyToRealmOrUpdate(list)
            realm.commitTransaction()
            Log.e("myLogsDB",list.size.toString())

        } catch (e:Exception) {
            Log.e("myLogs", "Error ${e.message}")
        }
    }

}