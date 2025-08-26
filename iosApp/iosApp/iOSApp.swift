import SwiftUI
import KakaoSDKCommon
import KakaoSDKAuth

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ComposeView()     .onOpenURL { url in
                NSLog("[Kakao] SwiftUI onOpenURL: %@", url.absoluteString)
                if AuthApi.isKakaoTalkLoginUrl(url) {
                    AuthController.handleOpenUrl(url: url)
                }
            }
        }
    }
}

final class AppDelegate: NSObject, UIApplicationDelegate {

    // (보강) 일부 케이스에 대비해 AppDelegate 경로에서도 받아줌
    func application(_ app: UIApplication,
                     open url: URL,
                     options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        NSLog("[Kakao] AppDelegate openURL: %@", url.absoluteString)
        if AuthApi.isKakaoTalkLoginUrl(url) {
            return AuthController.handleOpenUrl(url: url)
        }
        return false
    }
}
