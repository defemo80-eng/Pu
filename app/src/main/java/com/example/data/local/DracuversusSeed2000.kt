package com.example.data.local

import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus

internal object DracuversusSeed2000 {

    fun entries(startOrder: Int): List<FigureEntity> {
        val list = mutableListOf<FigureEntity>()
        var order = startOrder

        // 2000: Huéspedes del Terror (24 figuras confirmadas)
        val huespedes2000 = listOf(
            "Dimitri El Vampiro",
            "Yeti El Pie Grande",
            "Silver El Pirata",
            "El Capitán Morgan",
            "Tritón El Mutante",
            "Fred El Nauseabundo",
            "Karl El Leñador",
            "Helga La Madre Monte",
            "El Jorobado",
            "Jack El Vigía",
            "Úrsula La Hechicera",
            "Stuart El Gato",
            "Crow El Espantapájaros",
            "El Jinete Sin Cabeza",
            "El Fantasma de las Tinieblas",
            "El Verdugo de la Torre",
            "El Conde Drácula",
            "Igor El Mayordomo",
            "Gork El Simio",
            "Rulf El Hombre Lobo",
            "La Momia",
            "Ted El Malvado",
            "El Doctor Cerebro",
            "Albert Von Saverkraut"
        )

        huespedes2000.forEachIndexed { index, name ->
            val num = index + 1
            val extraNote = when (name) {
                "Igor El Mayordomo" -> "\n\nDentro de las narrativas del castillo de Drácula, Igor figura como parte de la servidumbre clásica y aparece registrado con el número 18 de este set."
                "Helga La Madre Monte" -> "\n\nDestaca por adaptar un mito tradicional colombiano (La Madremonte) dentro de una galería clásica de monstruos del terror."
                else -> ""
            }
            list += FigureEntity(
                id = String.format("DRAC-2000-%03d", num),
                name = name,
                year = 2000,
                yearLabel = "2000",
                era = EraPeriod.ERA_2000_2009.code,
                collectionName = "Huéspedes del Terror",
                subGroup = "Pieza #$num de 24",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura #$num de la colección Huéspedes del Terror lanzada con el cambio de milenio en el año 2000.",
                history = "En el año 2000, Crem Helado lanzó la colección Huéspedes del Terror, integrada por 24 figuras acompañadas de tarjetas de poder e historias ambientadas en la mansión y los dominios del Conde Drácula.$extraNote\n\nEl texto completo original de la tarjeta de '$name' continúa pendiente de transcripción literal en el archivo del museo.",
                curiosities = "• Cerró el ciclo de las colecciones clásicas de 24 miniaturas de cambio de siglo.\n• Incluía tarjetas con historia y valores de poder.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista oficial de 24 nombres verificada en archivos de coleccionistas colombianos.",
                sortOrder = order++
            )
        }

        // 2001–2005: Expediente abierto de transición
        list += FigureEntity(
            id = "DRAC-2001-EXP",
            name = "Temporadas de Transición (2001–2005)",
            year = 2001,
            yearLabel = "2001–2005",
            era = EraPeriod.ERA_2000_2009.code,
            collectionName = "Archivo 2001–2005 (En investigación)",
            subGroup = "Expediente abierto",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Periodo documentado de transición entre figuras de goma y accesorios de Halloween.",
            history = "Según las crónicas de prensa y coleccionismo colombiano (Infobae, GamerFocus y blogs especializados), entre 2001 y 2005 las campañas de la Paleta Drácula comenzaron a alternar las miniaturas de goma con otro tipo de sorpresas de temporada: calcomanías, tatuajes temporales, antifaces, anillos, ojos y uñas plásticas, además de nuevas versiones de colmillos neón.\n\nNo existe aún un listado completo verificado de nombres individuales por año para este quinquenio; este expediente documenta el periodo sin fabricar registros ficticios.",
            curiosities = "• Marca el inicio de la diversificación de sorpresas físicas más allá de las miniaturas tradicionales.",
            verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
            sourceNote = "Fuentes: Infobae, GamerFocus y archivos históricos de coleccionistas.",
            sortOrder = order++
        )

        // 2006: Mundo Congelado (12 figuras confirmadas)
        val mundoCongelado2006 = listOf(
            "Momion",
            "Hombre Lobo",
            "Mortis",
            "Gargoli",
            "Sustin",
            "Zombie",
            "Jinete sin Cabeza",
            "Drácula",
            "Frankie",
            "Vampirín",
            "La Sombra",
            "Calaverín"
        )

        mundoCongelado2006.forEachIndexed { index, name ->
            val num = index + 1
            val accessoryNote = if (name == "La Sombra") {
                " Se han documentado accesorios característicos en la serie como guadaña o candelabro."
            } else {
                ""
            }
            list += FigureEntity(
                id = String.format("DRAC-2006-%03d", num),
                name = name,
                year = 2006,
                yearLabel = "2006",
                era = EraPeriod.ERA_2000_2009.code,
                collectionName = "Mundo Congelado",
                subGroup = "Pieza #$num de 12",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura termocromática #$num de la colección Mundo Congelado de 2006.",
                history = "Lanzada en 2006, Mundo Congelado reunió 12 figuras que venían dentro de ataúdes de plástico de aproximadamente 4 cm con mirilla en la tapa —por donde se asomaba el rostro del monstruo— y el logotipo de la Paleta Drácula.\n\nCada entrega incluía además calcomanías o tatuajes con su arte conceptual.$accessoryNote La ficha narrativa individual de '$name' sigue pendiente de transcripción.",
                curiosities = "• Las figuras incorporaban material termocromático que cambiaba de color al exponerse al frío del congelador.\n• Los ataúdes de 4 cm contaban con una pequeña ventana en la tapa.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista de 12 figuras y especificaciones verificadas en blogs de coleccionismo (Blogspot) y registros audiovisuales.",
                sortOrder = order++
            )
        }

        // 2007–2009: Pausa de colecciones masivas de figuras
        list += FigureEntity(
            id = "DRAC-2007-EXP",
            name = "Pausa y Dinámicas Interactivas (2007–2009)",
            year = 2007,
            yearLabel = "2007–2009",
            era = EraPeriod.ERA_2000_2009.code,
            collectionName = "Archivo 2007–2009 (En investigación)",
            subGroup = "Expediente abierto",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Años de transición con menor presencia de series numeradas y enfoque en empaques, códigos web y colmillos.",
            history = "Los archivos de coleccionistas registran que entre 2007 y 2011 la marca atravesó un periodo intermitente respecto a grandes líneas continuas de figuras de monstruos, orientando varias campañas hacia códigos promocionales web, concursos, tarjetas, stickers y los clásicos colmillos de plástico.\n\nNota de contraste entre fuentes: algunas reseñas generales mencionan lanzamientos puntuales de figuras neón o translúcidas hacia 2008–2009, mientras que crónicas especializadas señalan una pausa formal hasta 2012. Se deja constancia de esta discrepancia en tanto no se documenten catálogos físicos verificables.",
            curiosities = "• Se documenta explícitamente la divergencia entre crónicas de coleccionistas sobre si hubo series propias de miniaturas entre 2007 y 2009.",
            verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
            sourceNote = "Contraste entre archivos de coleccionismo y reseñas de prensa (Revista Diners, Blogspot).",
            sortOrder = order++
        )

        return list
    }
}
