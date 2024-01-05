package com.jr.speergit.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.jr.speergit.domain.model.GithubUser

data class GithubUserDto(
    @SerializedName("login"               ) var login             : String,
    @SerializedName("id"                  ) var id                : Long,
    @SerializedName("node_id"             ) var nodeId            : String?  = null,
    @SerializedName("avatar_url"          ) var avatarUrl         : String?  = null,
    @SerializedName("gravatar_id"         ) var gravatarId        : String?  = null,
    @SerializedName("url"                 ) var url               : String?  = null,
    @SerializedName("html_url"            ) var htmlUrl           : String?  = null,
    @SerializedName("followers_url"       ) var followersUrl      : String?  = null,
    @SerializedName("following_url"       ) var followingUrl      : String?  = null,
    @SerializedName("gists_url"           ) var gistsUrl          : String?  = null,
    @SerializedName("starred_url"         ) var starredUrl        : String?  = null,
    @SerializedName("subscriptions_url"   ) var subscriptionsUrl  : String?  = null,
    @SerializedName("organizations_url"   ) var organizationsUrl  : String?  = null,
    @SerializedName("repos_url"           ) var reposUrl          : String?  = null,
    @SerializedName("events_url"          ) var eventsUrl         : String?  = null,
    @SerializedName("received_events_url" ) var receivedEventsUrl : String?  = null,
    @SerializedName("type"                ) var type              : String?  = null,
    @SerializedName("site_admin"          ) var siteAdmin         : Boolean  = false,
    @SerializedName("name"                ) var name              : String?  = null,
    @SerializedName("company"             ) var company           : String?  = null,
    @SerializedName("blog"                ) var blog              : String?  = null,
    @SerializedName("location"            ) var location          : String?  = null,
    @SerializedName("email"               ) var email             : String?  = null,
    @SerializedName("hireable"            ) var hireable          : Boolean? = null,
    @SerializedName("bio"                 ) var bio               : String?  = null,
    @SerializedName("twitter_username"    ) var twitterUsername   : String?  = null,
    @SerializedName("public_repos"        ) var publicRepos       : Int      = 0,
    @SerializedName("public_gists"        ) var publicGists       : Int?     = null,
    @SerializedName("followers"           ) var followers         : Int      = 0,
    @SerializedName("following"           ) var following         : Int      = 0,
    @SerializedName("created_at"          ) var createdAt         : String?  = null,
    @SerializedName("updated_at"          ) var updatedAt         : String?  = null,
)

/**
 * Mapping GithubUserDto to GithubUser
 * Note: This mapping can also be injected into GithubRepositoryImpl for more flexibility
 */
fun GithubUserDto.toGithubUser() = GithubUser(
    id = id,
    avatarUrl = avatarUrl ?: "",
    username = login,
    name = name ?: "",
    description = bio ?: "",
    repositories = publicRepos,
    followers = followers,
    following = following,
)