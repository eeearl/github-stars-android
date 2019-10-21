package com.eeearl.githubstars.ui

interface SearchUserRowItemType {
    val itemType: SearchUserType
    var name: String
}

data class SearchUserRowSection(val section: String): SearchUserRowItemType {

    override val itemType: SearchUserType
        get() = SearchUserType.SECTION

    override var name: String = ""
}

data class SearchUserRowRemoteItem(
    val id: Int,
    override var name: String,
    val thumbnailUrl: String,
    val favorite: Boolean): SearchUserRowItemType {

    override val itemType: SearchUserType
        get() = SearchUserType.REMOTE_ITEM
}

data class SearchUserRowLocalItem(
    val id: Int,
    override var name: String,
    val thumbnailUrl: String,
    val favorite: Boolean): SearchUserRowItemType {

    override val itemType: SearchUserType
        get() = SearchUserType.LOCAL_ITEM
}