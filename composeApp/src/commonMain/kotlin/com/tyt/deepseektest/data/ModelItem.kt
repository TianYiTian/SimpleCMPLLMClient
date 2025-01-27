package com.tyt.deepseektest.data

data class ModelItem(
    val name: String,
    override val alias: String = "",
    override val lastModifiedTime: Long,
) : BaseDataItem(name, alias, lastModifiedTime)
