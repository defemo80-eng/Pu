package com.example.data.local

import com.example.data.model.EraPeriod
import com.example.data.model.FigureEntity
import com.example.data.model.VerificationStatus

internal object DracuversusSeed2010 {

    fun entries(startOrder: Int): List<FigureEntity> {
        val list = mutableListOf<FigureEntity>()
        var order = startOrder

        // 2010–2011: Expediente abierto / Crápula
        list += FigureEntity(
            id = "DRAC-2010-EXP",
            name = "Crápula y Transición 2010–2011 (En investigación)",
            year = 2010,
            yearLabel = "2010–2011",
            era = EraPeriod.ERA_2010_2019.code,
            collectionName = "Archivo 2010–2011 (En investigación)",
            subGroup = "Expediente abierto",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Registro del periodo previo a los Draculuminosos y menciones al personaje Crápula.",
            history = "Entre 2010 y 2011 se mantuvo la transición previa al gran regreso de las figuras en 2012. En narrativas promocionales y expansiones del universo del castillo se registra la presencia de personajes recurrentes como Crápula (descrito como el torpe hermano menor de Drácula), aunque las fuentes consultadas difieren sobre qué piezas físicas exactas correspondieron a 2010 frente a campañas digitales y accesorios.\n\nEste registro conserva el expediente abierto sin asignar nombres no verificados.",
            curiosities = "• El personaje de Crápula reaparece en relatos y campañas posteriores de la marca.\n• Permanece pendiente confirmar el inventario físico completo de 2010–2011.",
            verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
            sourceNote = "Fuentes: archivos audiovisuales y crónicas históricas de Paleta Drácula.",
            sortOrder = order++
        )

        // 2012–2013: Draculuminosos (10 figuras fabricadas por Quinteplast S.A.)
        val draculuminososConfirmados = listOf(
            "Drácula (El Conde)" to "Figura central del Conde Drácula con carcasa exterior de dos piezas y esqueleto interno fosforescente.",
            "Franky" to "Versión estilizada de Frankenstein con cabeza cuadrada dentro de la serie Draculuminosos.",
            "Barbas" to "Felino o gato monstruoso documentado dentro de la línea Draculuminosos.",
            "Soila Zombie" to "Niña zombie documentada en los registros de coleccionistas de Draculuminosos."
        )

        draculuminososConfirmados.forEachIndexed { index, (name, desc) ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2012-%03d", num),
                name = name,
                year = 2012,
                yearLabel = "2012–2013",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Draculuminosos",
                subGroup = "Pieza confirmada #$num de 10",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = desc,
                history = "Lanzada en septiembre de 2012 (con circulación extendida hacia 2013) y fabricada en Colombia por Quinteplast S.A., la colección Draculuminosos marcó el regreso de las figuras tras años de pausa.\n\nCada una de las 10 figuras combinaba una estética cartoon en pose en cruz o en 'T' (diseñada por normativas infantiles anti-asfixia) y una carcasa desmontable de dos piezas con orificios en ojos, nariz y boca que alojaba un esqueleto translúcido fosforescente.",
                curiosities = "• Incluía 6 diseños de colmillos y un papel con arte conceptual más código para un juego virtual web.\n• En 2016 y 2020 los moldes de Quinteplast fueron reutilizados en promociones lácteas de Colanta (Yogur Kid y Frescolanta).",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Fabricante, mecánica y nombre confirmados en blogs de coleccionismo (Blogspot) y registros de época.",
                sortOrder = order++
            )
        }

        for (slot in 5..10) {
            list += FigureEntity(
                id = String.format("DRAC-2012-%03d", slot),
                name = "Molde Draculuminoso #$slot (Nombre oficial pendiente)",
                year = 2012,
                yearLabel = "2012–2013",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Draculuminosos",
                subGroup = "Espacio documentado #$slot de 10",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Pieza #$slot de la serie oficial de 10 figuras de Quinteplast S.A., cuyo nombre impreso oficial está pendiente de verificación.",
                history = "Se encuentra confirmado que la colección Draculuminosos (2012–2013) constó de exactamente 10 figuras con esqueleto fosforescente interno y carcasa de dos piezas.\n\nAunque distintas reseñas mencionan arquetipos como hombre lobo, momia, bruja o fantasma, los nombres comerciales exactos impresos en las fichas de los seis moldes restantes aún no cuentan con verificación documental directa.",
                curiosities = "• Se preserva este espacio numerado para respetar la cantidad real de 10 moldes sin inventar denominaciones.",
                verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
                sourceNote = "Cantidad total de 10 piezas confirmada en archivos de coleccionistas; nombre específico pendiente.",
                sortOrder = order++
            )
        }

        // 2014: Dracuzombies (8 motivos)
        val dracuzombies = listOf(
            Triple("DracuZombie", VerificationStatus.CONFIRMED.code, "Motivo principal de la colección Dracuzombies de septiembre de 2014."),
            Triple("Nerdín", VerificationStatus.CONFIRMED.code, "Personaje confirmado en los archivos de coleccionistas de Dracuzombies (2014)."),
            Triple("Payaso Loco", VerificationStatus.CONFIRMED.code, "Personaje confirmado de la serie Dracuzombies de septiembre de 2014."),
            Triple("Ojos Saltones", VerificationStatus.CONFIRMED.code, "Personaje confirmado de la serie Dracuzombies (2014)."),
            Triple("ChicaZombie", VerificationStatus.CONFIRMED.code, "Personaje confirmado de la serie Dracuzombies (2014)."),
            Triple("Niño Zombie (nombre no oficial)", VerificationStatus.PARTIAL.code, "Motivo documentado por coleccionistas bajo denominación descriptiva no oficial.")
        )

        dracuzombies.forEachIndexed { index, (name, status, desc) ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2014-%03d", num),
                name = name,
                year = 2014,
                yearLabel = "2014",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Dracuzombies",
                subGroup = "Motivo #$num de 8",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = desc,
                history = "Lanzada en septiembre de 2014, la colección Dracuzombies estuvo compuesta por 8 motivos de plástico duro monocromático con estética caricaturizada, detalles impresos en ojos y dientes, y un característico aerografiado rojo.\n\nConservaba la postura de seguridad infantil con brazos extendidos en cruz y salió en tres tonalidades principales: verde claro, morado metalizado y azul grisáceo.",
                curiosities = "• Colores base documentados: verde claro, morado metalizado y azul grisáceo con aerografiado rojo.\n• La ficha narrativa individual de cada motivo permanece pendiente de archivo.",
                verificationStatus = status,
                sourceNote = "Documentación fotográfica y listado parcial en blogs especializados de coleccionismo colombiano (Blogspot).",
                sortOrder = order++
            )
        }

        for (slot in 7..8) {
            list += FigureEntity(
                id = String.format("DRAC-2014-%03d", slot),
                name = "Motivo Dracuzombie #$slot (Nombre pendiente)",
                year = 2014,
                yearLabel = "2014",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Dracuzombies",
                subGroup = "Motivo #$slot de 8",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Espacio correspondiente al motivo #$slot de los 8 diseños lanzados en Dracuzombies (2014).",
                history = "Los archivos confirman que Dracuzombies contó con 8 motivos diferentes en septiembre de 2014. Dos de las denominaciones exactas permanecen pendientes de confirmación documental.",
                curiosities = "• Formato de plástico duro monocromático en cruz con detalles aerografiados en rojo.",
                verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
                sourceNote = "Archivo de coleccionismo colombiano (Blogspot): pendiente identificar 2 de los 8 motivos.",
                sortOrder = order++
            )
        }

        // 2016: Batallas Descerebradas (10 dedales confirmados)
        val batallas2016 = listOf(
            "Drácula" to "Cabeza dedal principal del Conde Drácula.",
            "Frankdefesio" to "Cabeza dedal inspirada en el monstruo de Frankenstein.",
            "Loboliento" to "Cabeza dedal inspirada en el Hombre Lobo.",
            "Putrefacto" to "Cabeza dedal inspirada en un muerto viviente / momia.",
            "Casposín" to "Personaje dedal de la colección Batallas Descerebradas.",
            "Acuaflema" to "Cabeza dedal inspirada en una criatura del pantano y el agua.",
            "Peodora" to "Personaje dedal de la serie Batallas Descerebradas.",
            "El Gran Mocón" to "Personaje dedal de la serie Batallas Descerebradas.",
            "Tutanfeon" to "Cabeza dedal inspirada en las momias egipcias.",
            "Berrujita" to "Cabeza dedal inspirada en una bruja clásica."
        )

        batallas2016.forEachIndexed { index, (name, desc) ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2016-%03d", num),
                name = name,
                year = 2016,
                yearLabel = "2016",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Batallas Descerebradas",
                subGroup = "Dedal #$num de 10",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "$desc Parte de los 10 dedales de goma de 2016.",
                history = "En 2016, Crem Helado propuso una dinámica distinta a las figuras de cuerpo entero: Batallas Descerebradas, una colección de 10 cabezas monstruosas diseñadas como dedales para encajar en los dedos.\n\nCada cabeza albergaba en su interior un pequeño cerebro de goma roja que, al presionarlo durante duelos de pulgares, se expandía y sobresalía por los orificios del molde.",
                curiosities = "• Mecánica interactiva de duelo de pulgares con cerebro rojo deformable en su interior.\n• Lista completa de 10 personajes confirmada.",
                verificationStatus = VerificationStatus.CONFIRMED.code,
                sourceNote = "Lista completa y mecánica documentadas en archivos de coleccionistas y catálogos en línea.",
                sortOrder = order++
            )
        }

        // 2015, 2017, 2018: Años sin línea formal masiva de figuras de monstruos
        list += FigureEntity(
            id = "DRAC-2015-EXP",
            name = "Temporadas sin línea masiva de figuras (2015, 2017–2018)",
            year = 2015,
            yearLabel = "2015 / 2017–2018",
            era = EraPeriod.ERA_2010_2019.code,
            collectionName = "Archivo 2015–2018 (Sin serie estándar / En contraste)",
            subGroup = "Expediente de archivo",
            photoAssetPath = null,
            photoStatus = "PENDIENTE_ARCHIVO",
            shortDescription = "Años de transición con colmillos, stickers o empaques temáticos antes del relanzamiento de 2019.",
            history = "Durante 2015, 2017 y 2018 las campañas de la Paleta Drácula apostaron de manera intermitente por colmillos tradicionales, calcomanías, máscaras y empaques especiales, sin consolidar una colección masiva numerada de figuras de monstruos como la que regresaría en 2019.\n\nNota de contraste: algunas reseñas generales mencionan lanzamientos puntuales o clanes clásicos hacia 2017–2018, pero al no contar con un catálogo confirmado de piezas individuales, el museo deja constancia transparente de esta etapa.",
            curiosities = "• Antecedió directamente a la celebración de los 30 años en octubre de 2019.",
            verificationStatus = VerificationStatus.PENDING_ARCHIVE.code,
            sourceNote = "Fuentes: Scribd, Blogspot de coleccionistas y GamerFocus.",
            sortOrder = order++
        )

        // 2019: Monstruos Terroríficos (30 Aniversario - 12 figuras confirmadas)
        val terrorificos2019 = listOf(
            "Drácula",
            "Vampiresa",
            "Zombie",
            "Esqueleto",
            "Hombre Lobo",
            "La Momia",
            "Jekyll y Mr. Hyde",
            "Franky Monstruo",
            "Jack sin cabeza",
            "Ogro",
            "Monstruo del pantano",
            "Fantasma"
        )

        terrorificos2019.forEachIndexed { index, name ->
            val num = index + 1
            list += FigureEntity(
                id = String.format("DRAC-2019-%03d", num),
                name = name,
                year = 2019,
                yearLabel = "2019",
                era = EraPeriod.ERA_2010_2019.code,
                collectionName = "Monstruos Terroríficos (30 Aniversario)",
                subGroup = "Pieza #$num de 12",
                photoAssetPath = null,
                photoStatus = "PENDIENTE_ARCHIVO",
                shortDescription = "Figura #$num de la colección conmemorativa de los 30 años de la Paleta Drácula (2019).",
                history = "En octubre de 2019, para celebrar el 30 aniversario de la Paleta Drácula (1989–2019), Crem Helado impulsó el gran regreso de las miniaturas coleccionables de monstruos en alianza con fabricantes locales.\n\nLa serie Monstruos Terroríficos estuvo integrada por 12 arquetipos clásicos del terror acompañados de tarjetas biográficas y accesorios como pequeños ataúdes o sarcófagos, despertando una masiva ola nostálgica entre coleccionistas adultos y nuevas generaciones.",
                curiosities = "• Salió en múltiples colores base, versiones translúcidas y acabados luminosos especiales.\n• Marcó el inicio de la era anual ininterrumpida de miniaturas desde 2019 hasta 2026.",
                verificationStatus = VerificationStatus.PARTIAL.code,
                sourceNote = "Lista de 12 personajes confirmada en Revista Diners, GamerFocus y registros de coleccionistas.",
                sortOrder = order++
            )
        }

        return list
    }
}
