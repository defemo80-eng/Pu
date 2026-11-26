package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FilterAltOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.ui.components.AntiqueDivider
import com.example.ui.components.GlassVitrineDisplay
import com.example.ui.components.MuseumStoneBackground
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BoneParchment
import com.example.ui.theme.BurnishedGold
import com.example.ui.theme.ConfirmedEmerald
import com.example.ui.theme.CryptShadow
import com.example.ui.theme.DarkBronzeGold
import com.example.ui.theme.MutedPewter
import com.example.ui.theme.NocturnalPlum
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.PartialAmber
import com.example.ui.theme.PendingSilver
import com.example.ui.theme.StoneBorder
import com.example.ui.theme.VelvetAmethyst
import com.example.ui.theme.WarmAlabaster
import com.example.ui.theme.WineRed
import com.example.ui.viewmodel.CatalogUiState

private sealed interface CatalogGridItem {
    data class EraBanner(val era: EraPeriod, val count: Int) : CatalogGridItem
    data class CollectionBanner(
        val key: String,
        val collectionName: String,
        val yearLabel: String,
        val count: Int
    ) : CatalogGridItem

    data class FigureCardItem(val figure: FigureEntity) : CatalogGridItem
}

@Composable
fun CatalogScreen(
    state: CatalogUiState,
    onSelectEra: (EraPeriod?) -> Unit,
    onSelectYear: (Int?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClearFilters: () -> Unit,
    onFigureClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showArchiveDialog by remember { mutableStateOf(false) }

    val gridItems = remember(state.filteredFigures) {
        buildChronologicalGridItems(state.filteredFigures)
    }

    MuseumStoneBackground(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            val columns = when {
                maxWidth >= 900.dp -> 4
                maxWidth >= 600.dp -> 3
                else -> 2
            }

            Column(modifier = Modifier.fillMaxSize()) {
                // Museum Wing Top Header
                Surface(
                    color = ObsidianBlack.copy(alpha = 0.85f),
                    border = BorderStroke(0.5.dp, StoneBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "DRACUVERSUS",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = BurnishedGold
                                )
                                Text(
                                    text = "Catálogo cronológico • 1989–2026",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MutedPewter
                                )
                            }

                            OutlinedButton(
                                onClick = { showArchiveDialog = true },
                                border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.7f)),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("archive_info_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Estado del archivo histórico",
                                    tint = AntiqueGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${state.totalCount}/~700",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = BurnishedGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Search Bar
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = onSearchQueryChange,
                            placeholder = {
                                Text(
                                    text = "Buscar personaje o colección...",
                                    color = MutedPewter
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Buscar",
                                    tint = AntiqueGold
                                )
                            },
                            trailingIcon = {
                                if (state.searchQuery.isNotEmpty()) {
                                    IconButton(
                                        onClick = { onSearchQueryChange("") },
                                        modifier = Modifier.minimumInteractiveComponentSize()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Close,
                                            contentDescription = "Limpiar búsqueda",
                                            tint = WarmAlabaster
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AntiqueGold,
                                unfocusedBorderColor = StoneBorder,
                                focusedContainerColor = CryptShadow,
                                unfocusedContainerColor = CryptShadow,
                                focusedTextColor = BoneParchment,
                                unfocusedTextColor = BoneParchment,
                                cursorColor = AntiqueGold
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("search_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Era Tabs (1989-1999, 2000-2009, 2010-2019, 2020-2026)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            EraFilterChip(
                                label = "Todas las épocas",
                                selected = state.selectedEra == null,
                                onClick = { onSelectEra(null) },
                                tag = "era_chip_all"
                            )
                            EraPeriod.entries.forEach { era ->
                                EraFilterChip(
                                    label = era.title,
                                    selected = state.selectedEra == era,
                                    onClick = { onSelectEra(era) },
                                    tag = "era_chip_${era.code}"
                                )
                            }
                        }

                        // Year Filter Row
                        if (state.availableYears.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .horizontalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Año:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MutedPewter,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                FilterChip(
                                    selected = state.selectedYear == null,
                                    onClick = { onSelectYear(null) },
                                    label = { Text("Todos", style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = WineRed,
                                        selectedLabelColor = BoneParchment,
                                        containerColor = CryptShadow,
                                        labelColor = MutedPewter
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = state.selectedYear == null,
                                        borderColor = StoneBorder,
                                        selectedBorderColor = AntiqueGold
                                    ),
                                    modifier = Modifier.testTag("year_chip_all")
                                )
                                state.availableYears.forEach { year ->
                                    val selected = state.selectedYear == year
                                    FilterChip(
                                        selected = selected,
                                        onClick = { onSelectYear(year) },
                                        label = { Text(year.toString(), style = MaterialTheme.typography.labelSmall) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = WineRed,
                                            selectedLabelColor = BoneParchment,
                                            containerColor = CryptShadow,
                                            labelColor = MutedPewter
                                        ),
                                        border = FilterChipDefaults.filterChipBorder(
                                            enabled = true,
                                            selected = selected,
                                            borderColor = StoneBorder,
                                            selectedBorderColor = AntiqueGold
                                        ),
                                        modifier = Modifier.testTag("year_chip_$year")
                                    )
                                }
                            }
                        }
                    }
                }

                // Content Grid
                when {
                    state.isLoading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(color = AntiqueGold)
                        }
                    }

                    state.filteredFigures.isEmpty() -> {
                        EmptyCatalogState(
                            onClearFilters = onClearFilters,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 12.dp,
                                bottom = 28.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .testTag("catalog_grid")
                        ) {
                            items(
                                items = gridItems,
                                span = { item ->
                                    when (item) {
                                        is CatalogGridItem.EraBanner,
                                        is CatalogGridItem.CollectionBanner -> GridItemSpan(maxLineSpan)
                                        is CatalogGridItem.FigureCardItem -> GridItemSpan(1)
                                    }
                                },
                                key = { item ->
                                    when (item) {
                                        is CatalogGridItem.EraBanner -> "era_${item.era.code}"
                                        is CatalogGridItem.CollectionBanner -> "col_${item.key}"
                                        is CatalogGridItem.FigureCardItem -> item.figure.id
                                    }
                                }
                            ) { item ->
                                when (item) {
                                    is CatalogGridItem.EraBanner -> {
                                        EraSectionHeader(era = item.era, count = item.count)
                                    }

                                    is CatalogGridItem.CollectionBanner -> {
                                        CollectionSectionHeader(
                                            collectionName = item.collectionName,
                                            yearLabel = item.yearLabel,
                                            count = item.count
                                        )
                                    }

                                    is CatalogGridItem.FigureCardItem -> {
                                        MuseumFigureCard(
                                            figure = item.figure,
                                            onClick = { onFigureClick(item.figure.id) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showArchiveDialog) {
        ArchiveTransparencyDialog(
            state = state,
            onDismiss = { showArchiveDialog = false }
        )
    }
}

@Composable
private fun EraFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = AntiqueGold,
            selectedLabelColor = ObsidianBlack,
            containerColor = NocturnalPlum,
            labelColor = WarmAlabaster
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = StoneBorder,
            selectedBorderColor = BurnishedGold
        ),
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .testTag(tag)
    )
}

@Composable
private fun EraSectionHeader(
    era: EraPeriod,
    count: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp)
    ) {
        AntiqueDivider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ALA ${era.title}",
                    style = MaterialTheme.typography.labelLarge,
                    color = AntiqueGold
                )
                Text(
                    text = era.subtitle,
                    style = MaterialTheme.typography.headlineLarge,
                    color = BoneParchment
                )
            }
            Surface(
                color = VelvetAmethyst,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.5f))
            ) {
                Text(
                    text = "$count registros",
                    style = MaterialTheme.typography.labelSmall,
                    color = BurnishedGold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun CollectionSectionHeader(
    collectionName: String,
    yearLabel: String,
    count: Int
) {
    Surface(
        color = NocturnalPlum.copy(alpha = 0.88f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, DarkBronzeGold.copy(alpha = 0.45f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = collectionName,
                    style = MaterialTheme.typography.titleLarge,
                    color = BoneParchment
                )
                Text(
                    text = "Temporada $yearLabel",
                    style = MaterialTheme.typography.labelMedium,
                    color = BurnishedGold
                )
            }
            Text(
                text = if (count == 1) "1 entrada" else "$count piezas",
                style = MaterialTheme.typography.labelMedium,
                color = MutedPewter
            )
        }
    }
}

@Composable
private fun MuseumFigureCard(
    figure: FigureEntity,
    onClick: () -> Unit
) {
    Surface(
        color = NocturnalPlum.copy(alpha = 0.90f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, StoneBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
            .testTag("figure_card_${figure.id}")
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            GlassVitrineDisplay(
                figure = figure,
                height = 136.dp,
                compact = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = figure.name,
                style = MaterialTheme.typography.titleMedium,
                color = BoneParchment,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                minLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = figure.collectionName,
                style = MaterialTheme.typography.labelSmall,
                color = BurnishedGold.copy(alpha = 0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            VerificationBadge(
                statusCode = figure.verificationStatus,
                compact = true
            )
        }
    }
}

@Composable
private fun EmptyCatalogState(
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(32.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.FilterAltOff,
            contentDescription = null,
            tint = AntiqueGold,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Sin piezas en esta búsqueda",
            style = MaterialTheme.typography.headlineMedium,
            color = BoneParchment,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Prueba limpiando los filtros de época o año para recorrer todo el archivo de 1989 a 2026.",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedPewter,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedButton(
            onClick = onClearFilters,
            border = BorderStroke(1.dp, AntiqueGold),
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .testTag("clear_filters_button")
        ) {
            Text("Mostrar todo el catálogo", color = BurnishedGold)
        }
    }
}

@Composable
private fun ArchiveTransparencyDialog(
    state: CatalogUiState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = NocturnalPlum,
        titleContentColor = BurnishedGold,
        textContentColor = WarmAlabaster,
        title = {
            Text(
                text = "Expediente de Autenticidad",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "El universo histórico de las paletas Drácula (1989–2026) reúne aproximadamente 700 muñequitos, variantes de color y promocionales. Por rigor histórico, Dracuversus no inventa nombres, fechas ni historias para completar esa cifra.",
                    style = MaterialTheme.typography.bodyMedium
                )
                AntiqueDivider()
                Text(
                    text = "• Registros catalogados actualmente: ${state.totalCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BoneParchment
                )
                Text(
                    text = "• Fichas confirmadas: ${state.confirmedCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ConfirmedEmerald
                )
                Text(
                    text = "• Confirmadas con lore/foto parcial: ${state.partialCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PartialAmber
                )
                Text(
                    text = "• Espacios y años en investigación: ${state.pendingCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PendingSilver
                )
                AntiqueDivider()
                Text(
                    text = "Cada ficha muestra «Fotografía pendiente» hasta que añadas personalmente su imagen desde la galería de tu teléfono (guardada de forma persistente y sin internet dentro de la aplicación).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedPewter
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Text("ENTENDIDO", color = BurnishedGold)
            }
        }
    )
}

private fun buildChronologicalGridItems(figures: List<FigureEntity>): List<CatalogGridItem> {
    if (figures.isEmpty()) return emptyList()
    val result = mutableListOf<CatalogGridItem>()

    val byEra = figures.groupBy { EraPeriod.fromCode(it.era) }
    for (era in EraPeriod.entries) {
        val eraFigures = byEra[era] ?: continue
        result += CatalogGridItem.EraBanner(era = era, count = eraFigures.size)

        val byCollection = eraFigures.groupBy { "${it.yearLabel}::${it.collectionName}" }
        for ((key, collectionFigures) in byCollection) {
            val first = collectionFigures.first()
            result += CatalogGridItem.CollectionBanner(
                key = key,
                collectionName = first.collectionName,
                yearLabel = first.yearLabel,
                count = collectionFigures.size
            )
            collectionFigures.forEach { fig ->
                result += CatalogGridItem.FigureCardItem(fig)
            }
        }
    }
    return result
}
