package com.abdulmanov.core.network.common

import okhttp3.TlsVersion
import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import javax.net.ssl.*

class Tls12SocketFactory(private val delegate: SSLSocketFactory) : SSLSocketFactory() {

    private fun Socket.patchForTls12(): Socket {
        return (this as? SSLSocket)?.apply {
            enabledProtocols += TlsVersion.TLS_1_2.javaName()
        } ?: this
    }

    override fun getDefaultCipherSuites(): Array<String> {
        return delegate.defaultCipherSuites
    }

    override fun getSupportedCipherSuites(): Array<String> {
        return delegate.supportedCipherSuites
    }

    @Throws(IOException::class)
    override fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket? {
        return delegate.createSocket(s, host, port, autoClose)
            .patchForTls12()
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(host: String, port: Int): Socket? {
        return delegate.createSocket(host, port)
            .patchForTls12()
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(
        host: String,
        port: Int,
        localHost: InetAddress,
        localPort: Int
    ): Socket? {
        return delegate.createSocket(host, port, localHost, localPort)
            .patchForTls12()
    }

    @Throws(IOException::class)
    override fun createSocket(host: InetAddress, port: Int): Socket? {
        return delegate.createSocket(host, port)
            .patchForTls12()
    }

    @Throws(IOException::class)
    override fun createSocket(
        address: InetAddress,
        port: Int,
        localAddress: InetAddress,
        localPort: Int
    ): Socket? {
        return delegate.createSocket(address, port, localAddress, localPort)
            .patchForTls12()
    }
}