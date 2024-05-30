package okik.tech.community.admin.shared.networking.datasources

interface AuthenticationDataSource {
    actual fun signUp() {}
    actual fun logIn() {}
    actual fun logOut() {}
}