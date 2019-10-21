package com.eeearl.githubstars.util

/**
 * 한글단어 초성추출 도우미 클래스
 */
object KoreanCharacterUtil {

    private const val CHOSEONG_COUNT = 19
    private const val JUNGSEONG_COUNT = 21
    private const val JONGSEONG_COUNT = 28

    private const val HANGUL_SYLLABLE_COUNT = CHOSEONG_COUNT * JUNGSEONG_COUNT * JONGSEONG_COUNT
    private const val HANGUL_SYLLABLES_BASE = 0xAC00
    private const val HANGUL_SYLLABLES_END = HANGUL_SYLLABLES_BASE + HANGUL_SYLLABLE_COUNT

    private val COMPAT_CHOSEONG_COLLECTION = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
        'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    @JvmStatic
    fun getCompatChoseong(syllable: Char): Char {
        require(isSyllable(syllable)) { syllable.toString() }

        return COMPAT_CHOSEONG_COLLECTION[getChoseongIndex(syllable)]
    }

    private fun getChoseongIndex(syllable: Char): Int {
        val sylIndex = syllable - HANGUL_SYLLABLES_BASE
        return sylIndex.toInt() / (JUNGSEONG_COUNT * JONGSEONG_COUNT)
    }

    private fun isSyllable(c: Char): Boolean {
        return HANGUL_SYLLABLES_BASE <= c.toInt() && c.toInt() < HANGUL_SYLLABLES_END
    }
}