package petrov.ivan.gb65apps.webapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class ParamSpecialty(
    @JsonProperty("specialty_id")
    val specialtyId: String,
    val name: String
): Serializable