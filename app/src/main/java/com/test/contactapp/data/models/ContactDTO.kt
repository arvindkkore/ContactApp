package com.test.contactapp.data.models

class ContactDTO {

    // Contact belong group fields.
    var groupId: Long = 0

    // Contacts id.
    var contactId: Long = 0

    // Raw contacts id. Has same value of contact id.
    var rawContactId: Long = 0

    // Contact structured name fields.
    var displayName: String? = null
    var givenName: String? = null
    var familyName: String? = null

    // Contact nickname fields.
    var nickName: String? = null

    // Contact organization fields.
    var company: String? = null
    var department: String? = null
    var title: String? = null
    var jobDescription: String? = null
    var officeLocation: String? = null

    // Contact phone list.
    var phoneList: List<DataDTO> = ArrayList<DataDTO>()

    // Contact email list
    var emailList: List<DataDTO> = ArrayList<DataDTO>()

    // Contact address list.
    var addressList: List<DataDTO> = ArrayList<DataDTO>()

    // Contact website list.
    var websiteList: List<DataDTO> = ArrayList<DataDTO>()

    // Contact note.
    var note: String? = null

    // Contact im list.
    var imList: List<DataDTO> = ArrayList<DataDTO>()

    // Contact postal fields.
    var country: String? = null
    var city: String? = null
    var postCode: String? = null
    var street: String? = null
    var region: String? = null
    var postType: Long = 0

    // Contact identity fields.
    // Identity value
    var identity: String? = null
    // Identity card, passport etc.
    var namespace: String? = null

    // Contact photo fields.
    var photo: String? = null
    var photoFieldId: String? = null
}