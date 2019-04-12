package com.google.callback

interface OnSuccess<RESULT> {
    fun onSuccess(result: RESULT)
}