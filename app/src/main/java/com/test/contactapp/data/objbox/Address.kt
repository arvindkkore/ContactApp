package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Address {
    @Id
    var id: Long = 0
    var address: String = ""
    var address_type: String = ""
    lateinit var contact: ToOne<Contact>
    lateinit var addressType: ToOne<AddressTypeLookup>
}