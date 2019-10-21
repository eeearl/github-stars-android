package com.eeearl.githubstars.util

object CharUtil {

  @JvmStatic
  fun isAlphabet(ch: Char): Boolean {
    return ch in 'A'..'Z' || ch in 'a'..'z'
  }

  @JvmStatic
  fun isKorean(ch: Char): Boolean {
    return ch.toInt() in Integer.parseInt("AC00", 16)..Integer.parseInt("D7A3", 16)
  }

  @JvmStatic
  fun isNumber(ch: Char): Boolean {
    return ch in '0'..'9'
  }

  @JvmStatic
  fun isSpecial(ch: Char): Boolean {
    return ch in '!'..'/'   // !"#$%&amp;'()*+,-./
        || ch in ':'..'@'   //:;&lt;=&gt;?@
        || ch in '['..'`'   //[\]^_`
        || ch in '{'..'~'   //{|}~
  }
}