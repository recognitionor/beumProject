//package com.kal.beum.myinfo.data.network
//
//import com.kal.beum.core.data.ApiConstants
//import com.kal.beum.core.domain.DataError
//import com.kal.beum.core.domain.Result
//import com.kal.beum.login.data.dto.LoginResponseDto
//import com.kal.beum.login.domain.SocialToken
//import com.kal.beum.main.domain.SocialType
//import com.kal.beum.main.domain.UserInfo
//import com.kal.beum.myinfo.data.dto.MyContentDto
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//
//class MockMyInfoDataSource(private val httpClient: HttpClient) : RemoteMyInfoDataSource {
//    override suspend fun getMyContents(userId: Int): Result<List<MyContentDto>, DataError.Remote> {
//        return Result.Success(
//            listOf(
//                MyContentDto(1, "오늘 첫 출근!", "정말 떨려요. 응원 부탁드립니다.", "커리어", "2025-07-01", 8, 2),
//                MyContentDto(2, "면접에서 실수했어요", "너무 아쉬운 하루네요.", "이직/퇴사", "2025-07-02", 11, 1),
//                MyContentDto(3, "회사 점심 추천좀!", "매일 고르기 너무 힘들어요.", "잡담", "2025-07-03", 3, 0),
//                MyContentDto(4, "이직 준비 어떻게?", "이직 준비하시는 분들 팁 공유해주세요.", "이직/퇴사", "2025-07-04", 7, 2),
//                MyContentDto(5, "퇴사 언제가 좋을까요?", "퇴사 시기 고민 중입니다.", "이직/퇴사", "2025-07-05", 10, 3),
//                MyContentDto(6, "연봉협상 성공기", "드디어 연봉 협상 성공했어요!", "커리어", "2025-07-06", 13, 5),
//                MyContentDto(7, "상사와 갈등", "요즘 상사랑 계속 부딪혀서 고민입니다.", "고민상담", "2025-07-07", 6, 2),
//                MyContentDto(8, "자격증 추천", "IT 자격증 뭐가 좋을까요?", "커리어", "2025-07-08", 4, 1),
//                MyContentDto(9, "재택근무 꿀팁", "재택근무 효율적으로 하는 방법 궁금해요.", "잡담", "2025-07-09", 9, 2),
//                MyContentDto(10, "오늘 너무 힘들었어요", "지치는 하루네요.", "잡담", "2025-07-10", 5, 1),
//                MyContentDto(11, "이직했는데 어색해요", "새 직장 적응이 쉽지 않네요.", "이직/퇴사", "2025-07-11", 12, 4),
//                MyContentDto(12, "업무량이 늘었어요", "야근이 많아져서 힘듭니다.", "고민상담", "2025-07-12", 7, 2),
//                MyContentDto(13, "퇴사 후 휴식", "잠시 쉬는 것도 좋은 선택인 것 같아요.", "이직/퇴사", "2025-07-13", 6, 0),
//                MyContentDto(14, "사내 정치 피하는 법", "어떻게 하면 좋을까요?", "잡담", "2025-07-14", 8, 3),
//                MyContentDto(15, "스터디 모집합니다", "이직 준비 스터디 구해요.", "이직/퇴사", "2025-07-15", 4, 1),
//                MyContentDto(16, "승진 실패ㅠㅠ", "아쉽게도 이번엔 실패했어요.", "커리어", "2025-07-16", 9, 2),
//                MyContentDto(17, "업무 자동화 툴 추천", "자동화 툴로 업무 효율 높이고 싶어요.", "커리어", "2025-07-17", 3, 1),
//                MyContentDto(18, "연차 쓸 타이밍?", "언제 연차 쓰면 좋을까요?", "잡담", "2025-07-18", 5, 0),
//                MyContentDto(19, "신입 때의 추억", "첫 회사에서 배운 게 많았어요.", "잡담", "2025-07-19", 10, 3),
//                MyContentDto(20, "면접 준비 꿀팁", "면접 때 실수 안 하는 방법이 있을까요?", "이직/퇴사", "2025-07-20", 7, 2)
//            )
//        )
//    }
//
//    override suspend fun getMyReply(userId: Int): Result<List<MyContentDto>, DataError.Remote> {
//        return Result.Success(
//            listOf(
//                MyContentDto(
//                    21, "첫 월급 썼어요!", "기분 너무 좋아요. 여러분은 어디에 쓰셨나요?", "잡담", "2025-07-01", 15, 6
//                ),
//                MyContentDto(22, "퇴사 고민", "계속 다닐지 고민입니다.", "이직/퇴사", "2025-07-02", 9, 1),
//                MyContentDto(23, "프로젝트 폭탄", "갑자기 업무가 몰려서 당황했어요.", "고민상담", "2025-07-03", 6, 3),
//                MyContentDto(24, "업무 스킬업", "새로운 기술 배워보고 싶어요.", "커리어", "2025-07-04", 8, 2),
//                MyContentDto(25, "취업 준비생 질문!", "서류 통과 팁 있나요?", "이직/퇴사", "2025-07-05", 12, 5),
//                MyContentDto(26, "직장 내 인간관계", "좋은 관계 유지하는 법?", "잡담", "2025-07-06", 11, 1),
//                MyContentDto(27, "재택근무 vs 출근", "여러분은 어떤 게 더 좋으세요?", "잡담", "2025-07-07", 14, 4),
//                MyContentDto(28, "경력 개발 조언", "이직을 위한 커리어 개발법 궁금해요.", "커리어", "2025-07-08", 7, 2),
//                MyContentDto(29, "새 직장 첫인상", "첫인상 중요하다고 하던데 꿀팁 있나요?", "잡담", "2025-07-09", 10, 3),
//                MyContentDto(30, "퇴사 통보 방법", "어떻게 말하는 게 좋을까요?", "이직/퇴사", "2025-07-10", 5, 1),
//                MyContentDto(31, "상사 칭찬받은 날", "오늘 상사에게 칭찬받았습니다!", "잡담", "2025-07-11", 13, 5),
//                MyContentDto(32, "스터디원 모집", "취업 스터디 멤버 구합니다.", "이직/퇴사", "2025-07-12", 6, 2),
//                MyContentDto(33, "신입 적응기", "신입 때 어려웠던 점 공유해요.", "잡담", "2025-07-13", 8, 3),
//                MyContentDto(34, "자격증 따기 힘드네요", "포기하지 말라는 조언이 필요해요.", "커리어", "2025-07-14", 4, 0),
//                MyContentDto(35, "점심 메뉴 투표", "오늘은 어떤 메뉴가 좋을까요?", "잡담", "2025-07-15", 7, 1),
//                MyContentDto(36, "면접관 질문 모음", "어려운 질문엔 어떻게 답하시나요?", "이직/퇴사", "2025-07-16", 9, 2),
//                MyContentDto(37, "업무 피드백", "동료 피드백 어떻게 주고 받으세요?", "잡담", "2025-07-17", 5, 0),
//                MyContentDto(38, "자소서 작성법", "자소서 쓸 때 주의할 점은?", "이직/퇴사", "2025-07-18", 12, 4),
//                MyContentDto(39, "슬럼프 극복법", "슬럼프가 왔을 때 어떻게 극복하세요?", "고민상담", "2025-07-19", 6, 1),
//                MyContentDto(40, "오늘 하루 마무리", "오늘도 수고하셨습니다.", "잡담", "2025-07-20", 10, 2)
//            )
//        )
//    }
//
//    override suspend fun reportUser(myContentId: Int): Result<Unit, DataError.Remote> {
//        return Result.Success(Unit)
//    }
//
//    override suspend fun refreshLoginInfo(
//        socialType: Int, socialToken: SocialToken
//    ): Result<UserInfo, DataError.Remote> {
//
//        val response = httpClient.post(ApiConstants.Endpoints.SIGNIN) {
//            setBody(
//                mapOf(
//                    "accessToken" to socialToken.accessToken,
//                    "socialType" to SocialType.toName(socialType)
//                )
//            )
//        }
//        if (response.status.value == 200) {
//            val responseBody = response.body<LoginResponseDto>()
//            val userInfo = UserInfo(
//                userId = responseBody.userId,
//                nickName = responseBody.nickName,
//                socialType = socialType,
//                email = responseBody.email,
//                sessionKey = "",
//                accessToken = responseBody.tokenSet?.accessToken ?: socialToken.accessToken,
//                refreshToken = responseBody.tokenSet?.refreshToken ?: socialToken.refreshToken,
//                profileImageId = responseBody.profileImageId,
//                needSignUp = responseBody.needSignUp
//            )
//            return Result.Success(userInfo)
//        } else {
//            return Result.Error(DataError.Remote.LOGIN_FAILED)
//        }
//    }
//}