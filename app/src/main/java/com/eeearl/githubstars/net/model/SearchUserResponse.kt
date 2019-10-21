package com.eeearl.githubstars.net.model

import com.google.gson.annotations.SerializedName

data class SearchUserResponse (
    @SerializedName("total_count")  val totalCount: Int,
    @SerializedName("items") val userItems: List<UserItem>
)

data class UserItem (
    @SerializedName("id")  val id: Int,
    @SerializedName("avatar_url")  val avatarUrl: String,
    @SerializedName("login")  val login: String,
    @SerializedName("text_matches") private val textMatches: List<TextMatches>
) {
    var name: String? = null
        get() {
            val matched = textMatches.firstOrNull { it.property == "name" }
            return if (matched?.fragment != null) matched.fragment else null
        }
}

data class TextMatches(
    @SerializedName("object_url") val objectUrl: String,
    @SerializedName("object_type") val objectType: String,
    @SerializedName("property") val property: String?,
    @SerializedName("fragment") val fragment: String?
)