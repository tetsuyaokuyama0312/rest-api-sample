package com.example.restapi.util

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.UncheckedIOException


fun <T> readObjectFromJsonResource(
    fileName: String, objectClass: Class<T>, mapper: ObjectMapper, caller: Any,
): T {
    val input = getResourceAsStream(caller, fileName)
    return mapper.readValue(input, objectClass)
}

fun <T> readListFromJsonResource(
    fileName: String, elementClass: Class<T>, mapper: ObjectMapper, caller: Any,
): List<T> {
    val input = getResourceAsStream(caller, fileName)
    return mapper.readValue(input, mapper.typeFactory.constructCollectionType(List::class.java, elementClass))
}

private fun getResourceAsStream(caller: Any, fileName: String): InputStream {
    return caller.javaClass.getResourceAsStream(fileName)
        ?: throw UncheckedIOException(FileNotFoundException("File not found: $fileName"))
}