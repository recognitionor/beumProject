//package com.kal.beum.notice.data.network
//
//import com.kal.beum.core.domain.DataError
//import com.kal.beum.core.domain.Result
//import com.kal.beum.notice.data.dto.NoticeCategoryDto
//import com.kal.beum.notice.data.dto.NoticeDto
//
//class MockRemoteNoticeDataSource : RemoteNoticeDataSource {
//
//    override suspend fun getCategoryList(): Result<List<NoticeCategoryDto>, DataError.Remote> {
//        return Result.Success(
//            listOf(
//                NoticeCategoryDto(0, "전체"),
//                NoticeCategoryDto(1, "공지"),
//                NoticeCategoryDto(2, "내 활동"),
//                NoticeCategoryDto(3, "내 고민")
//            )
//        )
//    }
//
//    override suspend fun getNoticeList(): Result<List<NoticeDto>, DataError.Remote> {
//        return Result.Success(
//            listOf(
//                NoticeDto(
//                    noticeId = 1,
//                    noticeType = "내 활동",
//                    noticeType = "한겨울에도님이 회원님의 게시글을 좋아합니다.",
//                    createTime = 1717245296000L
//                ), NoticeDto(
//                    noticeId = 2,
//                    noticeType = "내 고민",
//                    noticeType = "kanyewestda님이 회원님의 게시글을 좋아합니다.",
//                    createTime = 1717245216000L
//                ), NoticeDto(
//                    noticeId = 3,
//                    noticeType = "내 고민",
//                    noticeType = "한겨울에도님이 회원님의 게시글을 좋아합니다.",
//                    createTime = 1717245416000L
//                ), NoticeDto(
//                    noticeId = 4,
//                    noticeType = "내 활동",
//                    noticeType = "한겨울에도님이 댓글을 남겼습니다: 힘내세요!! 화이팅",
//                    createTime = 1717245216000L
//                ), NoticeDto(
//                    noticeId = 5,
//                    noticeType = "공지",
//                    noticeType = "stayhween님이 나의 댓글을 좋아합니다 💙",
//                    createTime = 1717245216000L
//                ), NoticeDto(
//                    noticeId = 6,
//                    noticeType = "공지",
//                    noticeType = "비움에서 제공하는 이벤트, 절대 놓치지 마세요!",
//                    createTime = 1717245216000L
//                )
//            )
//        )
//    }
//
//}