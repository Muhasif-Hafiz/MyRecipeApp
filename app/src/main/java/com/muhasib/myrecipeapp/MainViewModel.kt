package com.muhasib.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    data class RecipeState(

        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

    private val _categoryState = mutableStateOf(RecipeState())

    val categotyState: State<RecipeState> = _categoryState

    init {
        fetchCategories()
    }

    private fun fetchCategories() {

        viewModelScope.launch {

            try {
                val response= recipeService.getCategories()
                _categoryState.value=_categoryState.value.copy(
                    list=response.categories,
                    loading = false,
                    error = null
                )
            }catch (e: Exception){

                _categoryState.value=_categoryState.value.copy(
                    loading=false,
                    error="Error Fetching Recipies because ${e.message}"
                )
            }
        }
    }
}