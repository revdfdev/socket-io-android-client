package com.merkmod.socketnotification

import android.app.Application
import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException

class Main: Application() {
	
	private var mSocket : Socket? =
		try {
			IO.socket("http://merkmod.com:1800")
		}
		catch (e: URISyntaxException) {
			Log.d("", e.message!!)
			null
		}
	
	fun getSocket() : Socket? {
		return mSocket
	}
}