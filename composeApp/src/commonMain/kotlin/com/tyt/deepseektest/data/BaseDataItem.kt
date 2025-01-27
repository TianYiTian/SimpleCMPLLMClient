package com.tyt.deepseektest.data

sealed class BaseDataItem(open val data: String, open val alias: String = "", open val lastModifiedTime: Long) {
//    fun getData():String
//
//    fun getAlias():String
//
//    fun getLastModified():Long
}

