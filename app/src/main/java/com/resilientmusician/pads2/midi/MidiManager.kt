package com.resilientmusician.pads2.midi

import android.content.Context
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiManager
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class MidiManager(private val context: Context) {
    private val midiManager: MidiManager = context.getSystemService(Context.MIDI_SERVICE) as MidiManager
    val availableDevices = mutableStateListOf<MidiDeviceInfo>()
    val selectedDevice = mutableStateOf<MidiDeviceInfo?>(null)
    val isConnected = mutableStateOf(false)

    init {
        scanForDevices()
    }

    fun scanForDevices() {
        availableDevices.clear()
        val devices = midiManager.devices
        // En API 24, filtramos por dispositivos que tengan puertos de entrada/salida
        availableDevices.addAll(devices.filter { device ->
            device.ports.any { port ->
                port.type == MidiDeviceInfo.PortInfo.TYPE_INPUT ||
                        port.type == MidiDeviceInfo.PortInfo.TYPE_OUTPUT
            }
        })
    }

    fun selectDevice(deviceInfo: MidiDeviceInfo) {
        selectedDevice.value = deviceInfo
        openDevice(deviceInfo)
    }

    private fun openDevice(deviceInfo: MidiDeviceInfo) {
        try {
            midiManager.openDevice(deviceInfo, {
                if (it != null) {
                    isConnected.value = true
                    // Aquí puedes guardar la referencia al dispositivo para enviar mensajes
                } else {
                    isConnected.value = false
                }
            }, Handler(Looper.getMainLooper()))
        } catch (e: Exception) {
            e.printStackTrace()
            isConnected.value = false
        }
    }

    fun getDeviceName(deviceInfo: MidiDeviceInfo): String {
        val manufacturer = deviceInfo.properties.getString(MidiDeviceInfo.PROPERTY_MANUFACTURER) ?: "Unknown"
        val name = deviceInfo.properties.getString(MidiDeviceInfo.PROPERTY_NAME) ?: "MIDI Device"
        return "$manufacturer - $name"
    }
}