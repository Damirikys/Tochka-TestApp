package pro.jeminay.tochka.auth

class AuthException : Exception {
    constructor(message: String): super(message)
    constructor(e: Throwable): super(e)
}