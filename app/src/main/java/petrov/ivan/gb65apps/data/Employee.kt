package petrov.ivan.gb65apps.data

import java.io.Serializable

data class Employee(
    val fName: String,
    val lName: String,
    val birthday: String,
    val age: String,
    val avatrUrl: String?,
    val speciality: List<Speciality>
): Serializable