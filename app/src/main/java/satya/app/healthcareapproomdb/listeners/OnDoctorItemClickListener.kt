package satya.app.healthcareapproomdb.listeners

import satya.app.healthcareapproomdb.models.DoctorListModel

interface OnDoctorItemClickListener {
    fun onClick(itemDoctor: DoctorListModel, btnType: String)
}