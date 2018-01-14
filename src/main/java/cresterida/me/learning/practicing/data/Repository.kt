package cresterida.me.learning.practicing.data

import cresterida.me.learning.practicing.exception.CustomRealmResult
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by kiquetal on 1/13/18.
 */

interface Repository<T> where T:RealmObject
{
    fun getAllUsers():RealmResults<T>
    fun addUser(user:T): CustomRealmResult

    val clazz:Class<T>
}