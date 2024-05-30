package okik.tech.community.admin.shared.networking.datasources

expect interface AuthenticationDataSource {
    fun signUp()
    fun logIn()
    fun logOut()
}