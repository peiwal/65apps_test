package petrov.ivan.gb65apps.utils

import petrov.ivan.gb65apps.data.Employee
import petrov.ivan.gb65apps.data.Speciality
import petrov.ivan.gb65apps.webapi.ParamEmployee
import petrov.ivan.gb65apps.webapi.ParamSpecialty
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class ApiConverter {
    private val dateFormatter by lazy { SimpleDateFormat("dd.mm.yyyy г.", Locale.getDefault()) }
    private val dateFormatVariants by lazy {
        listOf(
            Pair("^\\d{4}-\\d{2}-\\d{2}\$", SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())),
            Pair("^\\d{2}-\\d{2}-\\d{4}\$", SimpleDateFormat("dd-mm-yyyy", Locale.getDefault()))
        )
    }

    fun convert(param: List<ParamEmployee>): List<Employee> {
        val result =  ArrayList<Employee>()

        param.forEach {
            result.add(convert(it))
        }

        return result
    }

    private fun convert(param: ParamEmployee): Employee {
        var birthday = "-"
        var age = "-"
        param.birthday?.let {
            val date = tryParseDate(it)
            date?.let {
                birthday = dateFormatter.format(date)
                age = getAge(date)
            } ?: Timber.e("Unknown date format")
        }

        val listOfInvitees = ArrayList<Speciality>()
        param.specialty.forEach {
            listOfInvitees.add(convert(it))
        }

        return Employee(param.firstName.formatName(), param.lastName.formatName(), birthday, age, param.avatrUrl, listOfInvitees)
    }

    private fun convert(param: ParamSpecialty): Speciality {
        return Speciality(param.specialtyId, param.name)
    }

    private fun getAge(bithday: Date): String {
        val calendarBirthday = Calendar.getInstance()
        calendarBirthday.time = bithday
        return (Calendar.getInstance().get(Calendar.YEAR) - calendarBirthday.get(Calendar.YEAR)).toString()
    }

    private fun tryParseDate(dateString: String): Date? {
        for (item in dateFormatVariants) {
            if (Pattern.matches(item.first, dateString)) {
                return item.second.parse(dateString)
            }
        }
        return null
    }
}

private fun String.formatName() = this.substring(0, 1).toUpperCase(Locale.getDefault()) + this.substring(1, this.length).toLowerCase(Locale.getDefault())