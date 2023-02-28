package satya.app.healthcareapp.listeners

import satya.app.healthcareapp.models.DoctorListModel

interface OnDoctorItemClickListener {
    fun onClick(itemDoctor: DoctorListModel, btnType: String)
}