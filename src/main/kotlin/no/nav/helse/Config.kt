package no.nav.helse

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

private val serviceuserBasePath = Paths.get("/var/run/secrets/nais.io/service_user")

fun readServiceUserCredentials() = ServiceUser(
    username = Files.readString(serviceuserBasePath.resolve("username")),
    password = Files.readString(serviceuserBasePath.resolve("password"))
)

class ServiceUser(
    val username: String,
    val password: String
)