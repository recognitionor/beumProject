package com.kal.beum.write.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.write.data.CategoryGroupDto

class MockWriteCategoryDataSource(
) : RemoteWriteCategoryDataSource {
    override suspend fun getWriteCategoryList(): Result<List<CategoryGroupDto>, DataError.Remote> {
        return Result.Success(
            listOf(
                // 커리어 관리
                CategoryGroupDto(1, "커리어 관리", "이직/퇴사"),
                CategoryGroupDto(2, "커리어 관리", "채용"),
                CategoryGroupDto(3, "커리어 관리", "면접"),
                CategoryGroupDto(4, "커리어 관리", "커리어"),
                CategoryGroupDto(5, "커리어 관리", "직무전환"),
                CategoryGroupDto(6, "커리어 관리", "해외취업"),

                // 보상 및 복지
                CategoryGroupDto(7, "보상 및 복지", "연봉/급여"),
                CategoryGroupDto(8, "보상 및 복지", "복리후생"),
                CategoryGroupDto(9, "보상 및 복지", "성과금"),
                CategoryGroupDto(10, "보상 및 복지", "휴가"),
                CategoryGroupDto(11, "보상 및 복지", "연차"),
                CategoryGroupDto(12, "보상 및 복지", "휴직"),

                // 업무 환경
                CategoryGroupDto(13, "업무 환경", "동료"),
                CategoryGroupDto(14, "업무 환경", "상사"),
                CategoryGroupDto(15, "업무 환경", "리더십"),
                CategoryGroupDto(16, "업무 환경", "멘토링"),
                CategoryGroupDto(17, "업무 환경", "사내정치"),
                CategoryGroupDto(18, "업무 환경", "갈등해결"),
                CategoryGroupDto(19, "업무 환경", "사내연애"),
                CategoryGroupDto(20, "업무 환경", "회식"),

                // 인간관계
                CategoryGroupDto(21, "인간관계", "업무"),
                CategoryGroupDto(22, "인간관계", "출퇴근"),
                CategoryGroupDto(23, "인간관계", "재택근무"),
                CategoryGroupDto(24, "인간관계", "유연근무"),
                CategoryGroupDto(25, "인간관계", "조직문화"),
                CategoryGroupDto(26, "인간관계", "팀워크"),
                CategoryGroupDto(27, "인간관계", "워라밸"),

                // 개인 성장
                CategoryGroupDto(28, "개인 성장", "자기계발"),
                CategoryGroupDto(29, "개인 성장", "재테크"),
                CategoryGroupDto(30, "개인 성장", "건강"),
                CategoryGroupDto(31, "개인 성장", "부업/투잡"),

                // 직장 내 문제
                CategoryGroupDto(32, "직장 내 문제", "차별"),
                CategoryGroupDto(33, "직장 내 문제", "괴롭힘"),
                CategoryGroupDto(34, "직장 내 문제", "따돌림"),
                CategoryGroupDto(35, "직장 내 문제", "권고사직"),
                CategoryGroupDto(36, "직장 내 문제", "부당해고"),

                // 회사 유형
                CategoryGroupDto(37, "회사 유형", "대기업"),
                CategoryGroupDto(38, "회사 유형", "중견기업"),
                CategoryGroupDto(39, "회사 유형", "중소기업"),
                CategoryGroupDto(40, "회사 유형", "외국계 기업"),
                CategoryGroupDto(41, "회사 유형", "스타트업")
            )
        )
    }
}