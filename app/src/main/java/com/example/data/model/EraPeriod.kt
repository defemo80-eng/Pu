package com.example.data.model

enum class EraPeriod(
    val code: String,
    val title: String,
    val subtitle: String,
    val startYear: Int,
    val endYear: Int
) {
    ERA_1989_1999(
        code = "1989-1999",
        title = "1989–1999",
        subtitle = "Orígenes y Edad Dorada",
        startYear = 1989,
        endYear = 1999
    ),
    ERA_2000_2009(
        code = "2000-2009",
        title = "2000–2009",
        subtitle = "Transición del Milenio",
        startYear = 2000,
        endYear = 2009
    ),
    ERA_2010_2019(
        code = "2010-2019",
        title = "2010–2019",
        subtitle = "Renacimiento Luminoso",
        startYear = 2010,
        endYear = 2019
    ),
    ERA_2020_2026(
        code = "2020-2026",
        title = "2020–2026",
        subtitle = "Era Moderna y Dracuverso",
        startYear = 2020,
        endYear = 2026
    );

    companion object {
        fun fromCode(code: String): EraPeriod =
            entries.firstOrNull { it.code == code } ?: ERA_1989_1999

        fun fromYear(year: Int): EraPeriod =
            entries.firstOrNull { year in it.startYear..it.endYear } ?: ERA_2020_2026
    }
}
