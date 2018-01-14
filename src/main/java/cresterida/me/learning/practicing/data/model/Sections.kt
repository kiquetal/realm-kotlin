package cresterida.me.learning.practicing.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by kiquetal on 1/13/18.
 */
open class Sections: RealmObject()
{
    @PrimaryKey
    lateinit var id:String;
    lateinit var name:String
}