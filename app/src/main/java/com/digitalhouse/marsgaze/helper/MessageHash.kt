package com.digitalhouse.marsgaze.helper

import java.security.MessageDigest

/**
 * Faz o hash de uma mensagem e retorna ela a partir da operação HashAndSalt. É possível trocar o
 * algoritmo e o sal a partir de classes derivadas.
 *
 * @author Jomar Júnior
 *
 * @property message Mensagem a ter o hash processado / Message which hash will be processed
 * @property salt Sal da mensagem / Salt of message
 * @property algorithmName Nome do algoritmo hash / Name of the hash algorithm
 * @property result Cache do resultado do hash / Cache of the hash result.
 */
open class MessageHash(var message: String) {
    /**
     * PT-BR
     * Sal a ser adicionado na mensagem.
     *
     * EN-US
     * Salt which will be added on the message
     */
    protected open var salt = "m584Arszeg0*a9z/e"

    /**
     * PT-BR
     * Nome do algoritmo hash a ser utilizado. Padrão SHA256
     *
     * EN-US
     * Name of the algorithm to be used. Default SHA256
     */
    protected open var algorithmName = "SHA256"

    /**
     * PT-BR
     * Cache do resultado de hashAndSalt, tornando a operação O(1)
     *
     * EN-US
     * Cache of the result from hashAndSalt, making it O(1)
     */
    private var result: String? = null

    /**
     * PT-BR
     * Processa a mensagem e retorna o seu valor hash a partir do sal e do algoritmo escolhido.
     *
     * EN-US
     * Process the given message and returns it's hash valeu by using the desired salt and algorithm
     *
     * @return String O hash da mensagem / The hash message
     */
    open fun hashAndSalt(): String {
        if (result != null) {
            return result!!
        }

        val algorithm = MessageDigest.getInstance(this.algorithmName)

        val digested = algorithm.digest((message + salt).encodeToByteArray())

        // Retorna a string de formato hexadecimal
        result = digested.joinToString("") { "%02x".format(it) }
        return result!!
    }
}