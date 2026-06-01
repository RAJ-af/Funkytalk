package com.itsraj.funkytalk.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsraj.funkytalk.data.model.UserProfile
import com.itsraj.funkytalk.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _allProfiles = MutableStateFlow<List<UserProfile>>(emptyList())

    private val _filteredProfiles = MutableStateFlow<List<UserProfile>>(emptyList())
    val filteredProfiles: StateFlow<List<UserProfile>> = _filteredProfiles.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _selectedLanguage = MutableStateFlow<String?>(null)

    init {
        loadProfiles()
    }

    fun loadProfiles() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _allProfiles.value = repository.getProfiles()
                filterProfiles()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load profiles"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterProfiles()
    }

    fun onLanguageSelected(language: String?) {
        _selectedLanguage.value = language
        filterProfiles()
    }

    private fun filterProfiles() {
        val query = _searchQuery.value.trim().lowercase()
        val language = _selectedLanguage.value

        _filteredProfiles.value = _allProfiles.value.filter { profile ->
            val matchesSearch = query.isBlank() ||
                profile.username?.lowercase()?.contains(query) == true ||
                profile.country?.lowercase()?.contains(query) == true

            val matchesLanguage = language == null ||
                profile.learning_languages?.any { it.equals(language, ignoreCase = true) } == true ||
                profile.native_languages?.any { it.equals(language, ignoreCase = true) } == true

            matchesSearch && matchesLanguage
        }
    }
}
