package ru.tyurin.foodies.ui.screens.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.tyurin.foodies.domain.models.Tag
import ru.tyurin.foodies.ui.screens.stateholders.CatalogViewModel
import ru.tyurin.foodies.ui.theme.Dark
import ru.tyurin.foodies.ui.theme.Orange
import ru.tyurin.foodies.ui.theme.White

/**
* Экран тегов. Отображает теги из эндпоинта Tags.json
* По ТЗ недостаточно ясно, можно ли выбирать один тег или несколько,
 * поэтому здесь можно выбрать несколько
 **/

@Composable
fun TagScreen(catalogViewModel: CatalogViewModel) {
    val tagsState = catalogViewModel.tags.collectAsState()
    val tags = tagsState.value
    val selectedTags = remember { mutableSetOf<Tag>() }

    Column {
        TagList(
            tags = tags,
            selectedTags = selectedTags
        ) { tag, isSelected ->
            if (isSelected) {
                selectedTags.add(tag)
            } else {
                selectedTags.remove(tag)
            }
        }
    }
}

@Composable
fun TagList(tags: List<Tag>, selectedTags: Set<Tag>, onTagSelected: (Tag, Boolean) -> Unit) {
    LazyRow {
        items(tags) { tag ->
            val pickedTags = selectedTags.contains(tag)
            TagItem(tag = tag, isSelected = pickedTags) { selectedTag, isSelected ->
                    onTagSelected(selectedTag, isSelected)
            }
        }
    }

}

@Composable
fun TagItem(tag: Tag, isSelected: Boolean = false, onTagSelected: (Tag, Boolean) -> Unit) {

    val (selected, setSelected) = remember { mutableStateOf(isSelected) }
    val backgroundColor = if (selected) Orange else White
    val contentColor = if (selected) White else Dark

    FilledTonalButton(
        onClick = {
            setSelected(!selected)
            onTagSelected(tag, !selected)
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = tag.name
        )
    }
}

@Composable
fun MockTags() {
    val tags = listOf(
        Tag(id = 0, "Суши"),
        Tag(id = 1, "Груши"),
        Tag(id = 2, "Яблоки"),
    )
    val selectedTags = remember { mutableSetOf<Tag>() }

    Column {
        TagList(
            tags = tags,
            selectedTags = selectedTags
        ) { tag, isSelected ->
            if (isSelected) {
                selectedTags.add(tag)
            } else {
                selectedTags.remove(tag)
            }
        }
    }
}

@Preview
@Composable
private fun MockTagsPreview() {
    MockTags()
}