package org.michaelbel.mobiledevemoji.data

const val APP_NAME = "MobileDevEmoji"
const val TELEGRAM_URL = "https://t.me/foundout"
const val TELEGRAM_PACK_1 = "https://t.me/addstickers/MobileDevEmojiTgs"
const val TELEGRAM_PACK_2 = "https://t.me/addstickers/MobileDevEmojiTgs2"
const val TELEGRAM_PACK_3 = "https://t.me/addstickers/MobileDevEmojiTgs3"
const val TELEGRAM_PACK_4 = "https://t.me/addstickers/MobileDevEmojiTgs4"
const val TELEGRAM_PACK_5 = "https://t.me/addstickers/MobileDevEmojiTgs5"
const val TELEGRAM_PACK_6 = "https://t.me/addstickers/MobileDevEmojiTgs6"
const val GITHUB_URL = "https://github.com/michaelbel/mobiledevemoji"
private const val PACK_1_SIZE = 200
private const val PACK_2_SIZE = 200
private const val PACK_3_SIZE = 200
private const val PACK_4_SIZE = 200
private const val PACK_5_SIZE = 200
private const val PACK_6_SIZE = 60
const val PACKS_SIZE = PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE + PACK_5_SIZE + PACK_6_SIZE

val <T> List<T>.pack1: List<T>
    get() = take(PACK_1_SIZE)

val <T> List<T>.pack2: List<T>
    get() = drop(PACK_1_SIZE).take(PACK_2_SIZE)

val <T> List<T>.pack3: List<T>
    get() = drop(PACK_1_SIZE + PACK_2_SIZE).take(PACK_3_SIZE)

val <T> List<T>.pack4: List<T>
    get() = drop(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE).take(PACK_4_SIZE)

val <T> List<T>.pack5: List<T>
    get() = drop(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE).take(PACK_5_SIZE)

val <T> List<T>.pack6: List<T>
    get() = drop(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE + PACK_5_SIZE).take(PACK_6_SIZE)

fun List<Emoji>.filterBy(filter: String): List<Emoji> {
    if (filter.isEmpty() || filter.isBlank()) return this
    val textLowercase = filter.lowercase()
    return filter { emoji -> emoji.emojiResponse.filters.orEmpty().contains(textLowercase) }
}

fun List<Emoji>.searchBy(query: String): List<Emoji> {
    if (query.isEmpty() || query.isBlank()) return this
    return filter { emoji ->
        val emojiResponse = emoji.emojiResponse
        emojiResponse.name.contains(query, ignoreCase = true) || emojiResponse.id.contains(query, ignoreCase = true) || emojiResponse.filters?.any { filter -> filter.contains(query, ignoreCase = true) } == true
    }
}

val Int.pack: String
    get() = when (this) {
        in 0..<PACK_1_SIZE -> "pack1"
        in PACK_1_SIZE..(PACK_1_SIZE + PACK_2_SIZE).minus(1) -> "pack2"
        in PACK_1_SIZE + PACK_2_SIZE..(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE).minus(1) -> "pack3"
        in PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE..(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE).minus(1) -> "pack4"
        in PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE..(PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE + PACK_4_SIZE + PACK_5_SIZE).minus(1) -> "pack5"
        else -> "pack6"
    }