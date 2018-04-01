package com.merkmod.socketnotification

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

data class SocketReg(val userId: String?, val connectionId: String?)
class MainActivity : AppCompatActivity() {
	
	var socket: Socket? =  null

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)
		socket  = (application as Main).getSocket()
		socket?.on(Socket.EVENT_CONNECT , {
			runOnUiThread {
				Toast.makeText(this@MainActivity, "socket connected", Toast.LENGTH_SHORT).show()
			}
		})
		
		socket?.on("message" , {
			val jobject = it[0]
			Log.d("this", "Method executed ${it[0]}")
			runOnUiThread {
				Toast.makeText(this@MainActivity, jobject.toString(), Toast.LENGTH_LONG).show()
			}
		})
		
		
		fab.setOnClickListener {
			val socketReg = SocketReg("mkodekar", "mkodekar@zoho.com")
			val jsonString = Gson().toJson(socketReg)
			Log.d("socketReg", jsonString)
			socket?.connect()
			socket?.emit("register", "mkodekar", "mkodekar@zoho.com")
		}
	}

	override fun onCreateOptionsMenu(menu : Menu) : Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_main , menu)
		return true
	}

	override fun onOptionsItemSelected(item : MenuItem) : Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}
}
