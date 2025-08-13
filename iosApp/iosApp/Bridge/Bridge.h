#import <Foundation/Foundation.h>
#import "KaKaoLoginSDK.h"
#import "NaverLoginSDK.h"

@class KaKaoLoginSDK;
@class NaverLoginSDK;

NS_ASSUME_NONNULL_BEGIN

@interface Bridge : NSObject
- (instancetype)initWithKakao:(KaKaoLoginSDK *)kakao naver:(NaverLoginSDK *)naver;
- (NSString *)greetFromTest;
- (NSInteger)add:(NSInteger)a b:(NSInteger)b;
@end

NS_ASSUME_NONNULL_END
