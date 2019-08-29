package com.apdallahy3.marvelcharcters.Network.Models

data class CharactersResponse(
    val code: Int,
    val status:String,
    val data:CharacterDataItems
) {
}