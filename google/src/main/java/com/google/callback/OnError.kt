package com.google.callback

interface OnError<ERROR> {
    fun onError(error: ERROR)
}