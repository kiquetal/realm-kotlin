package cresterida.me.learning.practicing.data

import cresterida.me.learning.practicing.data.model.Sections
import cresterida.me.learning.practicing.exception.CustomRealmResult
import io.realm.Realm
import io.realm.RealmResults
import io.realm.exceptions.RealmException
import io.realm.exceptions.RealmPrimaryKeyConstraintException

/**
 * Created by kiquetal on 1/13/18.
 */
class RepositorySections :Repository<Sections>
{
    private val realm= Realm.getDefaultInstance()
    override val clazz: Class<Sections>
        get() = Sections::class.java
    override fun getAllUsers(): RealmResults<Sections> {
       return realm.where(clazz).findAll()

    }

    override fun addUser(sections: Sections):CustomRealmResult {

        try {

            realm.beginTransaction()
            realm.copyToRealm(sections)
            realm.commitTransaction()
           return CustomRealmResult(true)
        } catch (e: RealmPrimaryKeyConstraintException) {
          return  CustomRealmResult(false,e.message!!)
        } catch (e: RealmException) {
            return CustomRealmResult(false,e.message!!)
        }

     }

}