package com.itsraj.funkytalk.data.repository

import com.itsraj.funkytalk.FunkyTalkApp
import com.itsraj.funkytalk.data.model.UserProfile
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val supabase = FunkyTalkApp.supabase

    suspend fun getProfiles(): List<UserProfile> = withContext(Dispatchers.IO) {
        try {
            supabase.postgrest["profiles"]
                .select {
                    order("last_seen", order = Order.DESCENDING)
                    limit(100)
                }
                .decodeList<UserProfile>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
