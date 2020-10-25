package com.borzg.data.service

import io.reactivex.Single

interface DetailElementService<T> {

    fun getElement(elementId : Int) : Single<T>

}