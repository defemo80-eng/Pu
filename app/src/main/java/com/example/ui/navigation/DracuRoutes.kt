package com.example.ui.navigation

object DracuRoutes {
    const val WELCOME = "welcome"
    const val CATALOG = "catalog"
    const val FIGURE_DETAIL = "figure_detail/{figureId}"

    fun figureDetail(figureId: String): String = "figure_detail/$figureId"
}
