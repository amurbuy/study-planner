package com.example.studyplanner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studyplanner.model.Subject

@Composable
fun SubjectFilterRow(
    selectedSubject: Subject?,
    onSubjectSelected: (Subject?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedSubject == null,
                onClick = { onSubjectSelected(null) },
                label = { Text("All") }
            )
        }
        items(Subject.entries) { subject ->
            FilterChip(
                selected = selectedSubject == subject,
                onClick = { onSubjectSelected(subject) },
                label = { Text(subject.displayName) }
            )
        }
    }
}