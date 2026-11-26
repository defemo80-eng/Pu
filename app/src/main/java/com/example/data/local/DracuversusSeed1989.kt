package com.example.data.local

import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus

internal object DracuversusSeed1989 {

    fun entries(): List<FigureEntity> {
        val list = mutableListOf<FigureEntity>()
        var order = 1

        // 1989–1993: Orígenes y Colmillos Neón
        list += FigureEntity(
            id = "DRAC-1989-001",
            name = "Colmillos de Plástico Neón (Edición Fundacional)",
            year = 1989,
            yearLabel = "1989–1993",
            era = EraPeriod.ERA_1989_1999.code,
            collectionName = "Orígenes de la Paleta Drácula (Colmillos)",
            subGroup = "Accesorio fundacional",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Primer coleccionable oficial incluido con la Paleta Drácula desde su lanzamiento en octubre de 1989.",
            history = "En octubre de 1989, Crem Helado (Meals de Colombia) lanzó al mercado la Paleta Drácula como producto estacional exclusivo para la temporada de Halloween, tras un estudio de mercado que identificó al Conde Drácula como el personaje más asociado con el terror en Colombia.\n\nAntes de la llegada de las figuras de monstruos en 1994, los primeros obsequios icónicos fueron los colmillos de plástico en colores neón y acabados fosforescentes que brillaban en la oscuridad. En 1991 se experimentó brevemente con salsa de mora en lugar de fresa, regresando posteriormente a la receta clásica de vainilla, salsa de fresa y cobertura de chocolate.",
            curiosities = "• Fue el juguete inaugural que consolidó la tradición escolar de coleccionar sorpresas de Drácula cada octubre.\n• Existieron variantes en colores neón y versiones fosforescentes; el catálogo exacto de tonos por año entre 1989 y 1993 continúa en investigación archivística.",
            verificationStatus = VerificationStatus.CONFIRMED.code,
            sourceNote = "Fuentes confirmadas: El Tiempo, Infobae, Revista Diners, Clarín y Señal Colombia.",
            sortOrder = order++
        )

        // 1994–1995: Monstruos en mi Bolsillo (Monster in My Pocket - 48 figuras)
        val mimp1994 = listOf(
            "Hombre Lobo",
            "Gran Bestia",
            "Tiranosaurio",
            "Tritón",
            "Tengu",
            "Cíclope",
            "Frankenstein (El Monstruo)",
            "Manticore",
            "Goblin",
            "Windigo",
            "Baba-Yaga",
            "Pie Grande",
            "Científico Loco",
            "El Fantasma",
            "Ghoul",
            "Ogro",
            "Espíritu",
            "Vampiresa",
            "La Medusa",
            "Drácula",
            "La Bruja",
            "La Bestia",
            "Esqueleto",
            "Momia",
            "Hydra",
            "Behemoth",
            "Griffin",
            "Cockatrice",
            "Kraken",
            "Jotun Troll",
            "Zombie",
            "Coatlicue",
            "Kali",
            "Catoblepas",
            "Harpy",
            "Haniver",
            "Gorra Roja",
            "Hobglobin",
            "Cerberus",
            "Karnak",
            "Chimera",
            "Roc",
            "Gremlin",
            "La Pantera Alada",
            "Charon",
            "Jack \"El Saltarín\"",
            "Hombre Invisible",
            "El Jorobado"
        )

        mimp1994.forEachIndexed { index, name ->
            val num = index + 1
            val idStr = String.format("DRAC-1994-%03d", num)
            val draculaNote = if (name == "Drácula") {
                "\n\nEn el sistema de juego de Monster in My Pocket, la figura de Drácula destacaba por portar la puntuación de poder 10 en el reverso."
            } else {
                ""
            }
            list += FigureEntity(
                id = idStr,
                name = name,
                year = 1994,
                yearLabel = "1994–1995",
                era = EraPeriod.ERA_1989_1999.code,
                collectionName = "Monstruos en mi Bolsillo (MIMP)",
                subGroup = "Pieza #$num de 48",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Miniatura #$num de la primera gran serie licenciada de monstruos coleccionables de la Paleta Drácula.",
                history = "Entre 1994 y 1995, Crem Helado introdujo en la Paleta Drácula la licencia internacional Monster in My Pocket (producida originalmente por Matchbox / Morrison Entertainment Group), adaptada para el mercado colombiano bajo el nombre Monstruos en mi Bolsillo.$draculaNote\n\nLa serie reunió 48 criaturas mitológicas, literarias y del cine clásico de terror moldeadas en plástico/goma de colores sólidos y tandas fosforescentes. El detalle biográfico individual de la tarjeta colombiana de esta pieza específica sigue pendiente de transcripción literal.",
                curiosities = "• Cada figura llevaba un puntaje de fuerza grabado en la espalda para realizar batallas entre coleccionistas.\n• Se distribuyeron variantes en múltiples colores (morado, rojo, verde, azul y tonos fosforescentes).\n• Algunas fuentes registran el reparto entre Serie 1 (1994) y continuación en 1995.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Nombre y pertenencia confirmados en archivos de coleccionistas colombianos y GamerFocus; ficha literal individual y foto oficial pendientes.",
                sortOrder = order++
            )
        }

        // 1996: Monstruos Mutantes (12 figuras confirmadas)
        val mutantes1996 = listOf(
            "La Criatura del Pantano",
            "Fachan",
            "Gárgola",
            "Troll",
            "El Espectro",
            "Gorgona",
            "Drácula",
            "Omogra",
            "Calaverus",
            "Arackne",
            "Aquanus",
            "Hernes"
        )

        mutantes1996.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-1996-%03d", num),
                name = name,
                year = 1996,
                yearLabel = "1996",
                era = EraPeriod.ERA_1989_1999.code,
                collectionName = "Monstruos Mutantes",
                subGroup = "Pieza #$num de 12",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura #$num de la colección Monstruos Mutantes de 1996, recordada por sus criaturas aqua-reactivas.",
                history = "Lanzada en octubre de 1996 por Crem Helado, la colección Monstruos Mutantes dio continuidad al éxito de las miniaturas de los años noventa con diseños de criaturas fantásticas y mutaciones del universo oscuro de Drácula.\n\nEl texto biográfico individual de la tarjeta de '$name' aún se encuentra pendiente de transcripción documental completa.",
                curiosities = "• Varias figuras de esta colección contaban con propiedades aqua-reactivas que cambiaban o reaccionaban al contacto con el agua.\n• Su legado fue homenajeado treinta años después en la edición 2026 (Secretos del Pasillo Mutante).",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Listado de nombres verificado en registros audiovisuales de coleccionistas y reseñas de prensa (GamerFocus).",
                sortOrder = order++
            )
        }

        // 1997: Escalofríos / Goosebumps (24 figuras confirmadas)
        val escalofrios1997 = listOf(
            "Nicodemus",
            "Fangoso",
            "Morged",
            "Gusajaro",
            "Cabrilio",
            "Lodis Presley",
            "Samanthus Insectus",
            "Murray",
            "Kolpek",
            "Avichucho",
            "Vampironus",
            "Tía Jane",
            "Esqueleton",
            "Drácula",
            "Vendamomo",
            "Roelio",
            "Pastobulo",
            "Luzbel",
            "Zachi, La Fiera",
            "Pelenor",
            "Ramsés",
            "Ranadam",
            "Neptuno",
            "Raquelitica"
        )

        escalofrios1997.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-1997-%03d", num),
                name = name,
                year = 1997,
                yearLabel = "1997",
                era = EraPeriod.ERA_1989_1999.code,
                collectionName = "Escalofríos",
                subGroup = "Pieza #$num de 24",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Miniatura fosforescente #$num de la icónica colección Escalofríos de 1997.",
                history = "En 1997, la Paleta Drácula presentó la colección Escalofríos, compuesta por 24 minifiguras de plástico y goma fosforescente que brillaban en la oscuridad, inspiradas en el auge de la literatura juvenil de terror de la época (Goosebumps de R. L. Stine).\n\nCada figura incluía una tarjeta coleccionable y venía dentro de un pequeño ataúd plástico característico. El texto exacto impreso en la tarjeta individual de '$name' permanece pendiente de verificación archivística.",
                curiosities = "• Venían acompañadas de su propio mini ataúd de plástico y tarjeta biográfica.\n• Material fosforescente diseñado para brillar en la oscuridad.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista completa de 24 nombres verificada en archivos de coleccionistas y catálogos en línea (Todocolección).",
                sortOrder = order++
            )
        }

        // 1998–1999: Expediente pendiente de investigación
        list += FigureEntity(
            id = "DRAC-1998-EXP",
            name = "Colecciones de cierre de década (Pendiente de desglose)",
            year = 1998,
            yearLabel = "1998–1999",
            era = EraPeriod.ERA_1989_1999.code,
            collectionName = "Archivo 1998–1999 (En investigación)",
            subGroup = "Expediente abierto",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Registro histórico abierto para documentar los nombres oficiales de las entregas entre 1998 y 1999.",
            history = "Los archivos generales confirman que entre 1998 y 1999 Crem Helado mantuvo la entrega anual de miniaturas coleccionables de terror, criaturas fantásticas y accesorios en plástico brillante o con ataúdes.\n\nSin embargo, los listados oficiales completos de nombres individuales por figura de estas dos temporadas específicas no han podido confirmarse con fuentes primarias independientes en esta compilación del museo, por lo que permanecen abiertos para investigación sin incorporar datos no verificados.",
            curiosities = "• Se mantiene este expediente para evitar duplicar figuras de Monster in My Pocket (1994–1995) o inventar nombres no confirmados.",
            verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
            sourceNote = "Estado: pendiente de verificación de catálogos físicos o publicidad impresa de 1998–1999.",
            sortOrder = order++
        )

        return list
    }
}
