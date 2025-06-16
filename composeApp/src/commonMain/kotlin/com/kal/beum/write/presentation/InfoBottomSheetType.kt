package com.kal.beum.write.presentation

sealed class InfoBottomSheetType {
    data object None : InfoBottomSheetType() // 아무 바텀시트도 열려있지 않은 상태

    data class GuideInfo( // 키워드 인포 시트
        val title: String = "글쓰기 가이드", val bullets: List<String> = listOf(
            "가이드라인에 맞지 않는 글은 통보 없이 숨겨질 수 있습니다."
        )
    ) : InfoBottomSheetType()

    data class TagInfo( // 키워드 인포 시트
        val title: String = "태그를 쓰면 좋은점", val bullets: List<String> = listOf(
            "카테고리에 없는 내용을 태그할 수 있어요.", "내 고민을 정확히 알릴 수 있어요."
        )
    ) : InfoBottomSheetType()

    data class CommunityInfo( // (예시) 내용 인포 시트
        val title: String = "커뮤니티 모드 변경", val bullets: List<String> = listOf(
            "고민글의 감정에 따라 천사와 악마를 선택해보세요", "천사는 위로가 필요할 때, 악마는 함께 분노해요"
        )
    ) : InfoBottomSheetType()

    data class PointInfo( // (예시) 내용 인포 시트
        val title: String = "포인트를 걸면 좋은점", val bullets: List<String> = listOf(
            "포인트를 받기위해 사람들이 더 관심을 갖을 수 있어요", "댓글을 쓴 한 사람에게 포인트를 줄 수 있어요"
        )
    ) : InfoBottomSheetType()

}