package cresterida.me.learning.practicing

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import cresterida.me.learning.practicing.data.Repository
import cresterida.me.learning.practicing.data.RepositorySections
import cresterida.me.learning.practicing.data.model.Sections
import cresterida.me.learning.practicing.exception.CustomRealmResult
import io.realm.ObjectServerError
import io.realm.Realm
import io.realm.SyncCredentials
import io.realm.SyncUser
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    lateinit var b:Button
    lateinit var m:RepositorySections
    companion object {
        val ref=this
    }
    val callBackHandlers:SyncUser.Callback=object: SyncUser.Callback{
        override fun onError(error: ObjectServerError?) {
            Log.v(this.javaClass.toString(),"Error en " + error!!.toString())

        }

        override fun onSuccess(user: SyncUser?) {
            Log.v(this::javaClass.toString(),"Se conectó " + user ?: "error")
            val s=Sections()
            s.id="kiquetal"
            s.name="que hay"

            val r:CustomRealmResult= m.addUser(s)
            when (r) {
                CustomRealmResult(true,"OK") -> toast("Ingreo ok")
                else ->toast("Jamas se ingreso debido a ${r.message}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        m= RepositorySections()

        RealmLog.setLevel(LogLevel.DEBUG);
        setContentView(R.layout.activity_main)

        b=findViewById<Button>(R.id.button_list)
        b.onClick {
            longToast(m.getAllUsers().toList().toString())
        }
        val creds:SyncCredentials= SyncCredentials.usernamePassword(BuildConfig.REALM_USER,BuildConfig.REALM_PASSWORD,true)
        
        SyncUser.loginAsync(creds,BuildConfig.REALM_URL,callBackHandlers)

    }


}
