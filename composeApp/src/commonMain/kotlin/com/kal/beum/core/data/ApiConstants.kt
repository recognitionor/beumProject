package com.kal.beum.core.data

object ApiConstants {

    const val BASE_URL = "https://dev.winning-lotto.com"

    object KEY {
        const val KEY_AUTH_TOKEN = "X-AUTH-TOKEN"
        const val KEY_IS_DEVIL = "isDevil"
        const val KEY_SIZE = "size"

        const val KEY_TOKEN = "token"
        const val KEY_PAGE = "page"
        const val KEY_CATEGORY_ID = "categoryId"
    }

    object Endpoints {
        const val COMMENT = "/comment"
        const val BOARD = "/board"
        const val BOARDS = "/boards"

        const val FIREBASE_TOKEN = "/firebase-token"

        const val MY_BOARD_INFO = "/my-board-info"

        const val MY_COMMENT_LIST = "/my-comment-list"
        const val COMMENTS = "/comments"
        const val SIGNUP = "/signup"
        const val SIGNIN = "/signin"

        const val CATEGORY_LIST = "category-list"
    }
}