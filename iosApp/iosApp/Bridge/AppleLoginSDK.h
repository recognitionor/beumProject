// AppleLoginSDK.h

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef void (^AppleLoginCompletion)(NSString *_Nullable identityToken,
                                     NSString *_Nullable authorizationCode,
                                     NSError *_Nullable error);

@interface AppleLoginSDK : NSObject

@property(class, nonatomic, readonly, strong) AppleLoginSDK *shared;

+ (void)requestLoginWithCompletion:(AppleLoginCompletion)completion;
+ (void)logoutWithCompletion:
    (void (^_Nonnull)(NSError *_Nullable error))completion;

@end

NS_ASSUME_NONNULL_END
