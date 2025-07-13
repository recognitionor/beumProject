package com.kal.beum.level.data.network

import com.kal.beum.core.domain.DataError
import com.kal.beum.core.domain.Result
import com.kal.beum.level.data.dto.RankerUserInfoDto

class MockRankerUserInfoDataSource : RemoteRankerUserInfoDataSource {
    override suspend fun getRankerList(isDevel: Boolean): Result<List<RankerUserInfoDto>, DataError.Remote> {
        return Result.Success(
            listOf(
                RankerUserInfoDto(
                    userId = "user1",
                    nickname = "랭킹1위",
                    level = 87,
                    rank = 1,
                    profileImageUrl = "https://raw.githubusercontent.com/recognitionor/ronImage/refs/heads/main/diamond_3142081.png",
                    score = 9500,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user2",
                    nickname = "랭킹2위",
                    level = 74,
                    rank = 2,
                    profileImageUrl = "",
                    score = 9200,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user3",
                    nickname = "랭킹3위",
                    level = 65,
                    rank = 3,
                    profileImageUrl = "",
                    score = 8900,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user4",
                    nickname = "랭킹4위",
                    level = 58,
                    rank = 4,
                    profileImageUrl = "",
                    score = 8600,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user5",
                    nickname = "랭킹5위",
                    level = 42,
                    rank = 5,
                    profileImageUrl = "",
                    score = 8300,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user6",
                    nickname = "랭킹6위",
                    level = 39,
                    rank = 6,
                    profileImageUrl = "",
                    score = 8000,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user7",
                    nickname = "랭킹7위",
                    level = 31,
                    rank = 7,
                    profileImageUrl = "",
                    score = 7700,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user8",
                    nickname = "랭킹8위",
                    level = 28,
                    rank = 8,
                    profileImageUrl = "",
                    score = 7400,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user9",
                    nickname = "랭킹9위",
                    level = 22,
                    rank = 9,
                    profileImageUrl = "https://raw.githubusercontent.com/recognitionor/ronImage/refs/heads/main/meteorite_9407489.png",
                    score = 7100,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user10",
                    nickname = "랭킹10위",
                    level = 15,
                    rank = 10,
                    profileImageUrl = "https://raw.githubusercontent.com/recognitionor/ronImage/refs/heads/main/star_5650367.png",
                    score = 6800,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user11",
                    nickname = "랭킹11위",
                    level = 67,
                    rank = 11,
                    profileImageUrl = "",
                    score = 6500,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user12",
                    nickname = "랭킹12위",
                    level = 54,
                    rank = 12,
                    profileImageUrl = "",
                    score = 6200,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user13",
                    nickname = "랭킹13위",
                    level = 48,
                    rank = 13,
                    profileImageUrl = "",
                    score = 5900,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user14",
                    nickname = "랭킹14위",
                    level = 36,
                    rank = 14,
                    profileImageUrl = "",
                    score = 5600,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user15",
                    nickname = "랭킹15위",
                    level = 29,
                    rank = 15,
                    profileImageUrl = "",
                    score = 5300,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user16",
                    nickname = "랭킹16위",
                    level = 71,
                    rank = 16,
                    profileImageUrl = "",
                    score = 5000,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user17",
                    nickname = "랭킹17위",
                    level = 63,
                    rank = 17,
                    profileImageUrl = "",
                    score = 4700,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user18",
                    nickname = "랭킹18위",
                    level = 52,
                    rank = 18,
                    profileImageUrl = "",
                    score = 4400,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user19",
                    nickname = "랭킹19위",
                    level = 45,
                    rank = 19,
                    profileImageUrl = "",
                    score = 4100,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user20",
                    nickname = "랭킹20위",
                    level = 38,
                    rank = 20,
                    profileImageUrl = "",
                    score = 3800,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user21",
                    nickname = "랭킹21위",
                    level = 33,
                    rank = 21,
                    profileImageUrl = "",
                    score = 3500,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user22",
                    nickname = "랭킹22위",
                    level = 26,
                    rank = 22,
                    profileImageUrl = "",
                    score = 3200,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user23",
                    nickname = "랭킹23위",
                    level = 19,
                    rank = 23,
                    profileImageUrl = "",
                    score = 2900,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user24",
                    nickname = "랭킹24위",
                    level = 61,
                    rank = 24,
                    profileImageUrl = "",
                    score = 2600,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user25",
                    nickname = "랭킹25위",
                    level = 49,
                    rank = 25,
                    profileImageUrl = "",
                    score = 2300,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user26",
                    nickname = "랭킹26위",
                    level = 41,
                    rank = 26,
                    profileImageUrl = "",
                    score = 2000,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user27",
                    nickname = "랭킹27위",
                    level = 34,
                    rank = 27,
                    profileImageUrl = "",
                    score = 1700,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user28",
                    nickname = "랭킹28위",
                    level = 27,
                    rank = 28,
                    profileImageUrl = "",
                    score = 1400,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user29",
                    nickname = "랭킹29위",
                    level = 21,
                    rank = 29,
                    profileImageUrl = "",
                    score = 1100,
                    isDevil = isDevel
                ),
                RankerUserInfoDto(
                    userId = "user30",
                    nickname = "랭킹30위",
                    level = 16,
                    rank = 30,
                    profileImageUrl = "",
                    score = 800,
                    isDevil = isDevel
                )
            )
        )
    }
}