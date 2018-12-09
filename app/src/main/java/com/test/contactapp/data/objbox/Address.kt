package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Address {
    @Id
    var id: Long = 0
    var address: String = ""
    var city :String =""
    var postCode :String =""
    var street :String =""
    var state :String =""
    var country :String =""

    var address_type: String = ""
    lateinit var contact: ToOne<Contact>
    lateinit var addressType: ToOne<AddressTypeLookup>
    var postalcode: String =""
}