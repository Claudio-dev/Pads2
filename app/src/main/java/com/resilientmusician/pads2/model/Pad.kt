package com.resilientmusician.pads2.model

data class Pad(
    val id: Int,
    var label: String = "Pad $id",
    var iconRes: Int? = null,
    var channel: Int = 1,
    var messageType: MessageType = MessageType.NoteOn,
    var controlNumber: Int = 0,
    var behavior: PadBehavior = PadBehavior.Trigger,
)

enum class MessageType {
    NoteOn, CC, ProgramChange
}

enum class PadBehavior {
    Trigger, Toggle
}