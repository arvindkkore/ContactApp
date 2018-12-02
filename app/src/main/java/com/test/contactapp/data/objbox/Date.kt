package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Date {

    @Id
    var id:Long =0
    var date :String =""
    //phone type
    lateinit var dateType : ToOne<DateTypeLookup>

    lateinit var contact: ToOne<Contact>


}