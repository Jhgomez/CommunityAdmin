package okik.tech.community.admin.shared.networking.datasources

interface AuthenticationDataSource {
    fun signUp()
    fun logIn()
    fun logOut()
}