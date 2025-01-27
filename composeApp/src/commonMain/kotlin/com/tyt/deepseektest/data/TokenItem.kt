package com.tyt.deepseektest.data

data class TokenItem(
    val token: String,
    override val alias: String = "",
    override val lastModifiedTime: Long,
) : BaseDataItem(token, alias, lastModifiedTime)
