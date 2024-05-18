package com.eeearl.githubstars.net.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchUserResponse (
    @JsonProperty("total_count")
    val totalCount: Int,
    @JsonProperty("items")
    val userItems: List<UserItem>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserItem (
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("avatar_url")
    val avatarUrl: String,
    @JsonProperty("login")
    val login: String,
    @JsonProperty("text_matches")
    private val textMatches: List<TextMatches>
) {
    val name: String?
        get() {
            val matched = textMatches.firstOrNull { it.property == "name" }
            return if (matched?.fragment != null) matched.fragment else null
        }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TextMatches(
    @JsonProperty("object_url")
    val objectUrl: String,
    @JsonProperty("object_type")
    val objectType: String,
    @JsonProperty("property")
    val property: String?,
    @JsonProperty("fragment")
    val fragment: String?
)