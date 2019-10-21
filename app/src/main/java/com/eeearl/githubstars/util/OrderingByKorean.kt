package com.eeearl.githubstars.util

import com.eeearl.githubstars.util.CharUtil.isKorean
import com.eeearl.githubstars.util.CharUtil.isNumber
import com.eeearl.githubstars.util.CharUtil.isAlphabet
import com.eeearl.githubstars.util.CharUtil.isSpecial

/**
 * String 을 한글 > 영어 > 숫자 > 특수문자 순으로 정열해주는 Sort 클래스
 */
object OrderingByKorean {

    private const val REVERSE = -1
    private const val LEFT_FIRST = -1
    private const val RIGHT_FIRST = 1

    @JvmStatic
    fun compare(left: String, right: String) : Int {
        val filteredLeft = left.toUpperCase().replace(" ", "")
        val filteredRight = right.toUpperCase().replace(" ", "")

        val leftLen = filteredLeft.length
        val rightLen = filteredRight.length
        val minLen = kotlin.math.min(leftLen, rightLen)

        for (i in 0 until minLen) {
            val leftCh = filteredLeft[i]
            val rightCh = filteredRight[i]

            if (leftCh != rightCh) {
                if (isKoreanAndEnglish(leftCh, rightCh) || isKoreanAndNumber(leftCh, rightCh)
                    || isEnglishAndNumber(leftCh, rightCh) || isKoreanAndSpecial(leftCh, rightCh)) {
                    return (leftCh - rightCh) * REVERSE
                } else if (isEnglishAndSpecial(leftCh, rightCh) || isNumberAndSpecial(leftCh, rightCh)) {
                    if (isAlphabet(leftCh) || isNumber(leftCh)) {
                        return LEFT_FIRST
                    } else {
                        return RIGHT_FIRST
                    }
                } else {
                    return leftCh - rightCh
                }
            }
        }

        return leftLen - rightLen
    }


    private fun isKoreanAndEnglish(ch1: Char, ch2: Char): Boolean {
        return isAlphabet(ch1) && isKorean(ch2) || isKorean(ch1) && isAlphabet(ch2)
    }

    private fun isKoreanAndNumber(ch1: Char, ch2: Char): Boolean {
        return isNumber(ch1) && isKorean(ch2) || isKorean(ch1) && isNumber(ch2)
    }

    private fun isEnglishAndNumber(ch1: Char, ch2: Char): Boolean {
        return isNumber(ch1) && isAlphabet(ch2) || isAlphabet(ch1) && isNumber(ch2)
    }

    private fun isKoreanAndSpecial(ch1: Char, ch2: Char): Boolean {
        return isKorean(ch1) && isSpecial(ch2) || isSpecial(ch1) && isKorean(ch2)
    }

    private fun isEnglishAndSpecial(ch1: Char, ch2: Char): Boolean {
        return isAlphabet(ch1) && isSpecial(ch2) || isSpecial(ch1) && isAlphabet(ch2)
    }

    private fun isNumberAndSpecial(ch1: Char, ch2: Char): Boolean {
        return isNumber(ch1) && isSpecial(ch2) || isSpecial(ch1) && isNumber(ch2)
    }
}