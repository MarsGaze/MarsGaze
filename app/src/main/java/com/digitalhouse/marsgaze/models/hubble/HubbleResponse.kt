package com.digitalhouse.marsgaze.models.hubble

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HubbleResponse(
    val itens: List<Item>
)

data class Item (
    val links: Link,
    @SerializedName("href") val itemHref: String,
    val data: Data
): Serializable

data class Link (
    val render: String,
    @SerializedName("href") val linkHref: String,           // IMPORTANT
    val rel: String
): Serializable

data class Data (
    val media_type: String,
    val center: String,
    val nasa_id: String,
    val description: String,    // IMPORTANT
    val keywords: ArrayList<Keyword>,
    val date_created: String,
    val title: String           // IMPORTANT
): Serializable

data class Keyword (
    val keyword: String
): Serializable