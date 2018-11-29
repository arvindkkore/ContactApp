package com.test.contactapp.data.objbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Website
{
  @Id
  var id: Long = 0

  var website :String =""

    lateinit var contact: ToOne<Contact>
}