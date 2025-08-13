import Foundation
import NidThirdPartyLogin

@objc(NaverLoginSDK)
public class NaverLoginSDK: NSObject {
    @objc public static let shared = NaverLoginSDK()

    @objc private override init() {
        super.init()
        
    }

    @objc public static func initSDK() {

    }

}
