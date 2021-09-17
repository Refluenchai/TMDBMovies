package com.slicedwork.tmdbmovies.data.results

sealed class Result {
    class Success() : Result() {
        lateinit var any: Any
        lateinit var list: List<Any>

        constructor(list: List<Any>) : this(){
            this.list = list
        }

        constructor(any: Any) : this(){
            this.any = any
        }

        constructor(list: List<Any>, any: Any) : this(list) {
            this.any = any
            this.list = list
        }
    }
    class ApiError(val statusCode: Int) : Result()
    object ServerError : Result()
}