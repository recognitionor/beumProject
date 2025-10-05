//package com.kal.beum.community.data.network
//
//import com.kal.beum.community.data.dto.CategoryDto
//import com.kal.beum.community.data.dto.CommunityDto
//import com.kal.beum.community.data.dto.CommunityItemDto
//import com.kal.beum.core.domain.DataError
//import com.kal.beum.core.domain.Result
//
//class MockCommunityDataSource : RemoteCommunityDataSource {
//    override suspend fun getCategoryList(): Result<List<CategoryDto>, DataError.Remote> {
//        return Result.Success(
//            listOf(
//                CategoryDto(0, "전체"),
//                CategoryDto(1, "이직/퇴사"),
//                CategoryDto(2, "채용"),
//                CategoryDto(3, "면접"),
//                CategoryDto(4, "커리어"),
//                CategoryDto(5, "직무전환"),
//                CategoryDto(6, "해외취업")
//            )
//        )
//    }
//
//    private val resignationList = listOf(
//        CommunityItemDto(0, "니들은 이런거 피지마라", "바람피지마라", "기맨장", "이직/퇴사", true, 1750500635012L),
//        CommunityItemDto(1, "오랜만이죠 우리", "LTNS", "주오남", "이직/퇴사", true, 1750483295012L),
//        CommunityItemDto(2, "임박사무엘", "뿌린대로 거두리라 너무 좋네요 회사가 너무 좋아요.", "김우진", "이직/퇴사", false, 1750665515012L),
//        CommunityItemDto(3, "붙잡을걸 그랬나봐", "가진게 너무 없어 줄게 너무 없어", "오대수", "이직/퇴사", false, 1750531835012L),
//        CommunityItemDto(4, "퇴사 결심", "이제 떠납니다. 응원해주세요.", "결심자", "이직/퇴사", true, 1750344035012L),
//        CommunityItemDto(5, "새로운 도전", "불안하지만 설레는 마음으로!", "새출발러", "이직/퇴사", true, 1750316615012L),
//        CommunityItemDto(6, "퇴사 후의 여유", "커피 한 잔의 여유를 찾았어요.", "여유러", "이직/퇴사", false, 1750324355012L),
//        CommunityItemDto(7, "퇴사 이유", "개인적인 이유로 퇴사합니다.", "솔직러", "이직/퇴사", false, 1750629095012L),
//        CommunityItemDto(8, "퇴사 준비", "마지막까지 최선을 다합니다.", "준비러", "이직/퇴사", true, 1750218755012L),
//        CommunityItemDto(9, "퇴사 후기", "마지막 인사드립니다.", "굿바이", "이직/퇴사", false, 1750311215012L)
//    )
//
//    private val recruitmentList = listOf(
//        CommunityItemDto(10, "채용 공고 모음", "올해 채용 공고 리스트 공유합니다.", "채용마스터", "채용", true, 1750212395012L),
//        CommunityItemDto(11, "채용 시즌 돌입", "다들 준비는 잘 하고 있나요?", "구직러", "채용",false, 1750425695012L),
//        CommunityItemDto(12, "채용 정보 사이트 추천", "각종 채용 정보를 얻을 수 있는 사이트 모아봤어요.", "정보통", "채용", true, 1750629515012L),
//        CommunityItemDto(13, "스타트업 채용", "도전해보세요!", "스타트업러", "채용", false, 1750473035012L),
//        CommunityItemDto(14, "공채 VS 수시채용", "각각의 장단점이 있어요.", "조언자", "채용", true, 1750610975012L),
//        CommunityItemDto(15, "채용 트렌드 2025", "올해 채용 트렌드 정리해봤습니다.", "트렌드러", "채용", true, 1750425575012L),
//        CommunityItemDto(16, "채용 합격 후기", "작은 노력들이 큰 결과로 돌아왔습니다.", "합격러", "채용", true, 1750286255012L),
//        CommunityItemDto(17, "채용 사이트별 특징", "사이트별로 정리해봤습니다.", "정보수집가", "채용", false, 1750568855012L),
//        CommunityItemDto(18, "채용 담당자의 시선", "지원자들의 모습과 채용 포인트!", "인사담당자", "채용", true, 1750266815012L),
//        CommunityItemDto(19, "채용 Q&A", "궁금한 점 있으면 물어보세요.", "상담러", "채용", false, 1750148915012L)
//    )
//
//    private val interviewList = listOf(
//        CommunityItemDto(20, "첫 면접의 떨림", "떨리는 마음으로 면접장에 들어섰습니다.", "면접러", "면접", true, 1750201535012L),
//        CommunityItemDto(21, "면접 복장 팁", "깔끔한 인상이 중요!", "정장맨", "면접", false, 1750463555012L),
//        CommunityItemDto(22, "면접관의 한마디", "마지막 질문은 준비해가세요.", "준비생", "면접", true, 1750601135012L),
//        CommunityItemDto(23, "면접장 에티켓", "시간 엄수! 웃는 얼굴!", "에티켓왕", "면접", false, 1750262735012L),
//        CommunityItemDto(24, "다대다 면접 후기", "정신 없었지만, 침착하려고 노력했어요.", "다대다맨", "면접", true, 1750542695012L),
//        CommunityItemDto(25, "면접에서 당황했던 순간", "질문을 못 알아들었어요.", "멘붕러", "면접", false, 1750379135012L),
//        CommunityItemDto(26, "면접의 기술", "자신을 잘 보여주자!", "스피치장인", "면접", true, 1750731935012L),
//        CommunityItemDto(27, "면접 후의 마음", "마음이 후련했어요.", "후련러", "면접", false, 1750172555012L),
//        CommunityItemDto(28, "면접 준비 꿀팁", "모의 면접 꼭 해보세요!", "연습벌레", "면접", true, 1750149095012L),
//        CommunityItemDto(29, "합격 후기", "긴장했지만, 좋은 결과로!", "합격러", "면접", true, 1750278875012L)
//    )
//
//    private val careerList = listOf(
//        CommunityItemDto(30, "커리어 전환기", "새로운 분야에 도전!", "도전자", "커리어", true, 1750180895012L),
//        CommunityItemDto(31, "나만의 커리어 로드맵", "목표를 정리했어요.", "계획러", "커리어", false, 1750636775012L),
//        CommunityItemDto(32, "커리어 성장의 비결", "작은 성취도 쌓이다 보면!", "성장러", "커리어", true, 1750232255012L),
//        CommunityItemDto(33, "10년차의 고민", "정체된 느낌...", "경력직", "커리어", false, 1750235255012L),
//        CommunityItemDto(34, "커리어 멘토링", "멘토의 조언이 큰 도움이 됐어요.", "멘티", "커리어", true, 1750353875012L),
//        CommunityItemDto(35, "커리어 개발 책 추천", "생각이 달라지는 책들!", "책덕후", "커리어", true, 1750316435012L),
//        CommunityItemDto(36, "커리어를 위한 이직", "성장을 위해 이직을 결심!", "성장중", "커리어", true, 1750322255012L),
//        CommunityItemDto(37, "내 커리어 이야기", "돌이켜보면 의미 있었네요.", "회고러", "커리어", false, 1750480175012L),
//        CommunityItemDto(38, "커리어와 워라밸", "일과 삶의 균형!", "워라밸러", "커리어", true, 1750332935012L),
//        CommunityItemDto(39, "꿈을 향한 발걸음", "오늘도 한 발자국!", "열정러", "커리어", false, 1750074515012L)
//    )
//
//
//    private val totalList = resignationList + recruitmentList + interviewList + careerList
//
//    override suspend fun getCommunityList(
//        categoryId: Int, isDevil: Boolean
//    ): Result<CommunityDto, DataError.Remote> {
//        println("MockCommunityDataSource getCommunityList : $categoryId")
//        val result = when (categoryId) {
//            0 -> totalList
//            1 -> resignationList
//            2 -> recruitmentList
//            3 -> interviewList
//            4 -> careerList
//            else -> emptyList()
//        }
//        return Result.Success(result)
//    }
//}
