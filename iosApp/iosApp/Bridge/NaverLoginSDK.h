// NaverLoginSDK.h  (개념적으로 등가 인터페이스)

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface NaverLoginSDK : NSObject

// Swift의 `public static let shared` → 클래스 프로퍼티(읽기 전용)
@property (class, nonatomic, readonly, strong) NaverLoginSDK *shared;

// Swift의 `@objc public static func initSDK()` → 클래스 메서드
+ (void)initSDK;

@end

NS_ASSUME_NONNULL_END
