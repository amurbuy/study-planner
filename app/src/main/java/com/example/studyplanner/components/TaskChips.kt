package com.example.studyplanner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studyplanner.model.Priority
import com.example.studyplanner.model.Subject
import com.example.studyplanner.ui.theme.PriorityHigh
import com.example.studyplanner.ui.theme.PriorityLow
import com.example.studyplanner.ui.theme.PriorityMedium
import com.example.studyplanner.ui.theme.StudyPlannerTheme

@Composable
fun SubjectChip(subject: Subject, modifier: Modifier = Modifier) {
    LabelChip(
        text = subject.displayName,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = modifier
    )
}

@Composable
fun PriorityChip(priority: Priority, modifier: Modifier = Modifier) {
    val color = when (priority) {
        Priority.HIGH -> PriorityHigh
        Priority.MEDIUM -> PriorityMedium
        Priority.LOW -> PriorityLow
    }
    LabelChip(
        text = priority.displayName,
        containerColor = color.copy(alpha = 0.15f),
        contentColor = color,
        modifier = modifier
    )
}

@Composable
private fun LabelChip(
    text: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskChipsPreview() {
    StudyPlannerTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SubjectChip(Subject.MATH)
            PriorityChip(Priority.HIGH)
            PriorityChip(Priority.MEDIUM)
            PriorityChip(Priority.LOW)
        }
    }
}