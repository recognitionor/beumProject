package com.kal.beum.main.domain

object SocialType {
    const val KAKAO = "카카오"
    const val GOOGLE = "구글"
    const val APPLE = "애플"
    const val NAVER = "네이버"
    const val KAKAO_CODE = 1
    const val GOOGLE_CODE = 2
    const val APPLE_CODE = 3
    const val NAVER_CODE = 4

    fun toType(socialName: String): Int {
        when (socialName.lowercase()) {
            "카카오", "kakao" -> {
                return KAKAO_CODE
            }

            "구글", "google" -> {
                return GOOGLE_CODE
            }

            "애플", "apple" -> {
                return APPLE_CODE
            }

            "네이버", "naver" -> {
                return NAVER_CODE
            }

            else -> {
                return GOOGLE_CODE
            }
        }
    }
}