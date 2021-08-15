package com.borzg.data.commons

interface DomainMapper<DomainModel: Any> {

    fun toDomain(): DomainModel
}