package com.tyt.deepseektest.data

data class ApiAddressItem(
    val apiAddress: String,
    override val alias: String = "",
    override val lastModifiedTime: Long,
) : BaseDataItem(apiAddress, alias, lastModifiedTime)
