import Foundation
import AuthenticationServices

@objc(AppleLoginSDK)
public class AppleLoginSDK: NSObject {
    @objc public static let shared = AppleLoginSDK()
    
    @objc private override init() {
        super.init()
    }
    
    @objc(requestLoginWithCompletion:)
    public class func requestLogin(
        _ completion: @escaping (_ identityToken: NSString?, _ authorizationCode: NSString?, _ error: NSError?) -> Void
    ) {
        let provider = ASAuthorizationAppleIDProvider()
        let request = provider.createRequest()
        request.requestedScopes = [.fullName, .email]
        
        let controller = ASAuthorizationController(authorizationRequests: [request])
        let delegate = AppleSignInDelegate.shared
        delegate.completion = completion
        
        controller.delegate = delegate
        controller.presentationContextProvider = delegate
        controller.performRequests()
    }
    
    @objc(logoutWithCompletion:)
    public class func logout(_ completion: @escaping (_ error: NSError?) -> Void) {
        // Apple Sign In doesn't have a logout API
        // Session management is handled by the app
        completion(nil)
    }
}

// MARK: - Apple Sign In Delegate
class AppleSignInDelegate: NSObject, ASAuthorizationControllerDelegate, ASAuthorizationControllerPresentationContextProviding {
    static let shared = AppleSignInDelegate()
    
    var completion: ((NSString?, NSString?, NSError?) -> Void)?
    
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        guard let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let window = scene.windows.first else {
            return UIWindow()
        }
        return window
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        guard let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential else {
            let error = NSError(domain: "com.kal.beum.apple", code: -1,
                                userInfo: [NSLocalizedDescriptionKey: "Invalid credential type"])
            completion?(nil, nil, error)
            return
        }
        
        guard let identityTokenData = appleIDCredential.identityToken,
              let identityToken = String(data: identityTokenData, encoding: .utf8) else {
            let error = NSError(domain: "com.kal.beum.apple", code: -2,
                                userInfo: [NSLocalizedDescriptionKey: "Failed to get identity token"])
            completion?(nil, nil, error)
            return
        }
        
        var authorizationCode: String? = nil
        if let authCodeData = appleIDCredential.authorizationCode {
            authorizationCode = String(data: authCodeData, encoding: .utf8)
        }
        
        completion?(identityToken as NSString, authorizationCode as NSString?, nil)
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        let nsError = error as NSError
        completion?(nil, nil, nsError)
    }
}
