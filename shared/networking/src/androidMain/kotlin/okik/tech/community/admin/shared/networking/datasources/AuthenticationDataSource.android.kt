package okik.tech.community.admin.shared.networking.datasources

actual interface AuthenticationDataSource {
    actual fun signUp()
    actual fun logIn()
    actual fun logOut()
}