package com.example.data.model

enum class VerificationStatus(
    val code: String,
    val label: String,
    val detail: String
) {
    CONFIRMED(
        code = "CONFIRMADA",
        label = "Ficha confirmada",
        detail = "Nombre, colección y año verificados en registros históricos de coleccionistas o prensa."
    ),
    PARTIAL(
        code = "PARCIAL",
        label = "Confirmada (foto/lore parcial)",
        detail = "Pieza confirmada en su colección, con detalles narrativos o fotografía oficial en proceso de verificación."
    ),
    PENDING_ARCHIVE(
        code = "PENDIENTE",
        label = "Pendiente de investigación",
        detail = "Registro reservado para colección o periodo documentado cuyo desglose completo aún se investiga."
    );

    companion object {
        fun fromCode(code: String): VerificationStatus =
            entries.firstOrNull { it.code == code } ?: PENDING_ARCHIVE
    }
}
