package com.borzg.domain.model

import javax.inject.Qualifier

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DB