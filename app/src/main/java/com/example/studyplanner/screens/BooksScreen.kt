package com.example.studyplanner.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.studyplanner.components.AppTopBar
import com.example.studyplanner.components.BookCard
import com.example.studyplanner.components.EmptyState
import com.example.studyplanner.components.OptionSelector
import com.example.studyplanner.model.Book
import com.example.studyplanner.model.Subject
import com.example.studyplanner.viewmodel.BooksUiState
import com.example.studyplanner.viewmodel.BooksViewModel

@Composable
fun BooksScreen(
    initialSubject: Subject,
    onBack: () -> Unit,
    viewModel: BooksViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedSubject by viewModel.selectedSubject.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onSubjectSelected(initialSubject)
    }

    Scaffold(
        topBar = { AppTopBar(title = "Study Books", onBack = onBack) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OptionSelector(
                options = Subject.entries,
                selected = selectedSubject ?: initialSubject,
                optionLabel = { it.displayName },
                onOptionSelected = { viewModel.onSubjectSelected(it) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            when (val state = uiState) {
                is BooksUiState.Loading -> LoadingContent()
                is BooksUiState.Success -> BookList(books = state.books)
                is BooksUiState.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.retry() }
                )
                is BooksUiState.Empty -> EmptyState(
                    title = "No books found",
                    message = "There are no books for this subject yet"
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BookList(books: List<Book>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(books) { book ->
            BookCard(book = book)
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Could not load books",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}