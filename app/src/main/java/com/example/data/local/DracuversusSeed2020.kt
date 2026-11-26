package com.example.data.local

import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus

internal object DracuversusSeed2020 {

    fun entries(startOrder: Int): List<FigureEntity> {
        val list = mutableListOf<FigureEntity>()
        var order = startOrder

        // 2020: Monstruos del Mundo (24 figuras)
        val mundo2020 = listOf(
            "Frankenstein",
            "Pie Grande",
            "Akhlut",
            "Monstruo del Lago Ness",
            "Drácula",
            "Pirata Fantasma",
            "Baba Yaga",
            "Yeti",
            "El Wendigo",
            "Catrina",
            "El Mohán",
            "Vampiresa",
            "Momia",
            "Hombre Lobo",
            "El Gigante",
            "Jiang Shi",
            "Sirena Encantada",
            "Leviatán",
            "La Llorona",
            "Mapinguari",
            "Popobawa",
            "Dragón",
            "Yara-Ma-Yha-Who",
            "Hombre Invisible"
        )

        mundo2020.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2020-%03d", num),
                name = name,
                year = 2020,
                yearLabel = "2020",
                era = EraPeriod.ERA_2020_2026.code,
                collectionName = "Monstruos del Mundo",
                subGroup = "Pieza #$num de 24",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura #$num de la colección Monstruos del Mundo (2020), dedicada al folclor y leyendas de distintos continentes.",
                history = "En 2020, la Paleta Drácula presentó Monstruos del Mundo, una colección de 24 figuras dedicadas a criaturas mitológicas y leyendas populares de distintas regiones del planeta, integrando figuras latinoamericanas como El Mohán, La Llorona, La Catrina y el Mapinguarí junto a mitos globales como Akhlut, Jiang Shi, Popobawa y Yara-Ma-Yha-Who.\n\nLas tarjetas descriptivas de la colección formaban en conjunto un mapamundi indicando el origen geográfico de cada ser.",
                curiosities = "• Las 24 tarjetas incluían detalles geográficos y se ensamblaban visualmente alrededor de un mapa mundial.\n• Se distribuyeron en múltiples variantes de color.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista completa de 24 nombres verificada en archivos de coleccionistas y GamerFocus.",
                sortOrder = order++
            )
        }

        // 2021: Refugios Tenebrosos (20 figuras/refugios)
        val refugios2021 = listOf(
            "Castillo de Drácula",
            "Árbol Embrujado",
            "Chimenea de los Murmullos",
            "Horno Tenebroso",
            "Libro Mágico",
            "Nevera Repugnante",
            "Lavamonster (Lavadora)",
            "Jarrón Ultratumba (Florero Ultratumba)",
            "Sótano del Miedo",
            "Cabina Encantada",
            "Armario del Terror",
            "Televisor Encantado",
            "Sofá Abrazador",
            "Puente de los Lamentos",
            "Cama de las Pesadillas",
            "Pozo sin Fondo",
            "Caneca Tóxica",
            "Casa de los Aullidos",
            "Escalera Misteriosa",
            "Reloj Escalofriante"
        )

        refugios2021.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2021-%03d", num),
                name = name,
                year = 2021,
                yearLabel = "2021",
                era = EraPeriod.ERA_2020_2026.code,
                collectionName = "Refugios Tenebrosos",
                subGroup = "Refugio #$num de 20",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Pieza #$num de la colección Refugios Tenebrosos (2021), ambientada en guaridas y objetos embrujados.",
                history = "Lanzada en 2021 —en el contexto posterior al confinamiento global—, Refugios Tenebrosos propuso 20 piezas centradas en recintos oscuros, muebles encantados y guaridas donde habitaban las criaturas del universo Drácula.\n\nCada figura se acompañaba de una placa o tarjeta holográfica con la descripción de su guarida.",
                curiosities = "• Las tarjetas incluían acabado holográfico.\n• Se registra la variante de nombre 'Jarrón Ultratumba' / 'Florero Ultratumba' entre catálogos de coleccionistas.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista oficial de 20 nombres verificada en informes de Grupo Nutresa, GamerFocus y archivos de coleccionistas.",
                sortOrder = order++
            )
        }

        // 2022: Monstruos del Futuro (20 figuras)
        val futuro2022 = listOf(
            "MecaFrank (Meca-Frank 3000)",
            "Ruidolf",
            "Velozilda",
            "Momiabot",
            "Digiduende",
            "Zombie",
            "Hidracall",
            "Tubedusa",
            "Nanogremlin (Nanogremlim)",
            "Espantachef",
            "Piratadroid",
            "NFT-Gargol",
            "Invisible",
            "Cíclope",
            "Esqueletron",
            "La Vampiresa",
            "Dragonthor",
            "Fantasmario",
            "El Ogro",
            "Drácula (Drácula del Futuro)"
        )

        futuro2022.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2022-%03d", num),
                name = name,
                year = 2022,
                yearLabel = "2022",
                era = EraPeriod.ERA_2020_2026.code,
                collectionName = "Monstruos del Futuro",
                subGroup = "Pieza #$num de 20",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Criatura cyber-terrorífica #$num de la colección Monstruos del Futuro (2022).",
                history = "En 2022, Crem Helado llevó al universo Drácula hacia la ciencia ficción oscura y la tecnología con Monstruos del Futuro: 20 figuras con armaduras espaciales, circuitos cibernéticos y mutaciones digitales.\n\nCada pieza venía con una tarjeta provista de código QR que habilitaba experiencias de realidad aumentada y minijuegos digitales.",
                curiosities = "• Las figuras se produjeron en 8 variantes de color, incluyendo tonos neón translúcidos y versiones fosforescentes que brillan en la oscuridad.\n• Existen ligeras variaciones ortográficas entre fuentes (p. ej., MecaFrank vs. Meca-Frank 3000; Nanogremlin vs. Nanogremlim).",
                verificationStatus = VerificationStatus.CONFIRMED.code,
                sourceNote = "Listado de 20 figuras verificado en GamerFocus y registros de coleccionistas colombianos.",
                sortOrder = order++
            )
        }

        // 2023: Monstruos Marinos (21 figuras)
        val marinos2023 = listOf(
            "Cuthul (El Dios Oscuro)",
            "Pobios (La Magia del Mar)",
            "Mongar (La Serpiente del Mundo)",
            "Pictal (El Horror Azteca)",
            "Ompachi (El Presagio de las Tormentas)",
            "Kernak (El Terror de los Mares)",
            "Quelon (La Isla Errante)",
            "Damong (La Mordida Letal)",
            "Biscardi (La Creadora de Remolinos)",
            "Rey Egeon (Las Pinzas del Mar)",
            "Norit (El Guerrero de los Mares)",
            "Bruma (La Hechicera Marina)",
            "Trollgua (El Gigante del Mar)",
            "Zeta (La Madre de los Monstruos)",
            "Felkor (La Devoradora)",
            "Nersia (El Canto Mortífero)",
            "Piyub (El Horror del Lago)",
            "Icaly (La Belleza Mortal)",
            "Valtani (El Más Antiguo del Mar)",
            "Explorador (El Vampiro del Mar)",
            "Submarino (El Vampiro del Mar)"
        )

        marinos2023.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2023-%03d", num),
                name = name,
                year = 2023,
                yearLabel = "2023",
                era = EraPeriod.ERA_2020_2026.code,
                collectionName = "Monstruos Marinos (Sumérgete en el Origen)",
                subGroup = if (num >= 20) "Edición Drácula (#$num de 21)" else "Criatura marina (#$num de 21)",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura termocromática #$num de la colección Monstruos Marinos (2023).",
                history = "Bajo el lema 'Sumérgete en el Origen', la edición 2023 presentó 21 figuras: 19 monstruos inspirados en abismos oceánicos, deidades ancestrales y mitología marina, más 2 versiones temáticas especiales de Drácula (Explorador y Submarino).\n\nLa historia individual completa de la tarjeta de '$name' continúa pendiente de transcripción íntegra.",
                curiosities = "• Todas las figuras contaban con efecto termocromático que cambiaba de color al sumergirse en agua helada.\n• Incluye títulos oficiales descriptivos grabados en cada tarjeta coleccionable.",
                verificationStatus = VerificationStatus.CONFIRMED.code,
                sourceNote = "Lista oficial numerada del 1 al 21 verificada en GamerFocus, Semana y archivos de tarjetas.",
                sortOrder = order++
            )
        }

        // 2024: Viajando en el Dracuverso (20 figuras)
        val dracuverso2024 = listOf(
            "Drakkashi" to "Versión del universo samurái / Japón feudal.",
            "Dracuvismond" to "Variante abisal y alada del Dracuverso.",
            "Drácuferno" to "Variante infernal del Conde en el Dracuverso.",
            "Dracálien" to "Variante extraterrestre del Conde.",
            "Drácubestial" to "Variante salvaje y bestial del Dracuverso.",
            "Dracutómico" to "Variante atómica del Conde Drácula.",
            "Drácuzomb" to "Variante zombi multiversal.",
            "Dráctoide" to "Variante reptiliana o insectoide del Dracuverso.",
            "Drácuptil" to "Variante reptil / dinosaurio del Conde.",
            "Dracavernario" to "Variante prehistórica de la era de las cavernas.",
            "Drácuborg" to "Variante cíborg tecnológica del Dracuverso.",
            "Dracurlin" to "Variante del universo de Drácula documentada en la serie 2024.",
            "Dractoony" to "Variante de estética caricaturesca / animada.",
            "Miaúcula" to "Versión felina proveniente de Mascotalandia.",
            "Drácuagente" to "Variante de agente secreto del Dracuverso.",
            "Drakingo" to "Variante vikinga del Conde Drácula.",
            "Drácules" to "Variante mitológica inspirada en la antigüedad.",
            "Drapunk" to "Variante de estética punk del Dracuverso.",
            "Drácuzor" to "Protector que conforma una sociedad secreta dentro del lore del Dracuverso.",
            "Drácula" to "El Conde vampiro original y eje del multiverso."
        )

        dracuverso2024.forEachIndexed { index, (name, desc) ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2024-%03d", num),
                name = name,
                year = 2024,
                yearLabel = "2024",
                era = EraPeriod.ERA_2020_2026.code,
                collectionName = "Viajando en el Dracuverso",
                subGroup = "Variante #$num de 20",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "$desc Pieza #$num de 20 de la temporada 2024.",
                history = "En 2024, Viajando en el Dracuverso reunió 20 figuras centradas en versiones alternas del Conde Drácula a través de portales temporales y dimensiones paralelas.\n\nLa narrativa oficial introdujo además la expansión de la marca hacia otros formatos como galletas y colmillos especiales de temporada.",
                curiosities = "• Es la primera colección donde la totalidad de los 20 muñecos son encarnaciones multiversales del propio Drácula.\n• Se complementó con experiencias interactivas web del Dracuverso.",
                verificationStatus = VerificationStatus.CONFIRMED.code,
                sourceNote = "Lista completa de 20 figuras verificada en GamerFocus y registros de coleccionismo.",
                sortOrder = order++
            )
        }

        // 2025: Secretos del Bosque Prohibido (31 figuras en 5 reinos)
        val bosque2025 = listOf(
            "Reino Pantano" to listOf("Plathom", "Venarth", "Nebraskarn", "Noctalum", "Hexalga", "Narthuun"),
            "Reino Floral" to listOf("Zelvira", "Hiedriss", "Voltheria", "Cardantae", "Lotharax", "Quirisdea", "Nekrophos"),
            "Reino Bestial" to listOf("Cenethra", "Taralith", "Geltrion", "Moheraidos", "Ardanox"),
            "Reino Rocoso" to listOf("Mulkar", "Ignarok", "Optaroc", "Lutherion", "Monocrit", "Karnizul"),
            "Reino Vampírico" to listOf("Dracutánicus", "Nubrakai", "Infernelle", "Noctariz", "Cavurnox", "Drakyra", "Drácula (Guardián del castillo)")
        )

        var idx2025 = 1
        bosque2025.forEach { (kingdom, members) ->
            members.forEach { name ->
                val num = idx2025++
                val specificLore = when (name) {
                    "Zelvira" -> " En las fichas oficiales de la campaña aparece descrita como 'La ninfa enredadera'."
                    "Mulkar" -> " Descrito en archivos de la colección como 'El duende de lodo'."
                    "Ignarok" -> " Descrito como 'El diablillo de lava' (algunas reseñas lo vinculan visualmente al Reino Rocoso y otras al Reino Vampírico; se deja constancia de esa variación)."
                    "Hiedriss" -> " Descrita en archivos de la colección como 'La serpiente floral'."
                    else -> ""
                }
                list += FigureEntity(
                    id = String.format("DRAC-2025-%03d", num),
                    name = name,
                    year = 2025,
                    yearLabel = "2025",
                    era = EraPeriod.ERA_2020_2026.code,
                    collectionName = "Secretos del Bosque Prohibido",
                    subGroup = kingdom,
                    photoAssetPath = null,
                    photoStatus = "PENDIENTE_ARCHIVO",
                    shortDescription = "Figura #$num de 31 perteneciente al $kingdom en la temporada 2025.",
                    history = "Con 31 monstruos inéditos, Secretos del Bosque Prohibido (2025) fue una de las colecciones más extensas de la era moderna, articulada alrededor de la Profecía de la Luna Verde y dividida en cinco reinos: Pantano, Floral, Bestial, Rocoso y Vampírico.$specificLore\n\nEl texto narrativo completo de la tarjeta individual de '$name' continúa en proceso de transcripción en el museo.",
                    curiosities = "• Incluyó una experiencia digital interactiva conectada con el videojuego Fortnite.\n• Los 31 personajes se distribuyen en 5 reinos temáticos.",
                    verificationStatus = VerificationStatus.CONFIRMED.code,
                    sourceNote = "Listado completo por reinos verificado en GamerFocus y reportes de campaña 2025.",
                    sortOrder = order++
                )
            }
        }

        // 2026: Secretos del Pasillo Mutante (20 figuras confirmadas + nota de contraste)
        val pasillo2026 = listOf(
            "Clan Drácula" to listOf(
                "Marquesa Draelia",
                "Dama Drávelle",
                "Lady Drácora",
                "Príncipe Dráciel",
                "Duque Drako",
                "Duque Drasferacu"
            ),
            "Guardianes" to listOf(
                "Sir Kaelor de la Torre Sur",
                "Vorag Triple Ojo",
                "Gromak de la Niebla Negra",
                "Vigía Hexar",
                "Escudero Malvek",
                "Nyxar Alada"
            ),
            "Objetos encantados" to listOf(
                "Relorak",
                "Calyxor",
                "Candravel",
                "Orgleth",
                "Vitrala",
                "Baúl Mordrake"
            ),
            "Exclusivos" to listOf(
                "Llave del Pasillo",
                "Drácula (Arquitecto del Pasillo)"
            )
        )

        var idx2026 = 1
        pasillo2026.forEach { (category, members) ->
            members.forEach { name ->
                val num = idx2026++
                val detailNote = if (name == "Calyxor") {
                    " En el relato oficial se presenta como el cáliz viviente formado por fragmentos de la Luna Verde."
                } else {
                    ""
                }
                list += FigureEntity(
                    id = String.format("DRAC-2026-%03d", num),
                    name = name,
                    year = 2026,
                    yearLabel = "2026",
                    era = EraPeriod.ERA_2020_2026.code,
                    collectionName = "Secretos del Pasillo Mutante",
                    subGroup = category,
                    photoAssetPath = null,
                    photoStatus = "PENDIENTE_ARCHIVO",
                    shortDescription = "Figura #$num de 20 de la categoría $category en Secretos del Pasillo Mutante (2026).",
                    history = "Lanzada en 2026 en homenaje indirecto a los 30 años de Monstruos Mutantes (1996), la colección Secretos del Pasillo Mutante reúne 20 figuras organizadas en cuatro categorías: Clan Drácula, Guardianes, Objetos encantados y Exclusivos.$detailNote\n\nLa trama sigue la energía de la Luna Verde y los pasadizos ocultos del castillo.",
                    curiosities = "• Generó amplia conversación entre coleccionistas por el uso de conceptos visuales asistidos por inteligencia artificial en sus tarjetas.\n• Nota de contraste entre fuentes: algunos listados preliminares mencionan un elemento adicional llamado 'Dracuverso' o variaciones ortográficas como 'Baúl Mordarke' o 'Escudero Vigía'; la lista oficial principal suma 20 figuras.",
                    verificationStatus = VerificationStatus.CONFIRMED.code,
                    sourceNote = "Listado principal por categorías verificado en GamerFocus y registros de campaña 2026.",
                    sortOrder = order++
                )
            }
        }

        return list
    }
}
