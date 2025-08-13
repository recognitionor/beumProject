import Foundation

@objc(KaKaoLoginSDK)
public class KaKaoLoginSDK: NSObject {
    @objc public static let shared = KaKaoLoginSDK()

    @objc private override init() {
        super.init()
    }

    @objc public static func initSDK() {
        
    }

}
