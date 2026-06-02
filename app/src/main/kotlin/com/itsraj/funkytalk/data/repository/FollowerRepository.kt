package com.itsraj.funkytalk.data.repository

import com.itsraj.funkytalk.FunkyTalkApp
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FollowerRepository {
    private val supabase = FunkyTalkApp.supabase

    suspend fun getFollowerCount(userId: String): Int = withContext(Dispatchers.IO) {
        try {
            supabase.postgrest["followers"]
                .select { filter { eq("following_id", userId) } }
                .decodeList<Map<String, Any?>>()
                .size
        } catch (e: Exception) { 0 }
    }

    suspend fun getFollowingCount(userId: String): Int = withContext(Dispatchers.IO) {
        try {
            supabase.postgrest["followers"]
                .select { filter { eq("follower_id", userId) } }
                .decodeList<Map<String, Any?>>()
                .size
        } catch (e: Exception) { 0 }
    }
}
