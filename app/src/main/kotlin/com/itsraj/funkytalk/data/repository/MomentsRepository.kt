package com.itsraj.funkytalk.data.repository

import com.itsraj.funkytalk.FunkyTalkApp
import com.itsraj.funkytalk.data.model.Moment
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MomentsRepository {
    private val supabase = FunkyTalkApp.supabase

    suspend fun getMoments(userId: String, limit: Long = 20L): List<Moment> = withContext(Dispatchers.IO) {
        try {
            supabase.postgrest["moments"]
                .select {
                    filter { eq("user_id", userId) }
                    order("created_at", order = Order.DESCENDING)
                    limit(limit)
                }
                .decodeList<Moment>()
        } catch (e: Exception) { emptyList() }
    }

    suspend fun getFeedMoments(limit: Long = 20L): List<Moment> = withContext(Dispatchers.IO) {
        try {
            supabase.postgrest["moments"]
                .select {
                    order("created_at", order = Order.DESCENDING)
                    limit(limit)
                }
                .decodeList<Moment>()
        } catch (e: Exception) { emptyList() }
    }
}
