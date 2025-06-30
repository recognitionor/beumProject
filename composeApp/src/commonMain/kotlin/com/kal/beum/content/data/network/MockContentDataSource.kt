package com.kal.beum.content.data.network

import com.kal.beum.content.data.dto.ContentDetailDto
import com.kal.beum.content.data.dto.ReplyInfoDto
import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result

class MockContentDataSource : RemoteContentDataSource {
    val replyList = listOf(
        ReplyInfoDto(
            writer = "유저1",
            content = "첫 번째 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245296000L
        ),
        ReplyInfoDto(
            writer = "유저2",
            content = "두 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245396000L
        ),
        ReplyInfoDto(
            writer = "유저3",
            content = "세 번째 댓글입니다.",
            isLiked = 1,
            isSelected = true,
            replyList = emptyList(),
            lastModifiedTime = 1717245496000L
        ),
        ReplyInfoDto(
            writer = "유저4",
            content = "네 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245596000L
        ),
        ReplyInfoDto(
            writer = "유저5",
            content = "다섯 번째 댓글입니다.",
            isLiked = 1,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245696000L
        ),
        ReplyInfoDto(
            writer = "유저6",
            content = "여섯 번째 댓글입니다.",
            isLiked = 0,
            isSelected = false,
            replyList = emptyList(),
            lastModifiedTime = 1717245796000L
        )
    )

    override suspend fun getContentDetail(contentId: Int): Result<ContentDetailDto, DataError.Remote> {
        return Result.Success(
            ContentDetailDto(
                id = 1,
                title = "목업 제목",
                content = "이것은 목업 본문입니다.",
                writer = "홍길동",
                isDevil = false,
                categoryName = "자유게시판",
                rewardPoint = 100,
                tags = "#KMP #Compose",
                likeCount = 10,
                viewCount = 200,
                lastModifiedTime = 1717245296000L,
                replyList = replyList
            )
        )
    }
}
