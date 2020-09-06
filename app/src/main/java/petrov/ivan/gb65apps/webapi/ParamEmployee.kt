package petrov.ivan.gb65apps.webapi

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParamEmployee(
    @JsonProperty("f_name")
    val firstName: String,
    @JsonProperty("l_name")
    val lastName: String,
    @JsonProperty("birthday")
    val birthday: String?,
    @JsonProperty("avatr_url")
    val avatrUrl: String?,
    @JsonProperty("specialty")
    val specialty: List<ParamSpecialty>
): Serializable