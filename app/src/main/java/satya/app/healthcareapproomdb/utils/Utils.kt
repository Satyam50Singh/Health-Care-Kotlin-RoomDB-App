package satya.app.healthcareapproomdb.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import satya.app.healthcareapproomdb.R
import satya.app.healthcareapproomdb.databinding.DialogUpdateUserProfileBinding
import satya.app.healthcareapproomdb.listeners.RefreshFragmentListener
import satya.app.healthcareapproomdb.models.*
import satya.app.healthcareapproomdb.view.activites.LoginActivity

class Utils {
    companion object {
        fun toastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun switchActivity(context: Context, activity: Class<*>) {
            context.startActivity(Intent(context, activity))
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun showProfileUpdateDialog(
            context: Context,
            layoutInflater: LayoutInflater,
            refreshFragmentListener: RefreshFragmentListener? = null
        ) {
            Log.e("TAG", "showProfileUpdateDialog: ")

            val dialogBinding = DialogUpdateUserProfileBinding.inflate(layoutInflater)

            val dialog = Dialog(context, R.style.Theme_HealthCareApp_RoundedCornersDialog)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(true)

            dialogBinding.etPersonalContact.setText(
                PreferenceManager.getSharedPreferences(
                    context, Constants.PREF_PERSONAL_CONTACT
                ).toString()
            )
            dialogBinding.etEmergencyContact.setText(
                PreferenceManager.getSharedPreferences(
                    context, Constants.PREF_EMERGENCY_CONTACT
                ).toString()
            )
            dialogBinding.etAddress.setText(
                PreferenceManager.getSharedPreferences(
                    context, Constants.PREF_USER_ADDRESS
                ).toString()
            )
            dialogBinding.etAadhaarNumber.setText(
                PreferenceManager.getSharedPreferences(
                    context, Constants.PREF_AADHAAR_NUMBER
                ).toString()
            )

            // update profile
            dialogBinding.btnUpdateProfile.setOnClickListener {
                PreferenceManager.setSharedPreferences(
                    context,
                    Constants.PREF_PERSONAL_CONTACT,
                    dialogBinding.etPersonalContact.text.toString()
                )
                PreferenceManager.setSharedPreferences(
                    context,
                    Constants.PREF_EMERGENCY_CONTACT,
                    dialogBinding.etEmergencyContact.text.toString()
                )
                PreferenceManager.setSharedPreferences(
                    context, Constants.PREF_USER_ADDRESS, dialogBinding.etAddress.text.toString()
                )
                PreferenceManager.setSharedPreferences(
                    context,
                    Constants.PREF_AADHAAR_NUMBER,
                    dialogBinding.etAadhaarNumber.text.toString()
                )

                dialog.dismiss()
                refreshFragmentListener?.refresh()
            }

            if (!dialog.isShowing) dialog.show()
        }

        // method to do user logout.
        fun userLogout(context: Context) {
            val builder = AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.do_you_want_to_do_logout))
                .setTitle(context.getString(R.string.logout))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.dialog_yes)) { _, _ ->
                    run {
                        PreferenceManager.clearAllSharedPreferences(context)
                        switchActivity(context, LoginActivity::class.java)
                        (context as Activity).overridePendingTransition(
                            R.anim.enter_from_left,
                            R.anim.exit_in_right
                        )
                        context.finish()
                    }
                }
                .setNegativeButton(context.getString(R.string.dialog_no)) { dialog, _ ->
                    dialog.cancel()
                }
            val alertDialog = builder.create()
            if (!alertDialog.isShowing)
                alertDialog.show()
        }

        fun setAppTheme(context: Context, isDarkModeEnabled: Boolean) {
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeEnabled)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )

            PreferenceManager.setSharedPreferences(
                context,
                Constants.PREF_DARK_MODE_VALUE,
                isDarkModeEnabled
            )
        }

        fun getDashboardModuleList(context: Context): ArrayList<DashboardModel> {
            val dashboardListItems = ArrayList<DashboardModel>()
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.find_doctors),
                    "Looking for a doctor? Use our helpful search tools to find a doctor for your individual",
                    R.drawable.logo
                )
            )
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.find_ambulance),
                    "Looking for a ambulance? Use our helpful search tools to find a ambulance for your individual",
                    R.drawable.logo
                )
            )
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.book_lab_test),
                    "Choose the best labs near you. Book Tests, Scans, Complete Test Packages Online. Also View Report, Test Costs, and other information about various Diagnostic Labs",
                    R.drawable.logo
                )
            )
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.order_medicine),
                    "Everyone should have access to cheap and quality medicine. And that’s exactly what Canada Drugs Direct offers to customers even living in USA.",
                    R.drawable.logo
                )
            )
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.health_articles),
                    "Time. Happiness. Inner Peace. Integrity. Love. Character. Manners. Health. Respect. Morals. Trust. Patience. Class. Common sense. Dignity",
                    R.drawable.logo
                )
            )
            dashboardListItems.add(
                DashboardModel(
                    context.getString(R.string.action_check_your_appointments),
                    "We encourage you to complete your transaction online. If you are not able to complete your transaction online, you can now schedule an appointment to visit one of our business centers.",
                    R.drawable.logo
                )
            )
            return dashboardListItems
        }

        fun getDoctorTypesList(context: Context): ArrayList<DashboardModel> {
            val doctorsTypesList = ArrayList<DashboardModel>()
            doctorsTypesList.add(
                DashboardModel(
                    "Physicians",
                    "Physicians and surgeons diagnose and treat injuries or illnesses and address health maintenance. Physicians examine patients; take medical histories",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Cardiologists",
                    "Cardiologists are doctors who specialize in the treatment of heart disease. Cardiologists can diagnose and treat coronary heart disease, heart failure, and other cardiovascular illnesses.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Dentist",
                    "Dental courses prepare students for a career that focuses on the health of the teeth, gums, and other tissues in and around the mouth. Dentistry refers to dental operations that are undertaken to prevent oral diseases.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Endocrinologist",
                    "Endocrinologists treat patients with diabetes and thyroid problems. As people grow older, he or she sees more osteoporosis patients.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Gynecologists",
                    "Gynecologist services include infertility treatment, gynecological diseases, vaginal infections (vaginalitis), dysmenorrhoea, hysterectomy, pregnancy-related issues, cervix and uterus infections (including fungal, bacterial, viral, and protozoal), amenorrhoea (absent menstrual periods), and urinary incontinence. ",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Orthopedic Surgeon",
                    "Orthopedic surgeons are experts in the prevention, diagnosis, and treatment of disorders that affect the bones, joints, ligaments, tendons, and muscles.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Pediatrician",
                    "Pediatrics is a branch of medicine that deals with the medical treatment of newborns, children, and adolescents.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Psychiatrists",
                    "Psychiatrists are doctors who specialize in mental health, which includes substance abuse disorders. Psychiatrists are educated to assess both the mental and physical aspects of psychiatric issues.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Radiologist",
                    "Radiology is a branch of medicine that uses medical imaging to identify and treat illnesses in both animals and humans.",
                    R.drawable.doctor
                )
            )
            doctorsTypesList.add(
                DashboardModel(
                    "Veterinarian",
                    "A veterinarian, sometimes known as a veterinary surgeon or veterinary physician, is a medical practitioner who treats illnesses, disorders, and injuries in animals that are not humans.",
                    R.drawable.doctor
                )
            )
            return doctorsTypesList
        }

        fun getPhysiciansList(): ArrayList<DoctorListModel> {
            val doctorsList = ArrayList<DoctorListModel>()
            doctorsList.add(
                DoctorListModel(
                    101, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.Background lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach. ","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    102, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    103, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    104, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    105, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    106, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    107, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    108, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    109, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    110, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    111, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    112, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    113, "Dr. Archana", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    114, "Dr. Arnav Malhotra", "An alumnus from Armed Forces Medical College with vast experience in professional field from base to apex levels. Also possess Post Graduate Diploma in Health Care Management and Fellowship in Rural Health . Amongst the very few with leadership experience in most challenging operational military medical environments.\nBackground lies in ensuring lowest mortality and morbidity by adopting protocols to achieve peak medical management levels. Excel at provision of compassionate medical care with a holistic and ethical approach.","786868669", 30.3165, 78.0322, "Physicians"
                )
            )
            return doctorsList
        }

        fun getDoctorsList(): ArrayList<DoctorListModel> {
            val doctorsList = ArrayList<DoctorListModel>()
            doctorsList.add(
                DoctorListModel(
                    201, "Dr. Shivani Chamoli","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    202, "Dr. Arnav Malhotra","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    203, "Dr. Shivani Chamoli","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    204, "Dr. Rahul Pandey","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    205, "Dr. Arnav Malhotra","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    206, "Dr. Shivani Chamoli","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    207, "Dr. Arnav Malhotra","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    208, "Dr. Shivani Chamoli","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    209, "Dr. Arnav Malhotra","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    210, "Dr. Shivani Chamoli","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    211, "Dr. Arnav Malhotra","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    212, "Dr. Shivani Chamoli","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    213, "Dr. Arnav Malhotra","Vaidyaratnam Dr. Rakesh Agarwal is a third-generation Ayurveda Specialist, Research Scholar, and Philanthropist. He is a dedicated and innovative Ayurvedic Doctor of International repute is renowned for handling many of the most complex cases in various fields of Ayurveda as: Chronic Diseases and Sexual Disorder, Infertility Problems, Diabetes Mellitus, Migraine, obesity ,Joint Pains ,Renal Calculi ,Urinary Calculi, Treatment for addictive injections ,Heart Diseases Diabetes Mellitus ,Piles ,Skin Diseases, Knee basti, for knee pain and other complications ,Mental disorders ,Liver disorders, Treatment for any type of addiction, Yoga therapy, All type of ayurvedic, panch karma procedure.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )
            doctorsList.add(
                DoctorListModel(
                    214, "Dr. Shivani Chamoli","I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.I'm a caring, skilled professional, dedicated to simplifying what is often a very complicated and confusing area of health care.", "786868669", 30.3165, 78.0322, "Dentist"
                )
            )

            return doctorsList
        }

        fun getLabTestList(): ArrayList<LabTestModel> {
            val labTestList = ArrayList<LabTestModel>()
            labTestList.add(
                LabTestModel(
                    101,
                    "Fasting Blood Sugar",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    102,
                    "Urine Routine",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            labTestList.add(
                LabTestModel(
                    103,
                    "Liver Function test (LFT)",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    104,
                    "Creatinine",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            labTestList.add(
                LabTestModel(
                    105,
                    "Malaria Antigen Test",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    106,
                    "Progesterone",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            labTestList.add(
                LabTestModel(
                    107,
                    "Thyroid Profile Test",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    108,
                    "Fasting Blood Sugar",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            labTestList.add(
                LabTestModel(
                    109,
                    "Urine Routine",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    110,
                    "Liver Function test (LFT)",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            labTestList.add(
                LabTestModel(
                    111,
                    "Creatinine",
                    "It is a urine test used to detect germs like bacteria or microbial infections of the urinary tract. Bacteria enter the body through the urethra and cause a urinary tract infection.",
                    "Urine",
                    false,
                    "Urine Container"
                )
            )
            labTestList.add(
                LabTestModel(
                    112,
                    "Malaria Antigen Test",
                    "Liver Function Test consists of various blood tests that may help diagnose and monitor liver-related diseases. Some tests help determine how well the liver is functioning. The LFT test measures the level of liver enzymes and proteins in your blood. Liver disorders may present symptoms like abdominal pain, weight loss, nausea, diarrhoea, lack of energy, jaundice, fever or fatigue, dark-coloured urine or light-coloured stools",
                    "Blood",
                    true,
                    "Plain"
                )
            )
            return labTestList
        }

        fun getLabsList(): ArrayList<LabListModel> {
            val labsList = ArrayList<LabListModel>()
            labsList.add(
                LabListModel(
                    401,
                    "Dr Lal PathLabs – Patient Service Centre",
                    3.3,
                    3345,
                    "Laboratory",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "09:00 AM",
                    "08:00PM",
                    399,
                    100
                )
            )
            labsList.add(
                LabListModel(
                    402,
                    "Life Care Path Lab",
                    4.3,
                    2345,
                    "Diagnostic Center",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "10:00 AM",
                    "09:00PM",
                    499,
                    50
                )
            )
            labsList.add(
                LabListModel(
                    403,
                    "Medical Microbiology & RDT Labs",
                    2.3,
                    2345,
                    "Medical Laboratory",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "09:00 AM",
                    "08:00PM",
                    399,
                    100
                )
            )
            labsList.add(
                LabListModel(
                    404,
                    "Raksha Pathology Lab",
                    5.0,
                    23345,
                    "Diagnostic Center",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "10:00 AM",
                    "09:00PM",
                    499,
                    50
                )
            )
            labsList.add(
                LabListModel(
                    405,
                    "Dr Lal PathLabs – Patient Service Centre",
                    3.3,
                    2345,
                    "Laboratory",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "09:00 AM",
                    "08:00PM",
                    499,
                    50
                )
            )
            labsList.add(
                LabListModel(
                    406,
                    "Life Care Path Lab",
                    4.3,
                    2335,
                    "Diagnostic Center",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "10:00 AM",
                    "09:00PM",
                    399,
                    100
                )
            )
            labsList.add(
                LabListModel(
                    407,
                    "Medical Microbiology & RDT Labs",
                    2.3,
                    2335,
                    "Medical Laboratory",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "09:00 AM",
                    "08:00PM",
                    499,
                    50
                )
            )
            labsList.add(
                LabListModel(
                    408,
                    "Raksha Pathology Lab",
                    5.0,
                    3345,
                    "Diagnostic Center",
                    "Shop No 3, Shastradhara Road, I. T. Park Rd, near Pnb Bank, Dehradun, Uttarakhand 249204",
                    "10:00 AM",
                    "09:00PM",
                    399,
                    100
                )
            )
            return labsList
        }

        fun getHealthArticles(): ArrayList<HealthArticleModel> {
            val healthArticleList = ArrayList<HealthArticleModel>()
            healthArticleList.add(
                HealthArticleModel(
                    "Reduce intake of harmful fats",
                    "Mar.12.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Avoid harmful use of alcohol",
                    "Apr.23.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Check your blood pressure regularly",
                    "Mar.12.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Practice safe sex",
                    "Apr.23.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Cover your mouth when coughing or sneezing",
                    "Mar.12.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Prevent mosquito bites",
                    "Apr.23.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Breastfeed babies from 0 to 2 years and beyond",
                    "Mar.12.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Prepare your food correctly",
                    "Apr.23.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Take antibiotics only as prescribed",
                    "Mar.12.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            healthArticleList.add(
                HealthArticleModel(
                    "Talk to someone you trust if you're feeling down",
                    "Apr.23.2023",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n\nContrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source.\n\n"
                )
            )
            return healthArticleList
        }

        fun getMedicineList(): ArrayList<MedicineModel> {
            val medicineList = ArrayList<MedicineModel>()
            medicineList.add(
                MedicineModel(
                    101, "Azithromycin", "500mg", "Tablet", 10, 300, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    102, "Methadone", "800mg", "Tablet", 10, 470, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    103, "Onpattro", "800mg", "Capsule", 6, 450, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    104, "Azithromycin", "500mg", "Tablet", 10, 300, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    105, "Methotrexate", "800mg", "Tablet", 10, 470, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    106, "Pantoprazole", "800mg", "Capsule", 6, 460
                )
            )
            medicineList.add(
                MedicineModel(
                    107, "Double Azithromycin", "500mg", "Tablet", 10, 300, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    108, "Methadone Repeat", "800mg", "Tablet", 10, 470, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    109, "Onpattro", "800mg", "Capsule", 6, 450, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    110, "Azithromycin", "500mg", "Tablet", 10, 300, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    111, "Methotrexate", "800mg", "Tablet", 10, 470, 0
                )
            )
            medicineList.add(
                MedicineModel(
                    112, "Pantoprazole", "800mg", "Capsule", 6, 460, 0
                )
            )
            return medicineList
        }
    }
}