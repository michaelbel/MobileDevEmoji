package org.michaelbel.mobiledevemoji.data

const val APP_NAME = "MobileDevEmoji"

const val TELEGRAM_URL = "https://t.me/foundout"
const val TELEGRAM_PACK_1 = "https://t.me/addstickers/MobileDevEmojiTgs"
const val TELEGRAM_PACK_2 = "https://t.me/addstickers/MobileDevEmojiTgs2"
const val TELEGRAM_PACK_3 = "https://t.me/addstickers/MobileDevEmojiTgs3"
const val FIGMA_URL = "https://www.figma.com/community/file/1385339470177359146"
private const val PACK_1_SIZE = 200
private const val PACK_2_SIZE = 200
private const val PACK_3_SIZE = 200
const val PACKS_SIZE = PACK_1_SIZE + PACK_2_SIZE + PACK_3_SIZE

val <T> List<T>.pack1: List<T>
    get() = take(PACK_1_SIZE)

val <T> List<T>.pack2: List<T>
    get() = subList(PACK_1_SIZE, PACK_1_SIZE.plus(PACK_2_SIZE))

val <T> List<T>.pack3: List<T>
    get() = takeLast(PACK_3_SIZE)

val Int.pack: String
    get() = when (this) {
        in 0..<200 -> "pack1"
        in 200..399 -> "pack2"
        else -> "pack3"
    }