package com.apdallahy3.marvelcharcters.Network.Models

data class CharacterDataItems(
    val offset: Int,
    val count: Int,
    val results: List<CharacterItem>
)